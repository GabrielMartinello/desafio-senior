package com.desafio.senior.desafiosenior.dto.form;

import com.desafio.senior.desafiosenior.model.ItensPedido;
import com.desafio.senior.desafiosenior.model.Pedido;
import com.desafio.senior.desafiosenior.model.Produto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Data
@Builder
public class ItensPedidoForm {

    @NotEmpty @NotNull
    private Produto produto;

    @NotEmpty @NotNull
    private Pedido pedido;

    @NotNull
    private BigDecimal quantidade;
}
