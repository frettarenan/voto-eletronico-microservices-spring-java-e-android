package br.com.renanfretta.votoeletronico.uis;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import br.com.renanfretta.votoeletronico.R;
import br.com.renanfretta.votoeletronico.dtos.UsuarioLogadoDTO;
import br.com.renanfretta.votoeletronico.services.LoginService;
import br.com.renanfretta.votoeletronico.utils.FormValidationUtil;
import br.com.renanfretta.votoeletronico.utils.SessaoUsuarioUtil;
import br.com.renanfretta.votoeletronico.utils.SharedPreferencesUtil;
import br.com.renanfretta.votoeletronico.utils.ToastUtil;
import br.com.renanfretta.votoeletronico.utils.progressbar.ProgressBarAsyncTask;
import br.com.renanfretta.votoeletronico.utils.progressbar.ProgressBarLoadingUtil;

public class LoginActivity extends AppCompatActivity {

    private ProgressBarLoadingUtil progressBarLoadingUtil;
    private LoginActivity currentActivity;

    private LoginService loginService;

    private Toolbar toolbar;

    private TextInputLayout etlUsuario;
    private EditText etUsuario;
    private TextInputLayout etlSenha;
    private EditText etSenha;
    private Button btAcessar;
    private CheckBox cbPermanecerConectado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        currentActivity = this;
        loginService = new LoginService(this);

        progressBarLoadingUtil = new ProgressBarLoadingUtil(currentActivity);
        etlUsuario = (TextInputLayout) findViewById(R.id.login_etl_usuario);
        etUsuario = (EditText) findViewById(R.id.login_et_usuario);
        etlSenha = (TextInputLayout) findViewById(R.id.login_etl_senha);
        etSenha = (EditText) findViewById(R.id.login_et_senha);
        btAcessar = (Button) findViewById(R.id.login_bt_acessar);
        cbPermanecerConectado = (CheckBox) findViewById(R.id.login_cb_permanecer_conectado);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        etUsuario.setText("frettarenan");
        etSenha.setText("123456");

        btAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValidUsuario = FormValidationUtil.addMensagemErroCampoObrigatorio(currentActivity, etlUsuario);
                boolean isValidSenha = FormValidationUtil.addMensagemErroCampoObrigatorio(currentActivity, etlSenha);
                if (isValidUsuario && isValidSenha)
                    new ExecutaLoginAsyncTask(progressBarLoadingUtil).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                else
                    ToastUtil.showMessageVerifiqueFormulario(currentActivity);
            }
        });
    }

    @Override
    public void onBackPressed() {
        ToastUtil.showMessageBackButtonDisabled(currentActivity);
    }

    private class ExecutaLoginAsyncTask extends ProgressBarAsyncTask {

        private UsuarioLogadoDTO usuarioLogadoDTO = null;
        private Exception exception;

        public ExecutaLoginAsyncTask(ProgressBarLoadingUtil progressBarLoadingUtil) {
            super(progressBarLoadingUtil);
        }

        @Override
        protected Void doInBackground(Void... notUsed) {
            try {
                String login = etUsuario.getText().toString().trim().toUpperCase();
                String senha = etSenha.getText().toString().trim().toUpperCase();
                usuarioLogadoDTO = loginService.getUsuarioLogado(login, senha);
            } catch (Exception e) {
                exception = e;
                ToastUtil.showMessageErroPadrao(progressBarLoadingUtil.getActivity(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void notUsed) {
            super.onPostExecute(notUsed);
            if (exception != null)
                return;
            if (usuarioLogadoDTO == null) {
                ToastUtil.showMessage(currentActivity, getText(R.string.login_usuario_invalido));
                return;
            }

            SharedPreferencesUtil.getInstance(getApplicationContext()).putBoolean(SharedPreferencesUtil.PERMANECER_CONECTADO, cbPermanecerConectado.isChecked());
            SessaoUsuarioUtil.getInstance(currentActivity).setUsuarioLogado(usuarioLogadoDTO);
            startActivity(new Intent(currentActivity, PrincipalActivity.class));
            finish();
        }
    }

}