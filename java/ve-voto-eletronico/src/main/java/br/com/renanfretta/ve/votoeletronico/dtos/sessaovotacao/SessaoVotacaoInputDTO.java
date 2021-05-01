package br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao;

import br.com.renanfretta.ve.votoeletronico.dtos.pauta.PautaOutputDTO;
import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessaoVotacaoInputDTO implements Serializable {

	private static final long serialVersionUID = -6687765329932958403L;

	private PautaOutputDTO pauta;

	private Integer minutosParaVotacao;

}
