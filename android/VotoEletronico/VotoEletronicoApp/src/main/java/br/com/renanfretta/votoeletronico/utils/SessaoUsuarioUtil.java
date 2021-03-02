package br.com.renanfretta.votoeletronico.utils;

import android.app.Activity;
import android.content.Context;

import br.com.renanfretta.votoeletronico.dtos.UsuarioLogadoDTO;
import br.com.renanfretta.votoeletronico.uis.LoginActivity;

public class SessaoUsuarioUtil {

    private static SessaoUsuarioUtil instance = null;
    private static Activity currentActivity = null;
    private static Context applicationContext = null;

    public static SessaoUsuarioUtil getInstance(Activity activity) {
        if (instance == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (instance == null) {
                    instance = new SessaoUsuarioUtil();
                }
            }
        }
        currentActivity = activity;
        applicationContext = activity.getApplicationContext();
        return instance;
    }

    public UsuarioLogadoDTO getUsuarioLogado() {
        return SharedPreferencesUtil.getInstance(applicationContext).getObject(SharedPreferencesUtil.USUARIO_LOGADO, UsuarioLogadoDTO.class);
    }

    public boolean isUsuarioLogado() {
        return getUsuarioLogado() != null;
    }

    public void setUsuarioLogado(UsuarioLogadoDTO usuarioLogadoDTO) {
        SharedPreferencesUtil.getInstance(applicationContext).putObject(SharedPreferencesUtil.USUARIO_LOGADO, usuarioLogadoDTO);
    }

    public void logout() {
        SharedPreferencesUtil.getInstance(applicationContext).putObject(SharedPreferencesUtil.USUARIO_LOGADO, null);
        ActivityUtil.startActivity(currentActivity, LoginActivity.class, true);
    }

}
