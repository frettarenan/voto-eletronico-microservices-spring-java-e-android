package br.com.renanfretta.ve.votoeletronico.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanfretta.ve.commons.dtos.votoeletronico.sessaovotacao.SessaoVotacaoInputDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.sessaovotacao.SessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.services.SessaoVotacaoService;

@RestController
@RequestMapping(value = "/sessao-votacao")
public class SessaoVotacaoResource {
	
	@Autowired
	private SessaoVotacaoService service;

	@PostMapping
	public ResponseEntity<SessaoVotacaoOutputDTO> salvarEIniciar(@Valid @RequestBody SessaoVotacaoInputDTO sessaoVotacaoInputDTO) {
		SessaoVotacaoOutputDTO sessaoVotacaoOutputDTO = service.save(sessaoVotacaoInputDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(sessaoVotacaoOutputDTO);
	}

}
