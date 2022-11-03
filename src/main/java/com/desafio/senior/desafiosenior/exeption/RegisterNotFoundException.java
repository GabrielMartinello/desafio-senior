package com.desafio.senior.desafiosenior.exeption;

import java.util.UUID;

public class RegisterNotFoundException extends Exception {
    public RegisterNotFoundException(UUID uuid) {
        super(String.format("Registro com o id %s não foi encontrado!", uuid));
    }
}
