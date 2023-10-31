package com.financas.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.financas.R;
import com.financas.fragments.FormCartaoFragment;
import com.financas.model.database.DatabaseHelper;
import com.financas.model.entities.Cartao;
import com.financas.utils.DrawableUtils;
import com.financas.utils.NumberUtils;

import java.util.List;

public class CartoesAdapter extends RecyclerView.Adapter<CartoesAdapter.ViewHolder> {

    private final FragmentManager fragmentManager;
    private final Context context;
    private final List<Cartao> dataList;

    public CartoesAdapter(FragmentManager fragmentManager, Context context, List<Cartao> cartoes) {
        this.fragmentManager = fragmentManager;
        this.context = context;
        dataList = cartoes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_cartao, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Cartao cartao = dataList.get(position);

        if (position == 0) {
            holder.vwDivider.setVisibility(View.GONE);
        }

        if (cartao.getBanco() == 1) {
            holder.ivLogoBanco.setImageResource(DrawableUtils.getIdDrawableByName(context, "ic_unicred"));
        } else if (cartao.getBanco() == 2) {
            holder.ivLogoBanco.setImageResource(DrawableUtils.getIdDrawableByName(context, "ic_c6"));
        }

        holder.tvDescricao.setText(cartao.getDescricao());
        holder.tvFechamento.setText(cartao.getFechamento());
        holder.tvVencimento.setText(cartao.getVencimento());
        holder.tvFatura.setText(NumberUtils.formatRealBrasileiro(cartao.getVlrFatura()));

        holder.clContainer.setOnClickListener(v -> fragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, new FormCartaoFragment(cartao))
                .addToBackStack(null)
                .commit());

        holder.clContainer.setOnLongClickListener(v -> {
            DatabaseHelper.getInstance(context).cartaoDao().delete(cartao);
            dataList.remove(position);
            notifyDataSetChanged();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout clContainer;
        View vwDivider;
        TextView tvDescricao;
        TextView tvFechamento;
        TextView tvVencimento;
        TextView tvFatura;
        ImageView ivLogoBanco;

        public ViewHolder(View itemView) {
            super(itemView);

            clContainer = itemView.findViewById(R.id.container);
            vwDivider = itemView.findViewById(R.id.divider_top);
            tvDescricao = itemView.findViewById(R.id.descricao_cartao);
            tvFechamento = itemView.findViewById(R.id.fechamento_fatura);
            tvVencimento = itemView.findViewById(R.id.vencimento_fatura);
            tvFatura = itemView.findViewById(R.id.vlr_fatura);
            ivLogoBanco = itemView.findViewById(R.id.ic_banco);
        }
    }
}
