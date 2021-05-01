package br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao;

import br.com.renanfretta.ve.votoeletronico.dtos.pauta.PautaOutputDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SessaoVotacaoOutputDTO implements Serializable {

    private static final long serialVersionUID = -558417135085492895L;

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    private PautaOutputDTO pauta;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataHoraInicio;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataHoraFim;

    @QueryProjection
    public SessaoVotacaoOutputDTO(Long id, Date dataHoraInicio, Date dataHoraFim) {
        this.id = id;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
    }

}
