package com.desafio.senior.desafiosenior.dto;

import com.desafio.senior.desafiosenior.model.ItensPedido;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ItensPedidoDTO {

    private ProdutoDTO produto;
    private BigDecimal quantidade;

}
