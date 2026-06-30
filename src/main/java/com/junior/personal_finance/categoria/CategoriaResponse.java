package com.junior.personal_finance.categoria;

import com.junior.personal_finance.enums.TipoTransacao;

public class CategoriaResponse {
    private Long id;
    private String nome;
    private TipoTransacao tipo;

    public CategoriaResponse(Long id, String nome, TipoTransacao tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
    }
    
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }
}


