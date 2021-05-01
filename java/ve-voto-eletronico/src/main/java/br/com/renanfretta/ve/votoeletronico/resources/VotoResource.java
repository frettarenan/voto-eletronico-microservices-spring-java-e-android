package br.com.renanfretta.ve.votoeletronico.resources;

import br.com.renanfretta.ve.votoeletronico.dtos.voto.RelatorioVotosContabilizadosPorPautaOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.VotoInputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.VotoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.exceptions.ErroTratadoRestException;
import br.com.renanfretta.ve.votoeletronico.services.VotoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/voto")
public class VotoResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(VotoResource.class);

	private final ObjectMapper objectMapper;
	private final VotoService service;

	public VotoResource(ObjectMapper objectMapper, VotoService service) {
		this.objectMapper = objectMapper;
		this.service = service;
	}

	@Operation(summary = "salvar", description = "Registra o voto")
	@ApiResponses(value = { //
			@ApiResponse(responseCode = "201", description = "Recurso criado", content = @Content(schema = @Schema(implementation = VotoOutputDTO.class))), //
			@ApiResponse(responseCode = "401", description = "Não autorizado"), //
			@ApiResponse(responseCode = "403", description = "Não possui permissão para acessar o recurso"), //
			@ApiResponse(responseCode = "404", description = "Não encontrado") //
	})
	@PostMapping
	public ResponseEntity<VotoOutputDTO> votar(@Valid @RequestBody VotoInputDTO votoInputDTO) throws ErroTratadoRestException, JsonProcessingException {
		LOGGER.trace("VotoResource/votar executado com o seguinte parâmetro entrada: votoInputDTO: " + objectMapper.writeValueAsString(votoInputDTO));
		VotoOutputDTO votoOutputDTO = service.save(votoInputDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(votoOutputDTO);
	}

	@Operation(summary = "listaVotosContabilizadosPorPauta", description = "Consulta total de votos pelo ID da pauta informada")
	@ApiResponses(value = { //
			@ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(schema = @Schema(implementation = RelatorioVotosContabilizadosPorPautaOutputDTO.class))), //
			@ApiResponse(responseCode = "401", description = "Não autorizado"), //
			@ApiResponse(responseCode = "403", description = "Não possui permissão para acessar o recurso"), //
			@ApiResponse(responseCode = "404", description = "Não encontrado") //
	})
	@GetMapping(value = "/contabilizados/pauta/{idPauta}")
	public ResponseEntity<List<RelatorioVotosContabilizadosPorPautaOutputDTO>> listaVotosContabilizadosPorPauta(@PathVariable Long idPauta) {
		LOGGER.trace("VotoResource/listaVotosContabilizadosPorPauta(" + idPauta + ") foi chamado");
		List<RelatorioVotosContabilizadosPorPautaOutputDTO> list = service.contabilizaVotosPorPauta(idPauta);
		// FIXME: Implementar: Pauta sem sessao de votacao
		// FIXME: Implementar: sem resultados
		return ResponseEntity.ok(list);
	}

	@Operation(summary = "listaVotosContabilizadosPorSessaoVotacao", description = "Consulta total de votos pelo ID da sessão de votação informada")
	@ApiResponses(value = { //
			@ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(schema = @Schema(implementation = RelatorioVotosContabilizadosPorPautaOutputDTO.class))), //
			@ApiResponse(responseCode = "401", description = "Não autorizado"), //
			@ApiResponse(responseCode = "403", description = "Não possui permissão para acessar o recurso"), //
			@ApiResponse(responseCode = "404", description = "Não encontrado") //
	})
	@GetMapping(value = "/contabilizados/sessao-votacao/{idSessaoVotacao}")
	public ResponseEntity<List<RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO>> listaVotosContabilizadosPorSessaoVotacao(@PathVariable Long idSessaoVotacao) {
		LOGGER.trace("VotoResource/listaVotosContabilizadosPorSessaoVotacao(" + idSessaoVotacao + ") foi chamado");
		List<RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO> list = service.contabilizaVotosPorSessaoVotacao(idSessaoVotacao);
		// FIXME: Implementar: Pauta sem sessao de votacao
		// FIXME: Implementar: sem resultados
		return ResponseEntity.ok(list);
	}

}
