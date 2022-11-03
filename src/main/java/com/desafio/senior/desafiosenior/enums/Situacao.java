package com.desafio.senior.desafiosenior.enums;

public enum Situacao {
    A("Aberto"),
    F("Fechado"),
    B("Bloqueia");
    private String situacao;
    Situacao(String situacao) {
        this.situacao = situacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
