package br.com.renanfretta.ve.votoeletronico.repositories.sessaovotacao;

import br.com.renanfretta.ve.votoeletronico.entities.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long>, JpaSpecificationExecutor<SessaoVotacao>, QuerydslPredicateExecutor<SessaoVotacao> {

}
