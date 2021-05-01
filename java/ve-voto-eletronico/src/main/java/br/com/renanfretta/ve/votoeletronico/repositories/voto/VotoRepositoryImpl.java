package br.com.renanfretta.ve.votoeletronico.repositories.voto;

import br.com.renanfretta.ve.votoeletronico.dtos.pauta.QPautaOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.QSessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.QRelatorioVotosContabilizadosPorPautaOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.QRelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.RelatorioVotosContabilizadosPorPautaOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.entities.QPauta;
import br.com.renanfretta.ve.votoeletronico.entities.QSessaoVotacao;
import br.com.renanfretta.ve.votoeletronico.entities.QVoto;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class VotoRepositoryImpl implements VotoRepositoryCustom {

	private final EntityManager em;

	private static final QVoto _voto = QVoto.voto1;
	private static final QPauta _pauta = QPauta.pauta;
	private static final QSessaoVotacao _sessaoVotacao = QSessaoVotacao.sessaoVotacao;

	public VotoRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<RelatorioVotosContabilizadosPorPautaOutputDTO> contabilizaVotosPorPauta(Long idPauta) {
		JPAQuery<RelatorioVotosContabilizadosPorPautaOutputDTO> query = new JPAQuery<>(em);

		query.select(QRelatorioVotosContabilizadosPorPautaOutputDTO.create( //
				_voto.voto, //
				_voto.count(), //
				QPautaOutputDTO.create(_pauta.id, _pauta.descricao)));

		query.from(_voto);
		query.join(_voto.pauta, _pauta).fetch();
		query.where(_voto.pauta.id.eq(idPauta));
		query.groupBy(_voto.voto, _pauta.id, _pauta.descricao);

		return query.fetch();
	}

	@Override
	public List<RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO> contabilizaVotosPorSessaoVotacao(Long idSessaoVotacao) {
		JPAQuery<RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO> query = new JPAQuery<>(em);

		query.select(QRelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO.create( //
				_voto.voto, //
				_voto.count(), //
				QSessaoVotacaoOutputDTO.create(_sessaoVotacao.id, _sessaoVotacao.dataHoraInicio, _sessaoVotacao.dataHoraFim)));

		query.from(_voto);
		query.join(_voto.sessaoVotacao, _sessaoVotacao).fetch();
		query.where(_sessaoVotacao.id.eq(idSessaoVotacao));
		query.groupBy(_voto.voto, _sessaoVotacao.id, _sessaoVotacao.dataHoraInicio, _sessaoVotacao.dataHoraFim);

		return query.fetch();
	}

}
