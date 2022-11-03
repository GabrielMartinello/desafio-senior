package com.desafio.senior.desafiosenior.dto;

import com.desafio.senior.desafiosenior.enums.Situacao;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PedidoDTO {

    private String descricao;
    private Situacao situacao;

    private BigDecimal descontoPercentual;

    private BigDecimal descontoTotal;
    private List<ItensPedidoDTO> itensPedido;

}
