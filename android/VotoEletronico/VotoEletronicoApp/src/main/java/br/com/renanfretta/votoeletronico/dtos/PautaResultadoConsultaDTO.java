package br.com.renanfretta.votoeletronico.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PautaResultadoConsultaDTO {

    private Integer id;
    private String descricao;

}
