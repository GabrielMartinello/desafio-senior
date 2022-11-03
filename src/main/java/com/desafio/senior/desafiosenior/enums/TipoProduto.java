package com.desafio.senior.desafiosenior.enums;

public enum TipoProduto {
    P("Produto"),
    S("Serviço");

    private String tipo;

    TipoProduto(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
