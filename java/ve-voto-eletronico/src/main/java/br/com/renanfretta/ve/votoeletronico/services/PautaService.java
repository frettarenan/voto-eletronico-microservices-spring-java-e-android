package br.com.renanfretta.ve.votoeletronico.services;

import br.com.renanfretta.ve.votoeletronico.configs.OrikaMapper;
import br.com.renanfretta.ve.votoeletronico.configs.properties.MessagesProperty;
import br.com.renanfretta.ve.votoeletronico.dtos.pauta.PautaInputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.pauta.PautaOutputDTO;
import br.com.renanfretta.ve.votoeletronico.entities.Pauta;
import br.com.renanfretta.ve.votoeletronico.enums.MessagesPropertyEnum;
import br.com.renanfretta.ve.votoeletronico.repositories.pauta.PautaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Validated
public class PautaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PautaService.class);
	
	private final ObjectMapper objectMapper;
	private final OrikaMapper orikaMapper;
	private final MessagesProperty messagesProperty;
	private final PautaRepository repository;

	public PautaService(ObjectMapper objectMapper, OrikaMapper orikaMapper, MessagesProperty messagesProperty, PautaRepository repository) {
		this.objectMapper = objectMapper;
		this.orikaMapper = orikaMapper;
		this.messagesProperty = messagesProperty;
		this.repository = repository;
	}

	public List<PautaOutputDTO> findAll() {
		List<Pauta> list = repository.findAll();
		LOGGER.trace("PautaRepository/findAll teve êxito");
		List<PautaOutputDTO> listDTO = orikaMapper.mapAsList(list, PautaOutputDTO.class);
		return listDTO;
	}

	public PautaOutputDTO findById(Long id) {
		Pauta pauta = repository.findById(id).orElseThrow(() -> new NoSuchElementException(messagesProperty.getMessage(MessagesPropertyEnum.ERRO__REGISTRO_NAO_ENCONTRADO_ENTIDADE_PAUTA)));
		LOGGER.trace("PautaRepository/findById(" + id + ") teve êxito");
		PautaOutputDTO dto = orikaMapper.map(pauta, PautaOutputDTO.class);
		return dto;
	}

	public PautaOutputDTO save(PautaInputDTO pautaInputDTO) throws JsonProcessingException {
		Pauta pauta = orikaMapper.map(pautaInputDTO, Pauta.class);
		pauta = repository.save(pauta);
		LOGGER.trace("PautaRepository/save(" + objectMapper.writeValueAsString(pauta) + ") teve êxito");
		PautaOutputDTO pautaOutputDTO = findById(pauta.getId());
		return pautaOutputDTO;
	}

	public PautaOutputDTO deleteById(Long id) {
		Pauta pauta = repository.findById(id).orElseThrow(() -> new NoSuchElementException(messagesProperty.getMessage(MessagesPropertyEnum.ERRO__REGISTRO_NAO_ENCONTRADO_ENTIDADE_PAUTA)));
		LOGGER.trace("PautaRepository/findById(" + id + ") teve êxito");
		repository.delete(pauta);
		LOGGER.trace("PautaRepository/deleteById(" + id + ") teve êxito");
		PautaOutputDTO dto = orikaMapper.map(pauta, PautaOutputDTO.class);
		return dto;
	}

}
