package br.com.renanfretta.ve.votoeletronico.repositories.pauta;

import br.com.renanfretta.ve.votoeletronico.entities.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long>, JpaSpecificationExecutor<Pauta>, QuerydslPredicateExecutor<Pauta> {

}
