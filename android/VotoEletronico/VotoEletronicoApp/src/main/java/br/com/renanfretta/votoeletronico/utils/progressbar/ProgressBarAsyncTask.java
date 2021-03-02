package br.com.renanfretta.votoeletronico.utils.progressbar;

import android.os.AsyncTask;

import androidx.annotation.CallSuper;

public abstract class ProgressBarAsyncTask extends AsyncTask<Void, Void, Void> {

    private ProgressBarLoadingUtil progressBarLoadingUtil;

    public ProgressBarAsyncTask(ProgressBarLoadingUtil progressBarLoadingUtil) {
        this.progressBarLoadingUtil = progressBarLoadingUtil;
    }

    @Override
    @CallSuper
    protected void onPreExecute() {
        progressBarLoadingUtil.show();
    }

    @Override
    protected Void doInBackground(Void... notUsed) {
        throw new RuntimeException("Método 'doInBackground' não declarado.");
    }

    @Override
    @CallSuper
    protected void onPostExecute(Void notUsed) {
        progressBarLoadingUtil.hide();
    }

}
