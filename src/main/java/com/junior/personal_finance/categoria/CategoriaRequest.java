package com.junior.personal_finance.categoria;

import com.junior.personal_finance.enums.TipoTransacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoriaRequest {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @NotNull(message = "Tipo (DESPESA/RECEITA) é obrigatório")
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

