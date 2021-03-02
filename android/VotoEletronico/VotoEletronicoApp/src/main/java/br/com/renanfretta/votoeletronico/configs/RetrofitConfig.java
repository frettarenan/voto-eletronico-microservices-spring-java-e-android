package br.com.renanfretta.votoeletronico.configs;

import android.content.Context;

import br.com.renanfretta.votoeletronico.R;
import br.com.renanfretta.votoeletronico.restservices.PautaRestService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig(Context context) {
        this.retrofit = new Retrofit.Builder() //
                .baseUrl(context.getString(R.string.app_server_url)) //
                .addConverterFactory(JacksonConverterFactory.create()) //
                .build();
    }

    public PautaRestService getPautaRestService() {
        return this.retrofit.create(PautaRestService.class);
    }

}
