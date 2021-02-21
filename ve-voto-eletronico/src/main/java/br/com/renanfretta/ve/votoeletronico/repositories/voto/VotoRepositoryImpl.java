package br.com.renanfretta.ve.votoeletronico.repositories.voto;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import br.com.renanfretta.ve.votoeletronico.dtos.pauta.QPautaOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.QRelatorioVotosContabilizadosOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.RelatorioVotosContabilizadosOutputDTO;
import br.com.renanfretta.ve.votoeletronico.entities.QPauta;
import br.com.renanfretta.ve.votoeletronico.entities.QVoto;

@Repository
public class VotoRepositoryImpl implements VotoRepositoryCustom {

	@Autowired
	private EntityManager em;

	private static final QVoto _voto = QVoto.voto1;
	private static final QPauta _pauta = QPauta.pauta;

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
