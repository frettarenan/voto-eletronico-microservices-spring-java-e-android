package br.com.renanfretta.ve.votoeletronico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.renanfretta.ve.votoeletronico.entities.SessaoVotacao;

public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {

}