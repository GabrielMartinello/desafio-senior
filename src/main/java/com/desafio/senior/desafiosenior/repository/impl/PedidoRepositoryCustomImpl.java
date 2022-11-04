package com.desafio.senior.desafiosenior.repository.impl;

import com.desafio.senior.desafiosenior.dto.PedidoDTO;
import com.desafio.senior.desafiosenior.enums.Situacao;
import com.desafio.senior.desafiosenior.model.ItensPedido;
import com.desafio.senior.desafiosenior.model.Pedido;
import com.desafio.senior.desafiosenior.model.QItensPedido;
import com.desafio.senior.desafiosenior.model.QPedido;
import com.desafio.senior.desafiosenior.repository.PedidoRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class PedidoRepositoryCustomImpl implements PedidoRepositoryCustom {

    @Autowired
    EntityManager entityManager;

    @Override
    public Page<PedidoDTO> filterPedido(String descricao, Situacao situacao, Pageable pageable) {
        JPAQuery<Pedido> query = new JPAQuery<>(entityManager);
        QPedido pedido = QPedido.pedido;

        BooleanBuilder builder = new BooleanBuilder();

        if (descricao != null) {
            builder.and(pedido.descricao.contains(descricao));
        }

        if (situacao != null) {
            builder.and(pedido.situacao.eq(situacao));
        }

        List<Pedido> pedidos = query.from(QPedido.pedido).where(builder).fetch();
        return PedidoDTO.toDTOFromPage(new PageImpl<>(pedidos, pageable, pedidos.size()));
    }
}
