package com.desafio.senior.desafiosenior.exeption;

public class ItemInativoException extends RuntimeException {

    public ItemInativoException() {
        super(String.format("Não é possível adicionar itens inativos!"));
    }
}

