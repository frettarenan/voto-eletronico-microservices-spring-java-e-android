package br.com.renanfretta.votoeletronico.restservices;

import java.util.List;

import br.com.renanfretta.votoeletronico.dtos.PautaResultadoConsultaDTO;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PautaRestService {

    @GET("pauta")
    Call<List<PautaResultadoConsultaDTO>> listar();

    @DELETE("pauta/{id}")
    Call<PautaResultadoConsultaDTO> deleteById(@Path("id") Integer id);

}
