package br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao;

import java.io.Serializable;

import br.com.renanfretta.ve.votoeletronico.dtos.pauta.PautaOutputDTO;
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
public class SessaoVotacaoInputDTO implements Serializable {

	private static final long serialVersionUID = -6687765329932958403L;

	private PautaOutputDTO pauta;

	private Integer minutosParaVotacao;

}
