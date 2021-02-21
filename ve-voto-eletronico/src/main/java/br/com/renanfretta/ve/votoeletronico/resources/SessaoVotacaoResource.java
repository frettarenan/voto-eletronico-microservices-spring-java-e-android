package br.com.renanfretta.ve.votoeletronico.resources;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoInputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.services.SessaoVotacaoService;

@RestController
@RequestMapping(value = "/sessao-votacao")
public class SessaoVotacaoResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessaoVotacaoResource.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SessaoVotacaoService service;

	@PostMapping
	public ResponseEntity<SessaoVotacaoOutputDTO> salvarEIniciar(@Valid @RequestBody SessaoVotacaoInputDTO sessaoVotacaoInputDTO) throws JsonProcessingException {
		LOGGER.trace("SessaoVotacaoResource/salvarEIniciar executado com o seguinte parâmetro entrada: sessaoVotacaoInputDTO: " + objectMapper.writeValueAsString(sessaoVotacaoInputDTO));
		SessaoVotacaoOutputDTO sessaoVotacaoOutputDTO = service.save(sessaoVotacaoInputDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(sessaoVotacaoOutputDTO);
	}

}
