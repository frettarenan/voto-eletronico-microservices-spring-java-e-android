package br.com.renanfretta.ve.votoeletronico.repositories.voto;

import java.util.List;

import br.com.renanfretta.ve.votoeletronico.dtos.voto.RelatorioVotosContabilizadosOutputDTO;

public interface VotoRepositoryCustom {

	List<RelatorioVotosContabilizadosOutputDTO> contabilizaVotos(Long idPauta);

}
