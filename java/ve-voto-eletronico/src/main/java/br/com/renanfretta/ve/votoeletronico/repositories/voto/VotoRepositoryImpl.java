package br.com.renanfretta.ve.votoeletronico.repositories.voto;

import br.com.renanfretta.ve.votoeletronico.dtos.pauta.QPautaOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.QRelatorioVotosContabilizadosOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.RelatorioVotosContabilizadosOutputDTO;
import br.com.renanfretta.ve.votoeletronico.entities.QPauta;
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

	public VotoRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<RelatorioVotosContabilizadosOutputDTO> contabilizaVotos(Long idPauta) {
		JPAQuery<RelatorioVotosContabilizadosOutputDTO> query = new JPAQuery<>(em);

		query.select(QRelatorioVotosContabilizadosOutputDTO.create( //
				_voto.voto, //
				_voto.count(), //
				QPautaOutputDTO.create(_pauta.id, _pauta.descricao)));

		query.from(_voto);
		query.join(_voto.pauta, _pauta).fetch();
		query.where(_voto.pauta.id.eq(idPauta));
		query.groupBy(_voto.voto, _pauta.id, _pauta.descricao);

		return query.fetch();
	}

}
