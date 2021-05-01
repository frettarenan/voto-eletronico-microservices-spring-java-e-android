package br.com.renanfretta.ve.commons.dtos.usuario;

import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = -5764553198889588201L;

	@EqualsAndHashCode.Include
	private Long id;

	private String nome;

	private String cpf;

	private String senha;

}
