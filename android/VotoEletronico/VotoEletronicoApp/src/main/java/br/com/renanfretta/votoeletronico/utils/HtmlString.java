package br.com.renanfretta.votoeletronico.utils;

import android.text.Html;
import android.text.Spanned;

public class HtmlString {

    public static Spanned fromHtml(String input) {
        return Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY);
    }

    public static String getQuebraLinha() {
        return "<br/>";
    }

    private static String addFontColor(String input, String hexColor, boolean addQuebraLinhaAntes) {
        if (input == null)
            return "";
        String retorno = "";
        retorno += "<font color='" + hexColor + "'>" + input + "</font>";
        if (addQuebraLinhaAntes)
            retorno = getQuebraLinha() + retorno;
        return retorno;
    }

    public static String addEstiloCabecalho(String input, boolean addQuebraLinhaAntes) {
        return addFontColor(input, "#000000", addQuebraLinhaAntes);
    }

    public static String addEstiloTextoListagem(String input) {
        if (input == null)
            return "";
        return input;
    }

    public static String addEspacosBrancoAte(String input, int nrEspacosCompletar) {
        return input + getEspacosBranco(Math.abs(input.length() - nrEspacosCompletar));
    }

    public static String getEspacosBranco(int nrEspacos) {
        String retorno = "";
        for (int i = 1; i <= nrEspacos; i++) {
            retorno += "&#160";
        }
        return retorno;
    }

}
