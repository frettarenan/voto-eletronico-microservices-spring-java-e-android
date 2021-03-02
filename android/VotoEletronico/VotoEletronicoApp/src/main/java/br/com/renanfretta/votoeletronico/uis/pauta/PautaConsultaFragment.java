package br.com.renanfretta.votoeletronico.uis.pauta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.HttpURLConnection;
import java.util.List;

import br.com.renanfretta.votoeletronico.R;
import br.com.renanfretta.votoeletronico.configs.RetrofitConfig;
import br.com.renanfretta.votoeletronico.dtos.PautaResultadoConsultaDTO;
import br.com.renanfretta.votoeletronico.services.PautaService;
import br.com.renanfretta.votoeletronico.uis.PrincipalActivity;
import br.com.renanfretta.votoeletronico.utils.SwipeToDeleteCallback;
import br.com.renanfretta.votoeletronico.utils.ToastUtil;
import br.com.renanfretta.votoeletronico.utils.progressbar.ProgressBarLoadingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        recyclerView = (RecyclerView) view.findViewById(R.id.consulta_pauta_rc_lista);
        noDataContainer = view.findViewById(R.id.no_data_container);

        model.getResultadosConsultaMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<PautaResultadoConsultaDTO>>() {
            @Override
            public void onChanged(@Nullable List<PautaResultadoConsultaDTO> list) {
                totalResultadosConsulta = new Long(list.size());
                mostraOcultaMensagemSemResultados();
                adapter = new PautaConsultaRecyclerViewAdapter(currentActivity, list);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        Call<List<PautaResultadoConsultaDTO>> call = new RetrofitConfig(currentActivity).getPautaRestService().listar();
        call.enqueue(new Callback<List<PautaResultadoConsultaDTO>>() {
            @Override
            public void onResponse(Call<List<PautaResultadoConsultaDTO>> call, Response<List<PautaResultadoConsultaDTO>> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    model.getResultadosConsultaMutableLiveData().setValue(response.body());
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<PautaResultadoConsultaDTO>> call, Throwable t) {
                ToastUtil.showMessageErroComunicacaoWebService(currentActivity, t);
            }
        });

        configuraExcluir();
    }

    private void configuraExcluir() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this.getContext()) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                final int position = viewHolder.getAdapterPosition();
                AlertDialog alert = new AlertDialog.Builder(getContext())
                        .setCancelable(false)
                        .setTitle(currentActivity.getString(R.string.alert_dialog_question_title))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage(currentActivity.getString(R.string.alert_dialog_question_message_delete_database_record))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                Integer id = model.getResultadosConsultaMutableLiveData().getValue().get(position).getId();
                                Call<PautaResultadoConsultaDTO> call = new RetrofitConfig(currentActivity).getPautaRestService().deleteById(id);

                                call.enqueue(new Callback<PautaResultadoConsultaDTO>() {
                                    @Override
                                    public void onResponse(Call<PautaResultadoConsultaDTO> call, Response<PautaResultadoConsultaDTO> response) {
                                        if (response.code() == HttpURLConnection.HTTP_OK) {
                                            ToastUtil.showMessageSucesso(currentActivity);
                                            adapter.removeItem(position);
                                            totalResultadosConsulta--;
                                            mostraOcultaMensagemSemResultados();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<PautaResultadoConsultaDTO> call, Throwable t) {
                                        ToastUtil.showMessageErroComunicacaoWebService(currentActivity, t);
                                        adapter.notifyItemRemoved(position);
                                        adapter.notifyItemInserted(position);
                                    }
                                });

                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyItemRemoved(position);
                                adapter.notifyItemInserted(position);
                                dialog.dismiss();
                            }
                        })
                        .create();
                alert.show();
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }

    private void mostraOcultaMensagemSemResultados() {
        if (totalResultadosConsulta > 0)
            noDataContainer.setVisibility(View.GONE);
        else
            noDataContainer.setVisibility(View.VISIBLE);
    }

}