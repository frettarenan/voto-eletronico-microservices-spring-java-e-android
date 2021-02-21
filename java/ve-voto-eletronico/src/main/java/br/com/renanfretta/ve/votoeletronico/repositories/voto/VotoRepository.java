package br.com.renanfretta.ve.votoeletronico.repositories.voto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import br.com.renanfretta.ve.votoeletronico.entities.Voto;

@Repository
public interface VotoRepository extends VotoRepositoryCustom, JpaRepository<Voto, Long>, JpaSpecificationExecutor<Voto>, QuerydslPredicateExecutor<Voto> {

}
