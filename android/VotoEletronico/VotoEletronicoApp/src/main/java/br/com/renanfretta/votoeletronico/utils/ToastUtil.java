package br.com.renanfretta.votoeletronico.utils;

import android.app.Activity;
import android.widget.Toast;

public class ToastUtil {

    public static void showMessage(Activity currentActivity, CharSequence charSequence) {
        Toast.makeText(currentActivity.getApplicationContext(), charSequence, Toast.LENGTH_LONG).show();
    }

    public static void showMessageVerifiqueFormulario(Activity currentActivity) {
        showMessage(currentActivity, "Verifique os erros destacados no formulário e tente novamente.");
    }

    public static void showMessageSucesso(Activity currentActivity) {
        showMessage(currentActivity, "Operação concluída com sucesso.");
    }

    private static void showMessageErro(Activity currentActivity, Throwable t, String mensagem) {
        if (t != null) {
            t.printStackTrace();
        }
        showMessage(currentActivity, mensagem);
    }

    public static void showMessageErroPadrao(Activity currentActivity, Exception e) {
        showMessageErro(currentActivity, e, "Ocorreu um erro ao executar a operação, tente novamente.");
    }

    public static void showMessageErroComunicacaoWebService(Activity currentActivity, Throwable t) {
        showMessageErro(currentActivity, t, "Ocorreu um erro de comunicação com o servidor. A operação foi cancelada.");
    }

    public static void showMessageBackButtonDisabled(Activity currentActivity) {
        showMessage(currentActivity, "Esta ação foi desativada nesta tela do app.");
    }

}
