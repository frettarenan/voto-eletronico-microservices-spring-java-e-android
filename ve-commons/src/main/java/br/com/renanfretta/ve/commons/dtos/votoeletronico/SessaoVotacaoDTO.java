package br.com.renanfretta.ve.commons.dtos.votoeletronico;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class SessaoVotacaoDTO implements Serializable {

	private static final long serialVersionUID = -4923818082363643288L;

	@EqualsAndHashCode.Include
	private Long id;

	private PautaDTO pauta;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dataHoraInicio;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dataHoraFim;

}
