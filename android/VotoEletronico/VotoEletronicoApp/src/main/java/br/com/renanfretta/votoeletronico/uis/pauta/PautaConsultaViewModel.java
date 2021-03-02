package br.com.renanfretta.votoeletronico.uis.pauta;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.renanfretta.votoeletronico.dtos.PautaResultadoConsultaDTO;
import lombok.Data;

@Data
public class PautaConsultaViewModel {

    private MutableLiveData<List<PautaResultadoConsultaDTO>> resultadosConsultaMutableLiveData;

    public PautaConsultaViewModel() {
        resultadosConsultaMutableLiveData = new MutableLiveData<List<PautaResultadoConsultaDTO>>();
    }

}