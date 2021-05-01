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
public class SessaoVotacao implements Serializable {

	private static final long serialVersionUID = -4984916939002330062L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_pauta")
	private Pauta pauta;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraInicio;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraFim;

}
