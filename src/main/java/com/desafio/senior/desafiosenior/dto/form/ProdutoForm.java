package com.desafio.senior.desafiosenior.dto.form;

import com.desafio.senior.desafiosenior.enums.TipoProduto;
import com.desafio.senior.desafiosenior.model.Produto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class ProdutoForm {

    @NotNull() @NotEmpty
    private String descricao;

    @NotNull()
    private BigDecimal preco;

    @NotNull()
    private TipoProduto tipoProduto;

    @NotNull()
    private boolean inativo;

//    public Produto converter() {
//        return new Produto(descricao, preco, tipoProduto, inativo);
//    }
}
