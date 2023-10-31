package com.financas.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.financas.R;
import com.financas.adapters.ParcelamentosAdapter;
import com.financas.model.database.DatabaseHelper;
import com.financas.model.entities.Lancamento;
import com.financas.utils.NumberUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ParcelamentosFragment extends Fragment {

    private RecyclerView rvParcelamentos;
    private TextView tvTotalParcelas;
    private ParcelamentosAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lancamentos, container, false);

        List<Lancamento> lancamentos = DatabaseHelper.getInstance(getContext()).lancamentoDao().findParcelamentos();

        adapter = new ParcelamentosAdapter(lancamentos, getActivity());

        tvTotalParcelas = view.findViewById(R.id.total);
        rvParcelamentos = view.findViewById(R.id.data_list);
        rvParcelamentos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvParcelamentos.setAdapter(adapter);

        FloatingActionButton fabAddLanc = view.findViewById(R.id.btn_add);
        fabAddLanc.setOnClickListener(v ->
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new FormParcelamentoFragment())
                        .addToBackStack(null)
                        .commit());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        double totParc = 0;

        for (Lancamento l : adapter.getDataList()) {
            totParc += l.getValor() / l.getTotParcelas();
        }

        tvTotalParcelas.setText(String.format("Total: %s", NumberUtils.formatRealBrasileiro(totParc)));
    }
}
