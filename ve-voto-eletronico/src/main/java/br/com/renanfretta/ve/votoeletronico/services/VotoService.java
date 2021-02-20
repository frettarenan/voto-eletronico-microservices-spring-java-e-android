package br.com.renanfretta.ve.votoeletronico.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.renanfretta.ve.commons.dtos.usuario.UsuarioDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.sessaovotacao.SessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.voto.VotoInputDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.voto.VotoOutputDTO;
import br.com.renanfretta.ve.commons.integracoes.userinfo.users.UserInfoApiUsersFindByCpfOutput;
import br.com.renanfretta.ve.votoeletronico.configs.OrikaMapper;
import br.com.renanfretta.ve.votoeletronico.configs.properties.MessagesProperty;
import br.com.renanfretta.ve.votoeletronico.configs.properties.YamlConfig;
import br.com.renanfretta.ve.votoeletronico.entities.Pauta;
import br.com.renanfretta.ve.votoeletronico.entities.Voto;
import br.com.renanfretta.ve.votoeletronico.enums.MessagesPropertyEnum;
import br.com.renanfretta.ve.votoeletronico.exceptions.ErroTratadoRestException;
import br.com.renanfretta.ve.votoeletronico.repositories.VotoRepository;
import reactor.core.publisher.Mono;

@Service
@Validated
public class VotoService {

	private static final String STATUS_ABLE_TO_VOTE = "ABLE_TO_VOTE";

	@Autowired
	private OrikaMapper orikaMapper;

	@Autowired
	private VotoRepository repository;

	@Autowired
	private YamlConfig yamlConfig;

	@Autowired
	private MessagesProperty messagesProperty;

	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;

	@Autowired
	private UsuarioService usuarioService;

	private VotoOutputDTO findById(Long id) {
		Voto voto = repository.findById(id).orElseThrow();
		VotoOutputDTO dto = orikaMapper.map(voto, VotoOutputDTO.class);
		return dto;
	}

	public VotoOutputDTO save(VotoInputDTO votoInputDTO) throws ErroTratadoRestException {
		Date dataVotacao = new Date();
		SessaoVotacaoOutputDTO sessaoVotacaoOutputDTO = sessaoVotacaoService
				.findById(votoInputDTO.getSessaoVotacao().getId());
		UsuarioDTO usuarioDTO = usuarioService.findById(votoInputDTO.getUsuario().getId());

		validaSeCpfTemPermissaoDeVoto(usuarioDTO.getCpf());
		validaHorarioVotacao(dataVotacao, sessaoVotacaoOutputDTO);

		Voto voto = orikaMapper.map(votoInputDTO, Voto.class);
		voto.setPauta(Pauta.builder().id(sessaoVotacaoOutputDTO.getPauta().getId()).build());

		voto.setDataHoraVotacao(dataVotacao);

		voto = repository.save(voto);
		VotoOutputDTO votoOutputDTO = findById(voto.getId());
		return votoOutputDTO;
	}

	private void validaSeCpfTemPermissaoDeVoto(String cpf) throws ErroTratadoRestException {
		String userinfoapiurl = yamlConfig.getUserinfoapiurl();
		WebClient webClient = WebClient.builder() //
				.baseUrl(userinfoapiurl) //
				.build();

		UserInfoApiUsersFindByCpfOutput retorno = webClient.get() //
				.uri("/users/" + cpf) //
				.accept(MediaType.APPLICATION_JSON).retrieve() //
				.onStatus(HttpStatus::is4xxClientError,
						error -> Mono.error(new ErroTratadoRestException(messagesProperty
								.getMessage(MessagesPropertyEnum.ERRO__USER_INFO_SERVICE_CPF_NAO_ENCONTRADO))))
				.bodyToMono(UserInfoApiUsersFindByCpfOutput.class) //
				.block();

		if (!retorno.getStatus().equalsIgnoreCase(STATUS_ABLE_TO_VOTE))
			throw new ErroTratadoRestException(
					messagesProperty.getMessage(MessagesPropertyEnum.RN__USUARIO_NAO_AUTORIZADO_VOTAR));
	}

	private void validaHorarioVotacao(Date dataVotacao, SessaoVotacaoOutputDTO sessaoVotacaoOutputDTO)
			throws ErroTratadoRestException {
		if (dataVotacao.after(sessaoVotacaoOutputDTO.getDataHoraFim()))
			throw new ErroTratadoRestException(
					messagesProperty.getMessage(MessagesPropertyEnum.RN__SESSAO_VOTACAO_ENCERRADA));
	}

}
