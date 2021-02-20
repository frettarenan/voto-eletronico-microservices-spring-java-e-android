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

import br.com.renanfretta.ve.commons.dtos.votoeletronico.voto.VotoInputDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.voto.VotoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.exceptions.ErroTratadoRestException;
import br.com.renanfretta.ve.votoeletronico.services.VotoService;

@RestController
@RequestMapping(value = "/voto")
public class VotoResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(VotoResource.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private VotoService service;

	@PostMapping
	public ResponseEntity<VotoOutputDTO> salvar(@Valid @RequestBody VotoInputDTO votoInputDTO) throws ErroTratadoRestException, JsonProcessingException {
		LOGGER.trace("VotoResource/salvar executado com o seguinte parâmetro entrada: votoInputDTO: " + objectMapper.writeValueAsString(votoInputDTO));
		VotoOutputDTO votoOutputDTO = service.save(votoInputDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(votoOutputDTO);
	}

}
