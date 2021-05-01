package br.com.renanfretta.ve.votoeletronico.repositories.voto;

import br.com.renanfretta.ve.votoeletronico.dtos.voto.RelatorioVotosContabilizadosPorPautaOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO;

import java.util.List;

public interface VotoRepositoryCustom {

	List<RelatorioVotosContabilizadosPorPautaOutputDTO> contabilizaVotosPorPauta(Long idPauta);

	List<RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO> contabilizaVotosPorSessaoVotacao(Long idSessaoVotacao);

}
