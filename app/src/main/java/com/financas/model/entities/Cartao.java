package com.financas.model.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CARTOES")
public class Cartao {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "BANCO")
    private int banco;

    @ColumnInfo(name = "DESCRICAO")
    private String descricao;

    @ColumnInfo(name = "FECHAMENTO")
    private String fechamento;

    @ColumnInfo(name = "VENCIMENTO")
    private String vencimento;

    @ColumnInfo(name = "VLRFATURA")
    private Double vlrFatura;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBanco() {
        return banco;
    }

    public void setBanco(int banco) {
        this.banco = banco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFechamento() {
        return fechamento;
    }

    public void setFechamento(String fechamento) {
        this.fechamento = fechamento;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public Double getVlrFatura() {
        return vlrFatura;
    }

    public void setVlrFatura(Double vlrFatura) {
        this.vlrFatura = vlrFatura;
    }
}
