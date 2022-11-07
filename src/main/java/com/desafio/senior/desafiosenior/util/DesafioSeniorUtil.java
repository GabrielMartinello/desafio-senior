package com.desafio.senior.desafiosenior.util;

import java.util.UUID;

public class DesafioSeniorUtil {

    public static UUID toUUID(String id) {
        if (id != null && !id.isEmpty()) {
            return UUID.fromString(id);
        }

        return null;
    }

}
