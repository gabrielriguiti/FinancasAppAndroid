package com.financas.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.financas.R;
import com.financas.fragments.FormParcelamentoFragment;
import com.financas.model.entities.Lancamento;
import com.financas.utils.NumberUtils;

import java.util.List;

public class ParcelamentosAdapter extends RecyclerView.Adapter<ParcelamentosAdapter.ViewHolder> {

    private final List<Lancamento> dataList;
    private final FragmentActivity activity;

    public ParcelamentosAdapter(List<Lancamento> dataList, FragmentActivity activity) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ParcelamentosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_parcelamento, parent, false);
        return new ParcelamentosAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ParcelamentosAdapter.ViewHolder holder, int position) {
        Lancamento lancamento = dataList.get(position);

        holder.container.setOnClickListener(v ->
                activity
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new FormParcelamentoFragment(lancamento))
                        .addToBackStack(null)
                        .commit());

        holder.descricao.setText(lancamento.getDescricao());
        holder.valor.setText(NumberUtils.formatRealBrasileiro(lancamento.getValor() / lancamento.getTotParcelas()));
        holder.progressBar.setMax(lancamento.getTotParcelas());
        holder.progressBar.setProgress(lancamento.getTotParcPag());

        double progresso = ((double) lancamento.getTotParcPag() / lancamento.getTotParcelas()) * 100;
        holder.progress.setText((int) progresso + "%");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout container;
        TextView descricao;
        TextView valor;
        ProgressBar progressBar;
        TextView progress;

        public ViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.item_container);
            descricao = itemView.findViewById(R.id.descricao);
            valor = itemView.findViewById(R.id.valor);
            progressBar = itemView.findViewById(R.id.progress);
            progress = itemView.findViewById(R.id.perc_pago);
        }
    }

    public List<Lancamento> getDataList() {
        return dataList;
    }
}
