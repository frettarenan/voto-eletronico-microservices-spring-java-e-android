package br.com.renanfretta.ve.votoeletronico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.renanfretta.ve.commons.dtos.votoeletronico.VotoDTO;
import br.com.renanfretta.ve.votoeletronico.configs.OrikaMapper;
import br.com.renanfretta.ve.votoeletronico.entities.Voto;
import br.com.renanfretta.ve.votoeletronico.repositories.VotoRepository;

@Service
@Validated
public class VotoService {

	@Autowired
	private OrikaMapper orikaMapper;

	@Autowired
	private VotoRepository repository;

	private VotoDTO findById(Long id) {
		Voto voto = repository.findById(id).orElseThrow();
		VotoDTO dto = orikaMapper.map(voto, VotoDTO.class);
		return dto;
	}
	
	public VotoDTO save(VotoDTO votoDTO) {
		Voto voto = orikaMapper.map(votoDTO, Voto.class);
		voto = repository.save(voto);
		votoDTO = findById(voto.getId());
		return votoDTO;
	}

}
