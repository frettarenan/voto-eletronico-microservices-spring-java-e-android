package br.com.renanfretta.ve.votoeletronico.dtos.voto;

import br.com.renanfretta.ve.votoeletronico.dtos.pauta.PautaOutputDTO;
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
