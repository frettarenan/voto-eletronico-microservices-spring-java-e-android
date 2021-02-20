package br.com.renanfretta.ve.votoeletronico.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.renanfretta.ve.commons.dtos.usuario.UsuarioDTO;
import br.com.renanfretta.ve.votoeletronico.configs.OrikaMapper;
import br.com.renanfretta.ve.votoeletronico.entities.Usuario;
import br.com.renanfretta.ve.votoeletronico.repositories.UsuarioRepository;

@Service
@Validated
public class UsuarioService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private OrikaMapper orikaMapper;

	@Autowired
	private UsuarioRepository repository;

	public UsuarioDTO findById(Long id) {
		Usuario usuario = repository.findById(id).orElseThrow();
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
