package br.com.renanfretta.ve.commons.dtos.votoeletronico.voto;

import java.io.Serializable;

import br.com.renanfretta.ve.commons.dtos.usuario.UsuarioDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.sessaovotacao.SessaoVotacaoOutputDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotoInputDTO implements Serializable {

	private static final long serialVersionUID = 9081576405220883084L;

	private SessaoVotacaoOutputDTO sessaoVotacao;

	private UsuarioDTO usuario;

	private Boolean voto;

}
