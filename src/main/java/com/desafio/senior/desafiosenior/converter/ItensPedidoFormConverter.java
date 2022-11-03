package com.desafio.senior.desafiosenior.converter;

import com.desafio.senior.desafiosenior.dto.form.ItensPedidoForm;
import com.desafio.senior.desafiosenior.model.ItensPedido;
import com.desafio.senior.desafiosenior.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItensPedidoFormConverter {

    public List<ItensPedido> toEntity(List<ItensPedidoForm> itensPedidoForm, Pedido pedido) {
        return itensPedidoForm.stream()
                .map(converter -> new ItensPedido(
                        converter.getProduto(),
                        pedido,
                        converter.getQuantidade()
                )).collect(Collectors.toList());
    }
}
