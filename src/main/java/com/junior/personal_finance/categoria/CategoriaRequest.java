package com.junior.personal_finance.categoria;

import com.junior.personal_finance.enums.TipoTransacao;

public class CategoriaRequest {
    private String nome;
    private TipoTransacao tipo;

    public CategoriaRequest() {}

    public CategoriaRequest(String nome, TipoTransacao tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }
}

