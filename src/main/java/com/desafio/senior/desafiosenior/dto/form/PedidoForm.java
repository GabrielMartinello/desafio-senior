package com.desafio.senior.desafiosenior.dto.form;

import com.desafio.senior.desafiosenior.enums.Situacao;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class PedidoForm {

    @NotNull @NotEmpty
    private String descricao;

    @NotNull
    private Situacao situacao;
    @NotNull @NotEmpty
    private List<ItensPedidoForm> itensPedido;

//    public Pedido converter() {
//        Pedido pedido = new Pedido();
//        List<ItensPedido> itensPedidoConverter = itensPedido.stream()
//                .map(item -> new ItensPedido(item.getProduto(), pedido, item.getQuantidade()))
//                .collect(Collectors.toList());
//
//        pedido.setDescricao(descricao);
//        pedido.setSituacao(situacao);
//        pedido.setItensPedido(itensPedidoConverter);
//
//        return pedido;
//    }
}
