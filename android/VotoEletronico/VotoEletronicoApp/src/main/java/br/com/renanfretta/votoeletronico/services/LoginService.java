package br.com.renanfretta.votoeletronico.services;

import android.content.Context;

import br.com.renanfretta.votoeletronico.dtos.UsuarioLogadoDTO;

public class LoginService {
    Context context;

    public LoginService(Context context) {
        this.context = context;
    }

    public UsuarioLogadoDTO getUsuarioLogado(String login, String senha) {
        // FIXME: realizar a implementação
        UsuarioLogadoDTO usuarioLogadoDTO = UsuarioLogadoDTO.builder() //
                .id(1) //
                .login("frettarenan") //
                .nome("Renan Fretta") //
                .build();
        return usuarioLogadoDTO;
    }
}
