package br.com.renanfretta.ve.votoeletronico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.renanfretta.ve.votoeletronico.entities.Voto;

public interface VotoRepository extends JpaRepository<Voto, Long> {

}
