package com.desafio.senior.desafiosenior.converter;

import com.desafio.senior.desafiosenior.dto.form.ProdutoForm;
import com.desafio.senior.desafiosenior.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoFormConverter {
    public void updateEntity(ProdutoForm produtoForm, Produto produto) {
        produto.setTipoProduto(produtoForm.getTipoProduto());
        produto.setPreco(produtoForm.getPreco());
        produto.setDescricao(produtoForm.getDescricao());
        produto.setInativo(produtoForm.isInativo());
    }

    public Produto toEntity(ProdutoForm produtoForm) {
        Produto produto = new Produto();
        produto.setTipoProduto(produtoForm.getTipoProduto());
        produto.setInativo(produtoForm.isInativo());
        produto.setPreco(produtoForm.getPreco());
        produto.setDescricao(produtoForm.getDescricao());

        return produto;
    }

}
