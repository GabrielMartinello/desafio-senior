package com.desafio.senior.desafiosenior.exception;

import java.util.UUID;

public class RegisterNotFoundException extends RuntimeException {
    public RegisterNotFoundException(UUID uuid) {
        super(String.format("Registro com o id %s não foi encontrado!", uuid));
    }
}
