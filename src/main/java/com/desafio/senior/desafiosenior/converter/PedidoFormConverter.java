package com.desafio.senior.desafiosenior.converter;

import com.desafio.senior.desafiosenior.dto.form.PedidoForm;
import com.desafio.senior.desafiosenior.model.Pedido;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class PedidoFormConverter {
    @Inject
    private ItensPedidoFormConverter itensPedidoFormConverter;

    public Pedido toEntity(PedidoForm pedidoForm) {
        Pedido pedido = new Pedido();
        pedido.setDescricao(pedidoForm.getDescricao());
        pedido.setSituacao(pedidoForm.getSituacao());
        pedido.setItensPedido(itensPedidoFormConverter.toEntity(pedidoForm.getItensPedido(), pedido));
        return pedido;
    }

    public void updateEntity(PedidoForm pedidoForm, Pedido pedido) {
        pedido.setItensPedido(itensPedidoFormConverter.toEntity(pedidoForm.getItensPedido(), pedido));
        pedido.setDescricao(pedidoForm.getDescricao());
        pedido.setSituacao(pedido.getSituacao());
    }
}
