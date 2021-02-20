package br.com.renanfretta.ve.votoeletronico.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.renanfretta.ve.commons.dtos.votoeletronico.voto.VotoInputDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.voto.VotoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.exceptions.ErroTratadoRestException;
import br.com.renanfretta.ve.votoeletronico.services.VotoService;

@RestController
@RequestMapping(value = "/voto")
public class VotoResource {
	
	@Autowired
	private VotoService service;
	
	@PostMapping
	public ResponseEntity<VotoOutputDTO> salvar(@Valid @RequestBody VotoInputDTO votoInputDTO) throws ErroTratadoRestException {
		VotoOutputDTO votoOutputDTO = service.save(votoInputDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(votoOutputDTO);
	}

}
