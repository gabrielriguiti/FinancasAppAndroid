package com.financas.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.financas.R;
import com.financas.model.database.DatabaseHelper;
import com.financas.model.entities.Lancamento;
import com.financas.utils.MessageUtils;
import com.google.android.material.textfield.TextInputLayout;

public class FormParcelamentoFragment extends Fragment {

    private EditText etDescricao;
    private EditText etVlrTotal;
    private CheckBox cbFatura;
    private EditText etData;
    private EditText etDiaVenc;
    private EditText etQtdParcelas;
    private EditText etQtdParcPagas;
    private final Lancamento lancamento;

    public FormParcelamentoFragment() {
        this.lancamento = new Lancamento();
    }

    public FormParcelamentoFragment(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_form_parcelamento, container, false);

        etDescricao = ((TextInputLayout) view.findViewById(R.id.descricao)).getEditText();
        etVlrTotal = ((TextInputLayout) view.findViewById(R.id.vlr_total)).getEditText();
        cbFatura = view.findViewById(R.id.fatura);
        etData = ((TextInputLayout) view.findViewById(R.id.data_compra)).getEditText();
        etDiaVenc = ((TextInputLayout) view.findViewById(R.id.dia_vencimento)).getEditText();
        etQtdParcelas = ((TextInputLayout) view.findViewById(R.id.qtd_parcelas)).getEditText();
        etQtdParcPagas = ((TextInputLayout) view.findViewById(R.id.qtd_parc_pagas)).getEditText();

        view.findViewById(R.id.btn_salvar).setOnClickListener(v -> salvar());

        if (lancamento.getId() > 0) {
            etDescricao.setText(lancamento.getDescricao());
            etVlrTotal.setText(String.valueOf(lancamento.getValor()));
            cbFatura.setChecked(lancamento.isFaturaCartao());
            etData.setText(lancamento.getDataCompra());
            etDiaVenc.setText(String.valueOf(lancamento.getDiaVencimento()));
            etQtdParcelas.setText(String.valueOf(lancamento.getTotParcelas()));
            etQtdParcPagas.setText(String.valueOf(lancamento.getTotParcPag()));
        }

        return view;
    }

    private void salvar() {

        if (etDescricao.getText().toString().trim().equals("")) {
            MessageUtils.showInfo(getContext(), "Informe a descrição da despesa.");
            return;
        }

        if (etVlrTotal.getText().toString().trim().equals("")) {
            MessageUtils.showInfo(getContext(), "Informe o valor total do parcelamento.");
            return;
        }

        if (etDiaVenc.getText().toString().trim().equals("")) {
            MessageUtils.showInfo(getContext(), "Informe o dia para pagamento do parcelamento.");
            return;
        }

        if (etData.getText().toString().trim().equals("")) {
            MessageUtils.showInfo(getContext(), "Informe a data do parcelamento.");
            return;
        }

        if (etQtdParcelas.getText().toString().trim().equals("")) {
            MessageUtils.showInfo(getContext(), "Informe a quantidade total de parcelas.");
            return;
        }

        if (etQtdParcPagas.getText().toString().trim().equals("")) {
            MessageUtils.showInfo(getContext(), "Informe a quantidade de parcelas pagas.");
            return;
        }

        lancamento.setDescricao(etDescricao.getText().toString());
        lancamento.setValor(Double.parseDouble(etVlrTotal.getText().toString()));
        lancamento.setDiaVencimento(Integer.parseInt(etDiaVenc.getText().toString()));
        lancamento.setRecDesp(-1);
        lancamento.setDataCompra(etData.getText().toString());
        lancamento.setTotParcelas(Integer.parseInt(etQtdParcelas.getText().toString()));
        lancamento.setTotParcPag(Integer.parseInt(etQtdParcPagas.getText().toString()));
        lancamento.setFaturaCartao(cbFatura.isChecked());

        if (lancamento.getValor() < 0) {
            MessageUtils.showInfo(getContext(), "O valor da despesa deve ser maior que zero.");
            return;
        }

        if (lancamento.getDiaVencimento() < 1 || lancamento.getDiaVencimento() > 31) {
            MessageUtils.showInfo(getContext(), "Informe um dia válido para pagamento da despesa.");
            return;
        }

        if (lancamento.getId() > 0) {
            lancamento.setId(lancamento.getId());
            DatabaseHelper.getInstance(getContext()).lancamentoDao().update(lancamento);
        } else {
            DatabaseHelper.getInstance(getContext()).lancamentoDao().insert(lancamento);
        }
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}