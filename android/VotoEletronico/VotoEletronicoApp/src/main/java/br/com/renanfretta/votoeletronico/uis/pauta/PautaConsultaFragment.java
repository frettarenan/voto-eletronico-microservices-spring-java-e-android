package br.com.renanfretta.votoeletronico.uis.pauta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.renanfretta.votoeletronico.R;
import br.com.renanfretta.votoeletronico.dtos.PautaResultadoConsultaDTO;
import br.com.renanfretta.votoeletronico.services.PautaService;
import br.com.renanfretta.votoeletronico.uis.PrincipalActivity;
import br.com.renanfretta.votoeletronico.utils.progressbar.ProgressBarLoadingUtil;

public class PautaConsultaFragment extends Fragment {

    private ProgressBarLoadingUtil progressBarLoadingUtil;

    private PautaConsultaViewModel model;
    private PautaService pautaService;
    private PrincipalActivity currentActivity;

    private Long totalResultadosConsulta = 0L;
    private EditText etFiltroPauta;
    private RecyclerView recyclerView;
    private PautaConsultaRecyclerViewAdapter adapter;
    private View noDataContainer;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pauta_consulta_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        currentActivity = (PrincipalActivity) this.getActivity();
        model = new PautaConsultaViewModel();

        pautaService = new PautaService();

        progressBarLoadingUtil = new ProgressBarLoadingUtil(currentActivity);
        etFiltroPauta = view.findViewById(R.id.et_filtro_pauta);
        recyclerView = (RecyclerView) view.findViewById(R.id.consulta_etiqueta_rc_lista);
        noDataContainer = view.findViewById(R.id.no_data_container);

        model.getResultadosConsultaMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<PautaResultadoConsultaDTO>>() {
            @Override
            public void onChanged(@Nullable List<PautaResultadoConsultaDTO> list) {
                mostraOcultaMensagemSemResultados();
                adapter = new PautaConsultaRecyclerViewAdapter(currentActivity, list);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });
    }

    private void mostraOcultaMensagemSemResultados() {
        if (totalResultadosConsulta > 0)
            noDataContainer.setVisibility(View.GONE);
        else
            noDataContainer.setVisibility(View.VISIBLE);
    }

}