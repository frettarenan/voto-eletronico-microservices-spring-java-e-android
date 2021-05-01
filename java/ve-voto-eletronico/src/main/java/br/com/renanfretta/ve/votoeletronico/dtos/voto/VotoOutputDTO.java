package br.com.renanfretta.ve.votoeletronico.dtos.voto;

import br.com.renanfretta.ve.commons.dtos.usuario.UsuarioDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.pauta.PautaOutputDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotoOutputDTO implements Serializable {

	private static final long serialVersionUID = 8331417390821624623L;

	@EqualsAndHashCode.Include
	private Long id;

	private PautaOutputDTO pauta;

	private UsuarioDTO usuario;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dataHoraVotacao;

	private Boolean voto;

}
