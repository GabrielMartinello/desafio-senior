package com.desafio.senior.desafiosenior.exception;

public class ItemInativoException extends RuntimeException {

    public ItemInativoException() {
        super(String.format("Não é possível adicionar itens inativos!"));
    }
}

