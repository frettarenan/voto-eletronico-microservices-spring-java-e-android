package br.com.renanfretta.ve.votoeletronico.services;

import br.com.renanfretta.ve.commons.dtos.usuario.UsuarioDTO;
import br.com.renanfretta.ve.commons.integracoes.userinfo.users.UserInfoApiUsersFindByCpfOutput;
import br.com.renanfretta.ve.votoeletronico.configs.OrikaMapper;
import br.com.renanfretta.ve.votoeletronico.configs.properties.MessagesProperty;
import br.com.renanfretta.ve.votoeletronico.configs.properties.YamlConfig;
import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.RelatorioVotosContabilizadosPorPautaOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.VotoInputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.VotoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.entities.Pauta;
import br.com.renanfretta.ve.votoeletronico.entities.Voto;
import br.com.renanfretta.ve.votoeletronico.enums.MessagesPropertyEnum;
import br.com.renanfretta.ve.votoeletronico.exceptions.ErroTratadoRestException;
import br.com.renanfretta.ve.votoeletronico.repositories.voto.VotoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Validated
public class VotoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VotoService.class);

	public static final String STATUS_ABLE_TO_VOTE = "ABLE_TO_VOTE";
	public static final String STATUS_UNABLE_TO_VOTE = "UNABLE_TO_VOTE";

	private final ObjectMapper objectMapper;
	private final OrikaMapper orikaMapper;
	private final MessagesProperty messagesProperty;
	private final VotoRepository repository;
	private final YamlConfig yamlConfig;
	private final SessaoVotacaoService sessaoVotacaoService;
	private final UsuarioService usuarioService;
	private final PautaService pautaService;

	public VotoService(ObjectMapper objectMapper, OrikaMapper orikaMapper, MessagesProperty messagesProperty, VotoRepository repository, YamlConfig yamlConfig, SessaoVotacaoService sessaoVotacaoService, UsuarioService usuarioService, PautaService pautaService) {
		this.objectMapper = objectMapper;
		this.orikaMapper = orikaMapper;
		this.messagesProperty = messagesProperty;
		this.repository = repository;
		this.yamlConfig = yamlConfig;
		this.sessaoVotacaoService = sessaoVotacaoService;
		this.usuarioService = usuarioService;
		this.pautaService = pautaService;
	}

	public VotoOutputDTO findById(Long id) {
		Voto voto = repository.findById(id).orElseThrow(() -> new NoSuchElementException(messagesProperty.getMessage(MessagesPropertyEnum.ERRO__REGISTRO_NAO_ENCONTRADO_ENTIDADE_VOTO)));
		LOGGER.trace("VotoRepository/findById(" + id + ") teve êxito");
		VotoOutputDTO dto = orikaMapper.map(voto, VotoOutputDTO.class);
		return dto;
	}

	public VotoOutputDTO save(VotoInputDTO votoInputDTO) throws ErroTratadoRestException, JsonProcessingException {
		Date dataVotacao = new Date();
		SessaoVotacaoOutputDTO sessaoVotacaoOutputDTO = sessaoVotacaoService.findById(votoInputDTO.getSessaoVotacao().getId());
		UsuarioDTO usuarioDTO = usuarioService.findById(votoInputDTO.getUsuario().getId());

		validaSeCpfTemPermissaoDeVoto(usuarioDTO.getCpf());
		validaHorarioVotacao(dataVotacao, sessaoVotacaoOutputDTO);

		Voto voto = orikaMapper.map(votoInputDTO, Voto.class);
		voto.setPauta(Pauta.builder().id(sessaoVotacaoOutputDTO.getPauta().getId()).build());

		voto.setDataHoraVotacao(dataVotacao);

		voto = repository.save(voto);
		LOGGER.trace("VotoRepository/save(" + objectMapper.writeValueAsString(voto) + ") teve êxito");

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
				.onStatus(HttpStatus::is4xxClientError, error -> Mono.error(new ErroTratadoRestException(messagesProperty.getMessage(MessagesPropertyEnum.ERRO__USER_INFO_SERVICE_CPF_NAO_ENCONTRADO))))
				.bodyToMono(UserInfoApiUsersFindByCpfOutput.class) //
				.block();

		if (!retorno.getStatus().equalsIgnoreCase(STATUS_ABLE_TO_VOTE)) {
			LOGGER.warn("VotoRepository/validaSeCpfTemPermissaoDeVoto(" + cpf + ") não pode votar > acesso negado");
			throw new ErroTratadoRestException(messagesProperty.getMessage(MessagesPropertyEnum.RN__USUARIO_NAO_AUTORIZADO_VOTAR));
		}
	}

	private void validaHorarioVotacao(Date dataVotacao, SessaoVotacaoOutputDTO sessaoVotacaoOutputDTO) throws ErroTratadoRestException, JsonProcessingException {
		if (dataVotacao.before(sessaoVotacaoOutputDTO.getDataHoraInicio()) || dataVotacao.after(sessaoVotacaoOutputDTO.getDataHoraFim())) {
			LOGGER.warn("VotoRepository/validaHorarioVotacao(" + objectMapper.writeValueAsString(sessaoVotacaoOutputDTO) + ", " + dataVotacao
					+ ") não pode votar > ultrapassou o horário máximo de votação");
			throw new ErroTratadoRestException(messagesProperty.getMessage(MessagesPropertyEnum.RN__SESSAO_VOTACAO_ENCERRADA));
		}
	}

	public List<RelatorioVotosContabilizadosPorPautaOutputDTO> contabilizaVotosPorPauta(Long idPauta) {

		// Valida se a pauta existe
		pautaService.findById(idPauta);

		List<RelatorioVotosContabilizadosPorPautaOutputDTO> list = repository.contabilizaVotosPorPauta(idPauta);
		return list;
	}

	public List<RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO> contabilizaVotosPorSessaoVotacao(Long idSessaoVotacao) {

		// Valida se a sessão de votação existe
		sessaoVotacaoService.findById(idSessaoVotacao);

		List<RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO> list = repository.contabilizaVotosPorSessaoVotacao(idSessaoVotacao);
		return list;
	}

}
