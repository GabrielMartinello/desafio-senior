package com.desafio.senior.desafiosenior.dto;

import com.desafio.senior.desafiosenior.enums.TipoProduto;
import com.desafio.senior.desafiosenior.model.Produto;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

@Data
public class ProdutoDTO {

    private String descricao;
    private BigDecimal preco;
    private TipoProduto tipoProduto;
    private boolean inativo;

    public ProdutoDTO(Produto produto) {
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.tipoProduto = produto.getTipoProduto();
        this.inativo = produto.isInativo();
    }

    public static Page<ProdutoDTO> toDTOFromPage(Page<Produto> produtoPage) {
        return produtoPage.map(produto -> new ProdutoDTO(produto));
    }
}
