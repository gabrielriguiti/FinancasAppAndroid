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

public class FormDespesaFragment extends Fragment {

    private EditText etDescricao;
    private CheckBox cbFatura;
    private EditText etValor;
    private EditText etDiaPagamento;
    private Button btnSalvar;
    private final Lancamento lancamento;

    public FormDespesaFragment() {
        this.lancamento = new Lancamento();
    }

    public FormDespesaFragment(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_form_despesa, container, false);

        etDescricao = ((TextInputLayout) view.findViewById(R.id.input_descricao)).getEditText();
        cbFatura = view.findViewById(R.id.fatura_cartao);
        etValor = ((TextInputLayout) view.findViewById(R.id.input_valor)).getEditText();
        etDiaPagamento = ((TextInputLayout) view.findViewById(R.id.input_dia_vencimento)).getEditText();
        btnSalvar = view.findViewById(R.id.btn_salvar);

        btnSalvar.setOnClickListener(v -> salvar());

        if (lancamento.getId() > 0) {
            etDescricao.setText(lancamento.getDescricao());
            cbFatura.setChecked(lancamento.isFaturaCartao());
            etValor.setText(String.valueOf(lancamento.getValor()));
            etDiaPagamento.setText(String.valueOf(lancamento.getDiaVencimento()));
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

        if (etDiaPagamento.getText().toString().trim().equals("")) {
            MessageUtils.showInfo(getContext(), "Informe o dia para pagamento da despesa.");
            return;
        }

        lancamento.setDescricao(etDescricao.getText().toString());
        lancamento.setFaturaCartao(cbFatura.isChecked());
        lancamento.setValor(Double.parseDouble(etValor.getText().toString()));
        lancamento.setDiaVencimento(Integer.parseInt(etDiaPagamento.getText().toString()));
        lancamento.setRecDesp(-1);

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