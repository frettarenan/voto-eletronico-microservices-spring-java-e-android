package br.com.renanfretta.ve.commons.dtos.votoeletronico;

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
public class PautaDTO implements Serializable {

	private static final long serialVersionUID = 6537686972443071228L;

	@EqualsAndHashCode.Include
	private Long id;

	private String descricao;
	
	private boolean encerrada;

}
