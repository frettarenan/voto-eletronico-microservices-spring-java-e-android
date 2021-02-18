package br.com.renanfretta.ve.votoeletronico.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.renanfretta.ve.commons.dtos.votoeletronico.SessaoVotacaoDTO;
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
	
	private SessaoVotacaoDTO findById(Long id) {
		SessaoVotacao sessaoVotacao = repository.findById(id).orElseThrow();
		SessaoVotacaoDTO dto = orikaMapper.map(sessaoVotacao, SessaoVotacaoDTO.class);
		return dto;
	}
	
	public SessaoVotacaoDTO save(SessaoVotacaoDTO sessaoVotacaoDTO) {
		SessaoVotacao sessaoVotacao = orikaMapper.map(sessaoVotacaoDTO, SessaoVotacao.class);
		sessaoVotacao = repository.save(sessaoVotacao);
		sessaoVotacaoDTO = findById(sessaoVotacao.getId());
		return sessaoVotacaoDTO;
	}
	
}
