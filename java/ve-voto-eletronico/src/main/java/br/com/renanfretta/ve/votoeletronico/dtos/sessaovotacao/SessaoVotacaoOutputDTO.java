package br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class SessaoVotacaoOutputDTO implements Serializable {

	private static final long serialVersionUID = -558417135085492895L;

	@EqualsAndHashCode.Include
	private Long id;

	private PautaOutputDTO pauta;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dataHoraInicio;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dataHoraFim;

}
