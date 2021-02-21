package br.com.renanfretta.ve.votoeletronico.resources;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.renanfretta.ve.votoeletronico.dtos.pauta.PautaOutputDTO;
import br.com.renanfretta.ve.votoeletronico.services.PautaService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/pauta")
public class PautaResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(PautaResource.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PautaService service;

	@ApiOperation(value = "Lista todas as pautas cadastradas")
	@GetMapping
	public ResponseEntity<List<PautaOutputDTO>> findAll() {
		LOGGER.trace("PautaResource/findAll foi chamado");
		List<PautaOutputDTO> list = service.findAll();
		if (list == null || list.isEmpty()) {
			LOGGER.trace("PautaResource/findAll noContent");
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(list);
	}

	@ApiOperation(value = "Consulta pauta pelo ID ")
	@GetMapping(value = "/{id}")
	public ResponseEntity<PautaOutputDTO> findById(@PathVariable Long id) {
		LOGGER.trace("PautaResource/findById(" + id + ") foi chamado");
		try {
			PautaOutputDTO pautaDTO = service.findById(id);
			return ResponseEntity.ok(pautaDTO);
		} catch (NoSuchElementException e) {
			LOGGER.warn("PautaResource/findById(" + id + ") NoSuchElementException");
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Cadastra uma nova pauta")
	@PostMapping
	public ResponseEntity<PautaOutputDTO> salvar(@Valid @RequestBody PautaOutputDTO pautaDTO) throws JsonProcessingException {
		LOGGER.trace("PautaResource/salvar( " + objectMapper.writeValueAsString(pautaDTO) + ") foi chamado");
		pautaDTO = service.save(pautaDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(pautaDTO);
	}

	@ApiOperation(value = "Deleta pauta pelo ID ")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<PautaOutputDTO> deleteById(@PathVariable Long id) {
		LOGGER.trace("PautaResource/deleteById(" + id + ") foi chamado");
		try {
			PautaOutputDTO pautaDTO = service.deleteById(id);
			return ResponseEntity.ok(pautaDTO);
		} catch (NoSuchElementException e) {
			LOGGER.warn("PautaResource/deleteById(" + id + ") NoSuchElementException");
			return ResponseEntity.notFound().build();
		}
	}

}
