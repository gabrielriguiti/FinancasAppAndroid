package com.financas.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.financas.R;
import com.financas.model.database.DatabaseHelper;
import com.financas.model.entities.Lancamento;
import com.financas.utils.MessageUtils;
import com.google.android.material.textfield.TextInputLayout;

public class FormReceitaFragment extends Fragment {

    private EditText etDescricao;
    private EditText etValor;
    private EditText etDiaVencimento;
    private EditText etObservacao;
    private Button btnSalvar;
    private final Lancamento lancamento;

    public FormReceitaFragment() {
        this.lancamento = new Lancamento();
    }

    public FormReceitaFragment(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_form_receita, container, false);

        etDescricao = ((TextInputLayout) view.findViewById(R.id.input_descricao)).getEditText();
        etValor = ((TextInputLayout) view.findViewById(R.id.input_valor)).getEditText();
        etDiaVencimento = ((TextInputLayout) view.findViewById(R.id.input_dia_vencimento)).getEditText();
        etObservacao = ((TextInputLayout) view.findViewById(R.id.input_obs)).getEditText();
        btnSalvar = view.findViewById(R.id.btn_salvar);

        btnSalvar.setOnClickListener(v -> salvar());

        if (lancamento.getId() > 0) {
            etDescricao.setText(lancamento.getDescricao());
            etValor.setText(String.valueOf(lancamento.getValor()));
            etDiaVencimento.setText(String.valueOf(lancamento.getDiaVencimento()));
            etObservacao.setText(lancamento.getObservacao());
        }

        return view;
    }

    private void salvar() {

        if (etDescricao.getText().toString().trim().equals("")) {
            MessageUtils.showInfo(getContext(), "Informe a descrição da despesa.");
            return;
        }

        if (etValor.getText().toString().trim().equals("")) {
            MessageUtils.showInfo(getContext(), "Informe o valor da despesa.");
            return;
        }

        if (etDiaVencimento.getText().toString().trim().equals("")) {
            MessageUtils.showInfo(getContext(), "Informe o dia para pagamento da despesa.");
            return;
        }

        lancamento.setDescricao(etDescricao.getText().toString());
        lancamento.setValor(Double.parseDouble(etValor.getText().toString()));
        lancamento.setDiaVencimento(Integer.parseInt(etDiaVencimento.getText().toString()));
        lancamento.setObservacao(etObservacao.getText().toString());
        lancamento.setRecDesp(1);

        if (lancamento.getValor() < 0) {
            MessageUtils.showInfo(getContext(), "O valor da despesa deve ser maior que zero.");
            return;
        }

        if (lancamento.getDiaVencimento() < 1 || lancamento.getDiaVencimento() > 31) {
            MessageUtils.showInfo(getContext(), "Informe um dia válido para pagamento da despesa.");
            return;
        }

        if (lancamento.getId() > 0) {
            DatabaseHelper.getInstance(getContext()).lancamentoDao().update(lancamento);
        } else {
            DatabaseHelper.getInstance(getContext()).lancamentoDao().insert(lancamento);
        }

        requireActivity().getSupportFragmentManager().popBackStack();
    }
}