package br.com.renanfretta.ve.votoeletronico.services;

import br.com.renanfretta.ve.commons.dtos.usuario.UsuarioDTO;
import br.com.renanfretta.ve.votoeletronico.configs.OrikaMapper;
import br.com.renanfretta.ve.votoeletronico.configs.properties.MessagesProperty;
import br.com.renanfretta.ve.votoeletronico.entities.Usuario;
import br.com.renanfretta.ve.votoeletronico.enums.MessagesPropertyEnum;
import br.com.renanfretta.ve.votoeletronico.repositories.usuario.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Validated
public class UsuarioService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);

	private final OrikaMapper orikaMapper;
	private final MessagesProperty messagesProperty;
	private final UsuarioRepository repository;

	public UsuarioService(OrikaMapper orikaMapper, MessagesProperty messagesProperty, UsuarioRepository repository) {
		this.orikaMapper = orikaMapper;
		this.messagesProperty = messagesProperty;
		this.repository = repository;
	}

	public UsuarioDTO findById(Long id) {
		Usuario usuario = repository.findById(id).orElseThrow(() -> new NoSuchElementException(messagesProperty.getMessage(MessagesPropertyEnum.ERRO__REGISTRO_NAO_ENCONTRADO_ENTIDADE_USUARIO)));
		LOGGER.trace("UsuarioRepository/findAll teve êxito");
		UsuarioDTO dto = orikaMapper.map(usuario, UsuarioDTO.class);
		return dto;
	}

	public List<UsuarioDTO> findByCpfAndSenha(String cpf, String senha) {
		List<Usuario> list = repository.findByCpfAndSenha(cpf, senha);
		LOGGER.trace("UsuarioRepository/findByCpfAndSenha(" + cpf + ", " + "*********" + ") teve êxito");
		List<UsuarioDTO> listDTO = orikaMapper.mapAsList(list, UsuarioDTO.class);
		return listDTO;
	}

}
