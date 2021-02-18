package br.com.renanfretta.ve.commons.dtos.votoeletronico.sessaovotacao;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.renanfretta.ve.commons.dtos.votoeletronico.PautaDTO;
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

	private PautaDTO pauta;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dataHoraInicio;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dataHoraFim;

}
