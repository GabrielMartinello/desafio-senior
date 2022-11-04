package com.desafio.senior.desafiosenior.dto;

import com.desafio.senior.desafiosenior.dto.form.PedidoForm;
import com.desafio.senior.desafiosenior.enums.Situacao;
import com.desafio.senior.desafiosenior.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private String descricao;
    private Situacao situacao;

    private BigDecimal descontoPercentual;

    private BigDecimal descontoTotal;
    private List<ItensPedidoDTO> itensPedido;

    public PedidoDTO(Pedido pedido) {
        this.descricao = pedido.getDescricao();
        this.situacao = pedido.getSituacao();
        this.descontoPercentual = pedido.getDescontoPercentual();
        this.descontoTotal = pedido.getDescontoTotal();
        this.itensPedido = pedido.getItensPedido().stream().map(itemPedido -> new ItensPedidoDTO(itemPedido))
                .collect(Collectors.toList());
    }
    public static Page<PedidoDTO> toDTOFromPage(Page<Pedido> pedidoPage) {
        return pedidoPage.map(pedido -> new PedidoDTO(pedido));
    }
    public static void updateEntity(PedidoForm pedidoForm, Pedido pedido) {
        pedido.setDescricao(pedidoForm.getDescricao());
        pedido.setSituacao(pedido.getSituacao());
    }
}
