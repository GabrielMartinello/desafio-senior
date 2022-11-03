package com.desafio.senior.desafiosenior.converter;

import com.desafio.senior.desafiosenior.dto.PedidoDTO;
import com.desafio.senior.desafiosenior.model.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class PedidoDTOConverter {

    @Inject
    private ItensPedidoDTOConverter converter;

    public PedidoDTO toDTO(Pedido pedido) {
        return PedidoDTO
                .builder()
                .descricao(pedido.getDescricao())
                .descontoPercentual(pedido.getDescontoPercentual())
                .descontoTotal(pedido.getDescontoTotal())
                .situacao(pedido.getSituacao())
                .itensPedido(converter.toDTOFromList(pedido.getItensPedido()))
                .build();
    }

    public Page<PedidoDTO> toDTOFromPage(Page<Pedido> pedidoPage) {
       return pedidoPage.map(pedido -> toDTO(pedido));
    }
}
