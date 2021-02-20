package br.com.renanfretta.ve.votoeletronico.services;

import java.util.List;

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
	
	@Autowired
	private OrikaMapper orikaMapper;

	@Autowired
	private UsuarioRepository repository;
	
	public UsuarioDTO findById(Long id) {
		Usuario usuario = repository.findById(id).orElseThrow();
		UsuarioDTO dto = orikaMapper.map(usuario, UsuarioDTO.class);
		return dto;
	}
	
	public List<UsuarioDTO> findByCpfAndSenha(String cpf, String senha) {
		List<Usuario> list = repository.findByCpfAndSenha(cpf, senha);
		List<UsuarioDTO> listDTO = orikaMapper.mapAsList(list, UsuarioDTO.class);
		return listDTO;
	}
	
	
	
}
