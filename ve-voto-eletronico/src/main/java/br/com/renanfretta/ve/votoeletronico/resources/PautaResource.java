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

import br.com.renanfretta.ve.votoeletronico.dtos.pauta.PautaInputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.pauta.PautaOutputDTO;
import br.com.renanfretta.ve.votoeletronico.services.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/pauta")
public class PautaResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(PautaResource.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PautaService service;

	@Operation(summary = "findAll", description = "Lista todas as pautas cadastradas")
	@ApiResponses(value = { //
			@ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PautaOutputDTO.class)))), //
			@ApiResponse(responseCode = "204", description = "Sem resultados"), //
			@ApiResponse(responseCode = "401", description = "Não autorizado"), //
			@ApiResponse(responseCode = "403", description = "Não possui permissão para acessar o recurso"), //
			@ApiResponse(responseCode = "404", description = "Não encontrado") //
	})
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

	@Operation(summary = "findById", description = "Consulta pauta pelo ID")
	@ApiResponses(value = { //
			@ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(schema = @Schema(implementation = PautaOutputDTO.class))), //
			@ApiResponse(responseCode = "401", description = "Não autorizado"), //
			@ApiResponse(responseCode = "403", description = "Não possui permissão para acessar o recurso"), //
			@ApiResponse(responseCode = "404", description = "Não encontrado") //
	})
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

	@Operation(summary = "salvar", description = "Cadastra uma nova pauta")
	@ApiResponses(value = { //
			@ApiResponse(responseCode = "201", description = "Sucesso", content = @Content(schema = @Schema(implementation = PautaOutputDTO.class))), //
			@ApiResponse(responseCode = "401", description = "Não autorizado"), //
			@ApiResponse(responseCode = "403", description = "Não possui permissão para acessar o recurso"), //
			@ApiResponse(responseCode = "404", description = "Não encontrado") //
	})
	@PostMapping
	public ResponseEntity<PautaOutputDTO> salvar(@Valid @RequestBody PautaInputDTO pautaInputDTO) throws JsonProcessingException {
		LOGGER.trace("PautaResource/salvar( " + objectMapper.writeValueAsString(pautaInputDTO) + ") foi chamado");
		PautaOutputDTO pautaOutputDTO = service.save(pautaInputDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(pautaOutputDTO);
	}

	@Operation(summary = "deleteById", description = "Deleta pauta pelo ID")
	@ApiResponses(value = { //
			@ApiResponse(responseCode = "201", description = "Sucesso", content = @Content(schema = @Schema(implementation = PautaOutputDTO.class))), //
			@ApiResponse(responseCode = "204", description = "Sem conteúdo"), //
			@ApiResponse(responseCode = "401", description = "Não autorizado"), //
			@ApiResponse(responseCode = "403", description = "Não possui permissão para acessar o recurso") //
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<PautaOutputDTO> deleteById(@PathVariable Long id) {
		LOGGER.trace("PautaResource/deleteById(" + id + ") foi chamado");
		try {
			PautaOutputDTO pautaDTO = service.deleteById(id);
			return ResponseEntity.ok(pautaDTO);
		} catch (NoSuchElementException e) {
			LOGGER.warn("PautaResource/deleteById(" + id + ") NoSuchElementException");
			return ResponseEntity.badRequest().build();
		}
	}

}
