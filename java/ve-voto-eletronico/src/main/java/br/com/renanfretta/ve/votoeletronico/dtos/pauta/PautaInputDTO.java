package br.com.renanfretta.ve.votoeletronico.dtos.pauta;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PautaInputDTO implements Serializable {

	private static final long serialVersionUID = -9144640868242213195L;

	private String descricao;

}
