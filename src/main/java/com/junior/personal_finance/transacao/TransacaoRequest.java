package com.junior.personal_finance.transacao;

import java.time.LocalDate;

import com.junior.personal_finance.enums.TipoTransacao;

public class TransacaoRequest {
    private String descricao;
    private double valor;
    private LocalDate data;
    private TipoTransacao tipo;
    private Long categoriaId;

    public TransacaoRequest() {}

    public TransacaoRequest(String descricao, double valor, LocalDate data, TipoTransacao tipo, Long categoriaId) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.categoriaId = categoriaId;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

}
