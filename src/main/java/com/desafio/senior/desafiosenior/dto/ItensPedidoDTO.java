package com.desafio.senior.desafiosenior.dto;

import com.desafio.senior.desafiosenior.model.ItensPedido;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ItensPedidoDTO {

    private ProdutoDTO produto;
    private BigDecimal quantidade;

    public ItensPedidoDTO (ItensPedido itensPedido) {
        produto = new ProdutoDTO(itensPedido.getProduto());
        quantidade = itensPedido.getQuantidade();
    }
    public static List<ItensPedidoDTO> toDTOFromList(List<ItensPedido> itensPedido) {
        if (CollectionUtils.isNotEmpty(itensPedido)) {
            return itensPedido.stream().map(item -> new ItensPedidoDTO(item)).collect(Collectors.toList());
        }
        return null;
    }
}
