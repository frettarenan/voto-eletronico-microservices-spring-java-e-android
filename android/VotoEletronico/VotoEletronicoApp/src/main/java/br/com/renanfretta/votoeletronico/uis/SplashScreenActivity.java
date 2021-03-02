package br.com.renanfretta.votoeletronico.uis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

import br.com.renanfretta.votoeletronico.R;
import br.com.renanfretta.votoeletronico.utils.SessaoUsuarioUtil;
import br.com.renanfretta.votoeletronico.utils.SharedPreferencesUtil;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    private SplashScreenActivity currentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        currentActivity = this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarMainActivity();
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void mostrarMainActivity() {
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            boolean isPermanecerConectado = SharedPreferencesUtil.getInstance(getApplicationContext()).getBoolean(SharedPreferencesUtil.PERMANECER_CONECTADO);
            boolean isUsuarioLogado = SessaoUsuarioUtil.getInstance(currentActivity).isUsuarioLogado();

            if (isPermanecerConectado && isUsuarioLogado) {
                startActivity(new Intent(this, PrincipalActivity.class));
                return;
            }

            startActivity(new Intent(this, LoginActivity.class));
        } finally {
            finish();
        }
    }

}
