package com.financas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.financas.R;
import com.financas.adapters.CartoesAdapter;
import com.financas.model.database.DatabaseHelper;
import com.financas.model.entities.Cartao;
import com.financas.model.entities.Lancamento;
import com.financas.utils.NumberUtils;

import java.util.List;

public class InicioFragment extends Fragment {

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_inicio, container, false);

        List<Cartao> cartoes = DatabaseHelper.getInstance(getContext()).cartaoDao().findAll();

        RecyclerView rwCartoes = view.findViewById(R.id.cartoes_list);
        rwCartoes.setLayoutManager(new LinearLayoutManager(getContext()));
        rwCartoes.setAdapter(new CartoesAdapter(requireFragmentManager(), requireContext(), cartoes));

        ImageView ivBtnAddCartao = view.findViewById(R.id.btn_add_cartao);
        ivBtnAddCartao.setOnClickListener(v -> requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, new FormCartaoFragment())
                .addToBackStack(null)
                .commit());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setSaldos();
    }

    private void setSaldos() {
        double totalDespesas = 0;
        double totalReceitas = 0;

        List<Lancamento> lancamentos = DatabaseHelper.getInstance(getContext()).lancamentoDao().findAll();
        List<Cartao> cartoes = DatabaseHelper.getInstance(getContext()).cartaoDao().findAll();

        for (Lancamento l : lancamentos) {
            if (!l.isFaturaCartao()) {
                double vlrTot = l.getValor();

                if (l.getTotParcelas() != null && l.getTotParcelas() != 0) {
                    vlrTot = l.getValor() / l.getTotParcelas();
                }

                if (l.getRecDesp() == -1) {
                    totalDespesas += vlrTot;
                } else {
                    totalReceitas += vlrTot;
                }
            }
        }

        for (Cartao c : cartoes) {
            totalDespesas += c.getVlrFatura();
        }

        TextView tvSaldo = view.findViewById(R.id.saldo);
        TextView tvReceitas = view.findViewById(R.id.vlr_tot_receitas);
        TextView tvDespesas = view.findViewById(R.id.vlr_tot_despesas);

        tvSaldo.setText(NumberUtils.formatRealBrasileiro(totalReceitas - totalDespesas));
        tvReceitas.setText(NumberUtils.formatRealBrasileiro(totalReceitas));
        tvDespesas.setText(NumberUtils.formatRealBrasileiro(totalDespesas));
    }
}