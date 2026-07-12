package com.junior.personal_finance.transacao;

import java.time.LocalDate;

import com.junior.personal_finance.enums.TipoTransacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransacaoRequest {

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private Double valor;

    private LocalDate data;

    @NotNull(message = "Tipo (DESPESA/RECEITA) é obrigatório")
    private TipoTransacao tipo;
    
    @NotNull(message = "Id da Categoria é obrigatório")
    private Long categoriaId;

    public TransacaoRequest() {}

    public TransacaoRequest(String descricao, Double valor, LocalDate data, TipoTransacao tipo, Long categoriaId) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.categoriaId = categoriaId;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public Double getValor() {
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
