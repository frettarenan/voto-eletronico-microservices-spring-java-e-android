package br.com.renanfretta.votoeletronico.utils;

import android.app.Activity;

import com.google.android.material.textfield.TextInputLayout;

public class FormValidationUtil {

    public static boolean addMensagemErroCampoObrigatorio(Activity currentActivity, TextInputLayout textInputLayout) {
        if (textInputLayout.getEditText().getText().toString().trim().length() > 0)
            textInputLayout.setError(null);
        else
            textInputLayout.setError("Preenchimento obrigatório");
        return textInputLayout.getEditText().getText().toString().trim().length() > 0;
    }

    public static boolean addMensagemErroCampoObrigatorio(Activity currentActivity, TextInputLayout textInputLayout, boolean isValid) {
        if (isValid)
            textInputLayout.setError(null);
        else
            textInputLayout.setError("Preenchimento obrigatório");
        return isValid;
    }

}
