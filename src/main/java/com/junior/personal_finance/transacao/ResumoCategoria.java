package com.junior.personal_finance.transacao;

public class ResumoCategoria {
    private String categoriaNome;
    private double totalGasto;

    public ResumoCategoria(String categoriaNome, double totalGasto) {
        this.categoriaNome = categoriaNome;
        this.totalGasto = totalGasto;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public double getTotalGasto() {
        return totalGasto;
    }
}
