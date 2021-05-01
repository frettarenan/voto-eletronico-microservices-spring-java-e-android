package br.com.renanfretta.ve.votoeletronico.dtos.voto;

import br.com.renanfretta.ve.commons.dtos.usuario.UsuarioDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoOutputDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
