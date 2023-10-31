package com.financas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.financas.R;
import com.financas.model.database.DatabaseHelper;
import com.financas.model.entities.Cartao;
import com.financas.utils.MessageUtils;
import com.financas.utils.NumberUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class FormCartaoFragment extends Fragment {

    private final Cartao cartao;
    private EditText etDescricao;
    private EditText etFechamento;
    private EditText etVencimento;
    private EditText etVlrFatura;
    private Button btnSalvar;
    private Spinner spinner;

    public FormCartaoFragment() {
        this.cartao = new Cartao();
    }

    public FormCartaoFragment(Cartao cartao) {
        this.cartao = cartao;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_form_cartao, container, false);

        spinner = view.findViewById(R.id.spinner_bancos);

        ArrayList<String> bancos = new ArrayList<>();
        bancos.add("Itaú");
        bancos.add("Unicred");
        bancos.add("C6");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, bancos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        etDescricao = ((TextInputLayout) view.findViewById(R.id.descricao)).getEditText();
        etFechamento = ((TextInputLayout) view.findViewById(R.id.fechamento)).getEditText();
        etVencimento = ((TextInputLayout) view.findViewById(R.id.vencimento)).getEditText();
        etVlrFatura = ((TextInputLayout) view.findViewById(R.id.fatura)).getEditText();
        btnSalvar = view.findViewById(R.id.btn_salvar);

        btnSalvar.setOnClickListener(v -> salvar());

        if (cartao.getId() > 0){
            etDescricao.setText(cartao.getDescricao());
            etFechamento.setText(cartao.getFechamento());
            etVencimento.setText(cartao.getVencimento());
            etVlrFatura.setText(NumberUtils.formatRealBrasileiro(cartao.getVlrFatura()));
        }

        return view;
    }

    private void salvar() {

        if (etDescricao.getText().toString().trim().equals("")) {
            MessageUtils.showInfo(getContext(), "Informe a descrição do cartão.");
            return;
        }

        if (etVlrFatura.getText().toString().trim().equals("")) {
            MessageUtils.showInfo(getContext(), "Informe o valor da fatura.");
            return;
        }

        if (etFechamento.getText().toString().trim().equals("")) {
            MessageUtils.showInfo(getContext(), "Informe o dia do fechamento da fatura.");
            return;
        }

        if (etVencimento.getText().toString().trim().equals("")) {
            MessageUtils.showInfo(getContext(), "Informe o dia do vencimento da fatura.");
            return;
        }

        cartao.setDescricao(etDescricao.getText().toString());
        cartao.setFechamento(etFechamento.getText().toString());
        cartao.setVencimento(etVencimento.getText().toString());
        cartao.setVlrFatura(Double.parseDouble(etVlrFatura.getText().toString()));
        cartao.setBanco(spinner.getSelectedItemPosition());

        if (cartao.getId() > 0) {
            DatabaseHelper.getInstance(getContext()).cartaoDao().update(cartao);
        } else {
            DatabaseHelper.getInstance(getContext()).cartaoDao().insert(cartao);
        }

        requireActivity().getSupportFragmentManager().popBackStack();
    }
}