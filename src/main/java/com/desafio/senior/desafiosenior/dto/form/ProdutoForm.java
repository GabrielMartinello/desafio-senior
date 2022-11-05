package com.desafio.senior.desafiosenior.dto.form;

import com.desafio.senior.desafiosenior.enums.TipoProduto;
import com.desafio.senior.desafiosenior.model.Produto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProdutoForm {

    @NotNull()
    @NotEmpty
    private String descricao;

    @NotNull()
    private BigDecimal preco;

    @NotNull()
    private TipoProduto tipoProduto;

    @NotNull()
    private boolean inativo;

    public static void updateEntity(ProdutoForm produtoForm, Produto produto) {
        produto.setTipoProduto(produtoForm.getTipoProduto());
        produto.setPreco(produtoForm.getPreco());
        produto.setDescricao(produtoForm.getDescricao());
        produto.setInativo(produtoForm.isInativo());
    }

    public Produto toEntity() {
        Produto produto = new Produto();
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setTipoProduto(tipoProduto);
        produto.setInativo(inativo);
        return produto;
    }
}
