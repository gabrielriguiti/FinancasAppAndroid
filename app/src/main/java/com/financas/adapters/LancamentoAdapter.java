package com.financas.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.financas.R;
import com.financas.fragments.FormDespesaFragment;
import com.financas.fragments.FormReceitaFragment;
import com.financas.model.entities.Lancamento;
import com.financas.utils.NumberUtils;

import java.util.List;

public class LancamentoAdapter extends RecyclerView.Adapter<LancamentoAdapter.ViewHolder> {

    private final List<Lancamento> dataList;
    private final FragmentActivity activity;

    public LancamentoAdapter(List<Lancamento> dataList, FragmentActivity activity) {
        this.dataList = dataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_lancamento, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lancamento lancamento = dataList.get(position);

        holder.descricao.setText(lancamento.getDescricao());
        holder.valor.setText(NumberUtils.formatRealBrasileiro(lancamento.getValor()));
        holder.diaVencimento.setText(String.format("Dia vencimento: %s", lancamento.getDiaVencimento()));

        holder.clContainer.setOnClickListener(v ->
                activity
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, lancamento.getRecDesp() == -1 ? new FormDespesaFragment(lancamento) : new FormReceitaFragment(lancamento))
                        .addToBackStack(null)
                        .commit());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout clContainer;
        TextView descricao;
        TextView valor;
        TextView diaVencimento;

        public ViewHolder(View itemView) {
            super(itemView);

            clContainer = itemView.findViewById(R.id.container);
            descricao = itemView.findViewById(R.id.descricao);
            valor = itemView.findViewById(R.id.valor);
            diaVencimento = itemView.findViewById(R.id.dia_vencimento);
        }
    }

    public List<Lancamento> getDataList() {
        return dataList;
    }
}
