package com.desafio.senior.desafiosenior.dto.form;

public class ErroForm {

    private String campo;
    private String erro;

    public ErroForm(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }

}
