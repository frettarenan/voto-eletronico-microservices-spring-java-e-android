package br.com.renanfretta.ve.votoeletronico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.renanfretta.ve.votoeletronico.entities.Pauta;

public interface PautaRepository extends JpaRepository<Pauta, Long> {

}
