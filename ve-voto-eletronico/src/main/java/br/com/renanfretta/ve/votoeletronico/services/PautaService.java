package br.com.renanfretta.ve.votoeletronico.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.renanfretta.ve.commons.dtos.votoeletronico.PautaDTO;
import br.com.renanfretta.ve.votoeletronico.configs.OrikaMapper;
import br.com.renanfretta.ve.votoeletronico.entities.Pauta;
import br.com.renanfretta.ve.votoeletronico.repositories.PautaRepository;

@Service
@Validated
public class PautaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PautaService.class);
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private OrikaMapper orikaMapper;

	@Autowired
	private PautaRepository repository;

	public List<PautaDTO> findAll() {
		List<Pauta> list = repository.findAll();
		LOGGER.trace("PautaRepository/findAll teve êxito");
		List<PautaDTO> listDTO = orikaMapper.mapAsList(list, PautaDTO.class);
		return listDTO;
	}

	public PautaDTO findById(Long id) {
		Pauta pauta = repository.findById(id).orElseThrow();
		LOGGER.trace("PautaRepository/findById(" + id + ") teve êxito");
		PautaDTO dto = orikaMapper.map(pauta, PautaDTO.class);
		return dto;
	}

	public PautaDTO save(PautaDTO pautaDTO) throws JsonProcessingException {
		Pauta pauta = orikaMapper.map(pautaDTO, Pauta.class);
		pauta = repository.save(pauta);
		LOGGER.trace("PautaRepository/save(" + objectMapper.writeValueAsString(pautaDTO) + ") teve êxito");
		pautaDTO = findById(pauta.getId());
		return pautaDTO;
	}

	public PautaDTO deleteById(Long id) {
		Pauta pauta = repository.findById(id).orElseThrow();
		LOGGER.trace("PautaRepository/findById(" + id + ") teve êxito");
		repository.delete(pauta);
		LOGGER.trace("PautaRepository/deleteById(" + id + ") teve êxito");
		PautaDTO dto = orikaMapper.map(pauta, PautaDTO.class);
		return dto;
	}

}
