package br.com.renanfretta.ve.commons.dtos.votoeletronico.voto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.renanfretta.ve.commons.dtos.usuario.UsuarioDTO;
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
public class VotoOutputDTO implements Serializable {

	private static final long serialVersionUID = 8331417390821624623L;

	@EqualsAndHashCode.Include
	private Long id;

	private PautaDTO pauta;

	private UsuarioDTO usuario;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dataHoraVotacao;

	private Boolean voto;

}
