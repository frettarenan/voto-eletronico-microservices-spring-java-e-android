package br.com.renanfretta.ve.votoeletronico.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.renanfretta.ve.commons.dtos.votoeletronico.PautaDTO;
import br.com.renanfretta.ve.votoeletronico.configs.OrikaMapper;
import br.com.renanfretta.ve.votoeletronico.entities.Pauta;
import br.com.renanfretta.ve.votoeletronico.repositories.PautaRepository;

@Service
@Validated
public class PautaService {

	@Autowired
	private OrikaMapper orikaMapper;

	@Autowired
	private PautaRepository repository;

	public List<PautaDTO> findAll() {
		List<Pauta> list = repository.findAll();
		List<PautaDTO> listDTO = orikaMapper.mapAsList(list, PautaDTO.class);
		return listDTO;
	}

	public PautaDTO findById(Long id) {
		Pauta pauta = repository.findById(id).orElseThrow();
		PautaDTO dto = orikaMapper.map(pauta, PautaDTO.class);
		return dto;
	}

	public PautaDTO save(PautaDTO pautaDTO) {
		Pauta pauta = orikaMapper.map(pautaDTO, Pauta.class);
		pauta = repository.save(pauta);
		pautaDTO = findById(pauta.getId());
		return pautaDTO;
	}

	public PautaDTO deleteById(Long id) {
		Pauta pauta = repository.findById(id).orElseThrow();
		repository.delete(pauta);
		PautaDTO dto = orikaMapper.map(pauta, PautaDTO.class);
		return dto;
	}

}
