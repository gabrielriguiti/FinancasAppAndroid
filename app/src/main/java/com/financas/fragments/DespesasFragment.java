package com.financas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.financas.R;
import com.financas.adapters.LancamentoAdapter;
import com.financas.model.database.DatabaseHelper;
import com.financas.model.entities.Lancamento;
import com.financas.utils.NumberUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DespesasFragment extends Fragment {

    private TextView tvTotal;
    private LancamentoAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lancamentos, container, false);

        List<Lancamento> lancamentos = DatabaseHelper.getInstance(getContext()).lancamentoDao().findDepesas();

         adapter = new LancamentoAdapter(lancamentos, getActivity());
        RecyclerView rvDespesas = view.findViewById(R.id.data_list);
        rvDespesas.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDespesas.setAdapter(adapter);

        tvTotal = view.findViewById(R.id.total);

//        FloatingActionButton fabNovaDespesa = view.findViewById(R.id.btn_add);
//        fabNovaDespesa.setOnClickListener(v ->
//                requireActivity()
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.frame_layout, new FormDespesaFragment())
//                        .addToBackStack(null)
//                        .commit());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        double total = 0;

        for (Lancamento l : adapter.getDataList()) {
            total += l.getValor();
        }

        tvTotal.setText(String.format("Total: %s", NumberUtils.formatRealBrasileiro(total)));
    }
}