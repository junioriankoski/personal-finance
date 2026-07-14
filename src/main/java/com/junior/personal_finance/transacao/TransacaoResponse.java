package com.junior.personal_finance.transacao;


import java.time.LocalDate;


import com.junior.personal_finance.enums.TipoTransacao;

public class TransacaoResponse {
    private Long id;
    private String descricao;
    private double valor;
    private LocalDate data;
    private TipoTransacao tipo;
    private String categoriaNome;
    private Long categoriaId;

    public TransacaoResponse(Long id, String descricao, double valor, LocalDate data, TipoTransacao tipo, String categoriaNome, Long categoriaId) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.categoriaNome = categoriaNome;
        this.categoriaId = categoriaId;
    }

    public Long getId() {
        return id;
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

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }
}
