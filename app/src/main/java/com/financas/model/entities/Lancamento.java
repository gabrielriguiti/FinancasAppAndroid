package com.financas.model.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lancamentos")
public class Lancamento {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "descricao")
    private String descricao;

    @ColumnInfo(name = "valor")
    private Double valor;

    @ColumnInfo(name = "diaVencimento")
    private int diaVencimento;

    @ColumnInfo(name = "recDesp")
    private int recDesp;

    @ColumnInfo(name = "TOTPARCELAS")
    private Integer totParcelas;

    @ColumnInfo(name = "TOTPARCPAG")
    private Integer totParcPag;

    @ColumnInfo(name = "FATURACARTAO")
    private Boolean faturaCartao;

    @ColumnInfo(name = "DATACOMPRA")
    private String dataCompra;

    @ColumnInfo(name = "OBSERVACAO")
    private String observacao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public int getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(int diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public int getRecDesp() {
        return recDesp;
    }

    public void setRecDesp(int recDesp) {
        this.recDesp = recDesp;
    }

    public Integer getTotParcelas() {
        return totParcelas;
    }

    public void setTotParcelas(Integer totParcelas) {
        this.totParcelas = totParcelas;
    }

    public Integer getTotParcPag() {
        return totParcPag;
    }

    public void setTotParcPag(Integer totParcPag) {
        this.totParcPag = totParcPag;
    }

    public Boolean isFaturaCartao() {
        if (faturaCartao == null){
            return false;
        }

        return faturaCartao;
    }

    public void setFaturaCartao(Boolean faturaCartao) {
        this.faturaCartao = faturaCartao;
    }

    public String getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(String dataCompra) {
        this.dataCompra = dataCompra;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
