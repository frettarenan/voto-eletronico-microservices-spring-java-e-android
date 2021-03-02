package br.com.renanfretta.votoeletronico.utils.progressbar;

import android.app.Activity;
import android.view.View;

import br.com.renanfretta.votoeletronico.R;

public class ProgressBarLoadingUtil {

    private boolean inicializado = false;

    private Activity activity;
    private View progressBarContainer;

    private static int _nrTotalChamadasEmAberto = 0;

    public synchronized void incrementa() {
        _nrTotalChamadasEmAberto++;
    }

    public synchronized void decrementa() {
        if (_nrTotalChamadasEmAberto > 0)
            _nrTotalChamadasEmAberto--;
    }

    public ProgressBarLoadingUtil(Activity currentActivity) {
        activity = currentActivity;
    }

    private void inicializeIfNecessary() {
        if (inicializado)
            return;
        inicializado = true;
        progressBarContainer = (View) activity.findViewById(R.id.progress_bar_container);
    }

    public void show() {
        inicializeIfNecessary();
        incrementa();
        if (progressBarContainer != null)
            progressBarContainer.setVisibility(View.VISIBLE);
    }

    public void hide() {
        inicializeIfNecessary();
        decrementa();
        if (_nrTotalChamadasEmAberto > 0)
            return;
        if (progressBarContainer != null)
            progressBarContainer.setVisibility(View.GONE);
    }

    public Activity getActivity() {
        return activity;
    }

}
