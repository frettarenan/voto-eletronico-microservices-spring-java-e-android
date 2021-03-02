package br.com.renanfretta.votoeletronico.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPreferencesUtil {

    private static final String FILE_NAME = "appSettings";

    public static final String USUARIO_LOGADO = "usuario_logado";
    public static final String PERMANECER_CONECTADO = "permanecer_conectado";

    private static SharedPreferencesUtil instance = null;
    private static SharedPreferences settings = null;

    public static SharedPreferencesUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (instance == null) {
                    instance = new SharedPreferencesUtil();
                    settings = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);
                }
            }
        }
        return instance;
    }

    public String getString(String key) {
        return settings.getString(key, null);
    }
    public int getInt(String key) { return settings.getInt(key, -1); }
    public int getInt(String key, int defValue) { return settings.getInt(key, defValue); }
    public boolean getBoolean(String key) {
        return settings.getBoolean(key, false);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void putObject(String key, Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        putString(key, json);
    }

    public <T> T getObject(String key, Class<T> classOfT) {
        Gson gson = new Gson();
        String json = getString(key);
        return gson.fromJson(json, classOfT);
    }

}
