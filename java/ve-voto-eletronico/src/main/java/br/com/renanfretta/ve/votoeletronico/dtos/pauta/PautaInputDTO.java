package br.com.renanfretta.ve.votoeletronico.dtos.pauta;

import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PautaInputDTO implements Serializable {

	private static final long serialVersionUID = -9144640868242213195L;

	private String descricao;

}
