package br.com.renanfretta.ve.votoeletronico.dtos.voto;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.querydsl.core.annotations.QueryProjection;

import br.com.renanfretta.ve.votoeletronico.dtos.pauta.PautaOutputDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class RelatorioVotosContabilizadosOutputDTO implements Serializable {

	private static final long serialVersionUID = -2502532290072614514L;

	@Id
	private Boolean voto;
	
	private Long totalVotos;
	
	private PautaOutputDTO pauta;

	@QueryProjection
	public RelatorioVotosContabilizadosOutputDTO(Boolean voto, Long totalVotos, PautaOutputDTO pauta) {
		this.voto = voto;
		this.totalVotos = totalVotos;
		this.pauta = pauta;
	}

}
