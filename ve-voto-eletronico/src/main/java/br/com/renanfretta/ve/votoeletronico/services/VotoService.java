package br.com.renanfretta.ve.votoeletronico.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import br.com.renanfretta.ve.commons.dtos.votoeletronico.sessaovotacao.SessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.voto.VotoInputDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.voto.VotoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.configs.OrikaMapper;
import br.com.renanfretta.ve.votoeletronico.entities.Pauta;
import br.com.renanfretta.ve.votoeletronico.entities.Voto;
import br.com.renanfretta.ve.votoeletronico.repositories.VotoRepository;

@Service
@Validated
public class VotoService {

	@Autowired
	private OrikaMapper orikaMapper;

	@Autowired
	private VotoRepository repository;
	
	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;

	private VotoOutputDTO findById(Long id) {
		Voto voto = repository.findById(id).orElseThrow();
		VotoOutputDTO dto = orikaMapper.map(voto, VotoOutputDTO.class);
		return dto;
	}
	
	public VotoOutputDTO save(VotoInputDTO votoInputDTO) {
		SessaoVotacaoOutputDTO sessaoVotacaoOutputDTO = sessaoVotacaoService.findById(votoInputDTO.getSessaoVotacao().getId());
		
		Voto voto = orikaMapper.map(votoInputDTO, Voto.class);
		voto.setPauta(Pauta.builder().id(sessaoVotacaoOutputDTO.getPauta().getId()).build());
		
		// FIXME: falta validação do horário de votação
		voto.setDataHoraVotacao(new Date());
		
		voto = repository.save(voto);
		VotoOutputDTO votoOutputDTO = findById(voto.getId());
		return votoOutputDTO;
	}

}
