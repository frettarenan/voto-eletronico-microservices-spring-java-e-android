package br.com.renanfretta.ve.votoeletronico.services;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.renanfretta.ve.commons.dtos.votoeletronico.sessaovotacao.SessaoVotacaoInputDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.sessaovotacao.SessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.configs.OrikaMapper;
import br.com.renanfretta.ve.votoeletronico.entities.SessaoVotacao;
import br.com.renanfretta.ve.votoeletronico.repositories.SessaoVotacaoRepository;

@Service
@Validated
public class SessaoVotacaoService {
	
	@Autowired
	private OrikaMapper orikaMapper;

	@Autowired
	private SessaoVotacaoRepository repository;
	
	public SessaoVotacaoOutputDTO findById(Long id) {
		SessaoVotacao sessaoVotacao = repository.findById(id).orElseThrow();
		SessaoVotacaoOutputDTO dto = orikaMapper.map(sessaoVotacao, SessaoVotacaoOutputDTO.class);
		return dto;
	}
	
	public SessaoVotacaoOutputDTO save(SessaoVotacaoInputDTO sessaoVotacaoInputDTO) {
		SessaoVotacao sessaoVotacao = orikaMapper.map(sessaoVotacaoInputDTO, SessaoVotacao.class);
		
		Date dataInicioSessao = new Date();
		Date dataFimSessao = DateUtils.addMinutes(dataInicioSessao, sessaoVotacaoInputDTO.getMinutosParaVotacao());
		
		sessaoVotacao.setDataHoraInicio(dataInicioSessao);
		sessaoVotacao.setDataHoraFim(dataFimSessao);
		
		sessaoVotacao = repository.save(sessaoVotacao);
		
		SessaoVotacaoOutputDTO sessaoVotacaoOutputDTO = findById(sessaoVotacao.getId());
		return sessaoVotacaoOutputDTO;
	}
	
}
