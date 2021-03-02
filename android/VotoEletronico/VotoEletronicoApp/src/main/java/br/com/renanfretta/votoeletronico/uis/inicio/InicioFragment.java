package br.com.renanfretta.votoeletronico.uis.inicio;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.renanfretta.votoeletronico.R;
import br.com.renanfretta.votoeletronico.uis.PrincipalActivity;
import br.com.renanfretta.votoeletronico.utils.ToastUtil;

public class InicioFragment extends Fragment {

    private PrincipalActivity currentActivity;

    private ViewGroup containerAddComponentes;
    private Button buttonGerarCamposDinamicos;

    private int quantidadeBotoes = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inicio_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        currentActivity = (PrincipalActivity) this.getActivity();

        buttonGerarCamposDinamicos = view.findViewById(R.id.button_add_campo);
        containerAddComponentes = view.findViewById(R.id.container_add_componentes);

        buttonGerarCamposDinamicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btTeste = new Button(currentActivity);
                btTeste.setId(++quantidadeBotoes);
                btTeste.setText("Meu botão " + btTeste.getId());
                containerAddComponentes.addView(btTeste);

                btTeste.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtil.showMessage(currentActivity, "Vamos registrar seu voto para o código " + btTeste.getId());
                    }
                });
            }
        });
    }

}