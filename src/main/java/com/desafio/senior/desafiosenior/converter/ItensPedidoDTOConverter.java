package com.desafio.senior.desafiosenior.converter;

import com.desafio.senior.desafiosenior.dto.ItensPedidoDTO;
import com.desafio.senior.desafiosenior.model.ItensPedido;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItensPedidoDTOConverter {

    @Inject
    ProdutoDTOConverter produtoDTOConverter;
    public ItensPedidoDTO toDTO(ItensPedido itensPedido) {
        return ItensPedidoDTO
                .builder()
                .produto(produtoDTOConverter.toDTO(itensPedido.getProduto()))
                .quantidade(itensPedido.getQuantidade())
                .build();
    }

    public List<ItensPedidoDTO> toDTOFromList(List<ItensPedido> itensPedido) {
        if (CollectionUtils.isNotEmpty(itensPedido)) {
            return itensPedido.stream().map(item -> toDTO(item)).collect(Collectors.toList());
        }
        return null;
    }
}
