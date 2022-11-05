package com.desafio.senior.desafiosenior.dto.form;

import com.desafio.senior.desafiosenior.model.ItensPedido;
import com.desafio.senior.desafiosenior.model.Pedido;
import com.desafio.senior.desafiosenior.model.Produto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ItensPedidoForm {

    @NotEmpty
    @NotNull
    private String idProduto;

    @NotEmpty @NotNull
    private Pedido pedido;

    private String idItemPedido;

    @NotNull
    private BigDecimal quantidade;
    public static ItensPedido toEntity(Pedido pedido, Produto produto, BigDecimal quantidade) {
        ItensPedido itensPedido = new ItensPedido();
        itensPedido.setPedido(pedido);
        itensPedido.setQuantidade(quantidade);
        itensPedido.setProduto(produto);

        return itensPedido;
    }
}
