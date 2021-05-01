package br.com.renanfretta.ve.votoeletronico.repositories.voto;

import br.com.renanfretta.ve.votoeletronico.dtos.voto.RelatorioVotosContabilizadosOutputDTO;

import java.util.List;

public interface VotoRepositoryCustom {

	List<RelatorioVotosContabilizadosOutputDTO> contabilizaVotos(Long idPauta);

}
