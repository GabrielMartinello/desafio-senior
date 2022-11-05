package com.desafio.senior.desafiosenior.repository.impl;

import com.desafio.senior.desafiosenior.dto.ProdutoDTO;
import com.desafio.senior.desafiosenior.model.Produto;
import com.desafio.senior.desafiosenior.model.QProduto;
import com.desafio.senior.desafiosenior.repository.ProdutoRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoRepositoryCustomImpl implements ProdutoRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Page<ProdutoDTO> filterProduto(String descricao, BigDecimal preco, Pageable pageable) {
        JPAQuery<Produto> query = new JPAQuery<>(entityManager);
        QProduto produto = QProduto.produto;
        BooleanBuilder builder = new BooleanBuilder();

        if (descricao != null) {
            builder.and(produto.descricao.contains(descricao));
        }

        if (preco != null) {
            builder.and(produto.preco.eq(preco));
        }


        List<Produto> produtos = query.from(QProduto.produto).where(builder).fetch();
        return ProdutoDTO.toDTOFromPage(new PageImpl<>(produtos, pageable, produtos.size()));
    }


}
