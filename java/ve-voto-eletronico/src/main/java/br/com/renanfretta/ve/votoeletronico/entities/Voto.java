package br.com.renanfretta.ve.votoeletronico.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Voto implements Serializable {

	private static final long serialVersionUID = 6345130418351526135L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_pauta")
	private Pauta pauta;
	
	@ManyToOne
	@JoinColumn(name = "id_sessao_votacao")
	private SessaoVotacao sessaoVotacao;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraVotacao;

	private boolean voto;
	
}
