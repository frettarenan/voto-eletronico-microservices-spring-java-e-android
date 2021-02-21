package br.com.renanfretta.ve.votoeletronico.resources;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.renanfretta.ve.votoeletronico.dtos.voto.RelatorioVotosContabilizadosOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.VotoInputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.VotoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.exceptions.ErroTratadoRestException;
import br.com.renanfretta.ve.votoeletronico.services.VotoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/voto")
public class VotoResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(VotoResource.class);

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private VotoService service;

	@ApiOperation(value = "Salva o voto")
	@PostMapping
	public ResponseEntity<VotoOutputDTO> votar(@Valid @RequestBody VotoInputDTO votoInputDTO) throws ErroTratadoRestException, JsonProcessingException {
		LOGGER.trace("VotoResource/votar executado com o seguinte parâmetro entrada: votoInputDTO: " + objectMapper.writeValueAsString(votoInputDTO));
		VotoOutputDTO votoOutputDTO = service.save(votoInputDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(votoOutputDTO);
	}

	@ApiOperation(value = "Consulta total de votos pelo ID da pauta informada")
	@GetMapping(value = "/contabilizados/pauta/{idPauta}")
	public ResponseEntity<List<RelatorioVotosContabilizadosOutputDTO>> contabilizaVotos(@PathVariable Long idPauta) {
		LOGGER.trace("VotoResource/contabilizaVotos(" + idPauta + ") foi chamado");
		List<RelatorioVotosContabilizadosOutputDTO> list = service.contabilizaVotos(idPauta);
		// FIXME: Implementar: Pauta sem sessao de votacao
		// FIXME: Implementar: sem resultados
		return ResponseEntity.ok(list);
	}

}
