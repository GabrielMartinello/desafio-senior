package com.desafio.senior.desafiosenior.dto.form;

import com.desafio.senior.desafiosenior.exception.ItemInativoException;
import com.desafio.senior.desafiosenior.model.ItensPedido;
import com.desafio.senior.desafiosenior.model.Pedido;
import com.desafio.senior.desafiosenior.model.Produto;
import com.desafio.senior.desafiosenior.repository.ProdutoRepository;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class ItensPedidoForm {

    @NotEmpty
    @NotNull
    private String idProduto;

    @NotEmpty @NotNull
    private Pedido pedido;

    @NotNull
    private BigDecimal quantidade;

    public static List<ItensPedido> toEntity(List<ItensPedidoForm> itensPedidoForms,
                                             ProdutoRepository produtoRepository, Pedido pedido) {
        return itensPedidoForms.stream().map(itemPedido -> {
            Produto produto = produtoRepository.findById(UUID.fromString(itemPedido.getIdProduto())).get();
            if (Boolean.TRUE.equals(produto.isInativo())) {
                throw new ItemInativoException();
            }

            ItensPedido itensPedido = new ItensPedido();
            itensPedido.setProduto(produto);
            itensPedido.setPedido(pedido);
            itensPedido.setQuantidade(itemPedido.getQuantidade());

            return itensPedido;
        }).collect(Collectors.toList());
    }
    public static ItensPedido toEntity(Pedido pedido, Produto produto, BigDecimal quantidade) {
        ItensPedido itensPedido = new ItensPedido();
        itensPedido.setPedido(pedido);
        itensPedido.setQuantidade(quantidade);
        itensPedido.setProduto(produto);

        return itensPedido;
    }
}
