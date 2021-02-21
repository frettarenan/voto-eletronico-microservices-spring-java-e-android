package br.com.renanfretta.ve.votoeletronico.services;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.renanfretta.ve.votoeletronico.configs.OrikaMapper;
import br.com.renanfretta.ve.votoeletronico.configs.properties.YamlConfig;
import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoInputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.entities.SessaoVotacao;
import br.com.renanfretta.ve.votoeletronico.repositories.sessaovotacao.SessaoVotacaoRepository;

@Service
@Validated
public class SessaoVotacaoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SessaoVotacaoService.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private OrikaMapper orikaMapper;
	
	@Autowired
	private YamlConfig yamlConfig;

	@Autowired
	private SessaoVotacaoRepository repository;
	
	public SessaoVotacaoOutputDTO findById(Long id) {
		SessaoVotacao sessaoVotacao = repository.findById(id).orElseThrow();
		LOGGER.trace("SessaoVotacaoRepository/findById(" + id + ") teve êxito");
		SessaoVotacaoOutputDTO dto = orikaMapper.map(sessaoVotacao, SessaoVotacaoOutputDTO.class);
		return dto;
	}
	
	public SessaoVotacaoOutputDTO save(SessaoVotacaoInputDTO sessaoVotacaoInputDTO) throws JsonProcessingException {
		SessaoVotacao sessaoVotacao = orikaMapper.map(sessaoVotacaoInputDTO, SessaoVotacao.class);
		
		if (sessaoVotacaoInputDTO.getMinutosParaVotacao() == null)
			sessaoVotacaoInputDTO.setMinutosParaVotacao(yamlConfig.getQuantidademinutosessaovotacaopadrao());
		
		Date dataInicioSessao = new Date();
		Date dataFimSessao = DateUtils.addMinutes(dataInicioSessao, sessaoVotacaoInputDTO.getMinutosParaVotacao());
		
		sessaoVotacao.setDataHoraInicio(dataInicioSessao);
		sessaoVotacao.setDataHoraFim(dataFimSessao);
		
		sessaoVotacao = repository.save(sessaoVotacao);
		LOGGER.trace("SessaoVotacaoRepository/save(" + objectMapper.writeValueAsString(sessaoVotacao) + ") teve êxito");
		
		SessaoVotacaoOutputDTO sessaoVotacaoOutputDTO = findById(sessaoVotacao.getId());
		return sessaoVotacaoOutputDTO;
	}
	
}
