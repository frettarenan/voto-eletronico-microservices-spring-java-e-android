package br.com.renanfretta.votoeletronico.utils;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;

public class ActivityUtil {

    public static final String NOME_DO_ATRIBUTO_ID = "id";
    public static final String NOME_OBJETO_SERIALIZABLE = "param";

    public static void startActivity(Activity currentActivity, Class<?> activityClass, boolean finishCurrentActivity) {
        startActivity(currentActivity, activityClass, null, finishCurrentActivity);
    }

    public static void startActivity(Activity currentActivity, Class<?> activityClass, Serializable param, boolean finishCurrentActivity) {
        Intent intent = new Intent(currentActivity.getApplicationContext(), activityClass);
        if (param != null) {
            intent.putExtra(NOME_OBJETO_SERIALIZABLE, param);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        currentActivity.startActivity(intent);
        if (finishCurrentActivity) {
            currentActivity.finish();
        }
    }

    public static void startActivity(Activity currentActivity, Class<?> activityClass, Integer id, boolean finishCurrentActivity) {
        Intent intent = new Intent(currentActivity.getApplicationContext(), activityClass);
        if (id != null) {
            intent.putExtra(NOME_DO_ATRIBUTO_ID, id);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        currentActivity.startActivity(intent);
        if (finishCurrentActivity) {
            currentActivity.finish();
        }
    }

}
