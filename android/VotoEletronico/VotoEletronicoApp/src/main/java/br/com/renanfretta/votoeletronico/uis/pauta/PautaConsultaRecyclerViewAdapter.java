package br.com.renanfretta.votoeletronico.uis.pauta;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.renanfretta.votoeletronico.R;
import br.com.renanfretta.votoeletronico.dtos.PautaResultadoConsultaDTO;
import br.com.renanfretta.votoeletronico.utils.HtmlString;

public class PautaConsultaRecyclerViewAdapter extends RecyclerView.Adapter<PautaConsultaRecyclerViewAdapter.ViewHolder> {

    private Activity activity;
    private Context context;
    private List<PautaResultadoConsultaDTO> data;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.card_view_row_default_text);
        }
    }

    public PautaConsultaRecyclerViewAdapter(Activity activity, List<PautaResultadoConsultaDTO> data) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_row_default, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PautaResultadoConsultaDTO dto = data.get(position);

        String text = "";
        text += HtmlString.addEstiloCabecalho("Id: ", false) + HtmlString.addEstiloTextoListagem(HtmlString.addEspacosBrancoAte(dto.getId().toString(), 15)) + HtmlString.getEspacosBranco(8);
        text += HtmlString.addEstiloCabecalho("Descrição: ", true) + HtmlString.addEstiloTextoListagem(dto.getDescricao());
        holder.textView.setText(HtmlString.fromHtml(text));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FIXME: Falta implementar
                // ActivityUtil.startActivity(activity, PautaCadastroActivity.class, dto.getId(), false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(PautaResultadoConsultaDTO item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public List<PautaResultadoConsultaDTO> getData() {
        return data;
    }

}
