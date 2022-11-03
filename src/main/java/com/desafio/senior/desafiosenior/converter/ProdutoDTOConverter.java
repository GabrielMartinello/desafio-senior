package com.desafio.senior.desafiosenior.converter;

import com.desafio.senior.desafiosenior.dto.ProdutoDTO;
import com.desafio.senior.desafiosenior.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ProdutoDTOConverter {

    public ProdutoDTO toDTO(Produto produto) {
        return ProdutoDTO.builder()
                .descricao(produto.getDescricao())
                .inativo(produto.isInativo())
                .preco(produto.getPreco())
                .tipoProduto(produto.getTipoProduto())
                .build();
    }

    public Page<ProdutoDTO> toDTOFromPage(Page<Produto> produtos) {
        return produtos.map(produto -> toDTO(produto));
    }
}
