package br.com.renanfretta.ve.votoeletronico.dtos.voto;

import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoOutputDTO;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO implements Serializable {

	private static final long serialVersionUID = -3162602567651794704L;

	@Id
	private Boolean voto;

	private Long totalVotos;

	private SessaoVotacaoOutputDTO sessaoVotacao;

	@QueryProjection
	public RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO(Boolean voto, Long totalVotos, SessaoVotacaoOutputDTO sessaoVotacao) {
		this.voto = voto;
		this.totalVotos = totalVotos;
		this.sessaoVotacao = sessaoVotacao;
	}

}
