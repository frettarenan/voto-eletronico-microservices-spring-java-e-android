package br.com.renanfretta.ve.votoeletronico.dtos.pauta;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Builder
@Entity
public class PautaOutputDTO implements Serializable {

	private static final long serialVersionUID = 6537686972443071228L;

	@Id
	@EqualsAndHashCode.Include
	private Long id;

	private String descricao;

	@QueryProjection
	public PautaOutputDTO(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

}
