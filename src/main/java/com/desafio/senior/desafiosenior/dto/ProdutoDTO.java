package com.desafio.senior.desafiosenior.dto;

import com.desafio.senior.desafiosenior.enums.TipoProduto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProdutoDTO {

    private String descricao;
    private BigDecimal preco;
    private TipoProduto tipoProduto;
    private boolean inativo;
}
