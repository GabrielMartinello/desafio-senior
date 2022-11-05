package com.desafio.senior.desafiosenior.dto.form;

import com.desafio.senior.desafiosenior.enums.Situacao;
import com.desafio.senior.desafiosenior.model.ItensPedido;
import com.desafio.senior.desafiosenior.model.Pedido;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PedidoForm {

    @NotNull @NotEmpty
    private String descricao;

    @NotNull
    private Situacao situacao;
    @NotNull @NotEmpty
    private List<ItensPedidoForm> itensPedido;

    public Pedido toEntity(Pedido pedido, List<ItensPedido> itensPedidos) {;
        pedido.setDescricao(descricao);
        pedido.setSituacao(situacao);
        pedido.setItensPedido(itensPedidos);

        return pedido;
    }
}
