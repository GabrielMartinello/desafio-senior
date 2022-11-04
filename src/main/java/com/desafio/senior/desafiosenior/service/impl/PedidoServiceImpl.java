package com.desafio.senior.desafiosenior.service.impl;

import com.desafio.senior.desafiosenior.dto.PedidoDTO;
import com.desafio.senior.desafiosenior.dto.form.ItensPedidoForm;
import com.desafio.senior.desafiosenior.dto.form.PedidoForm;
import com.desafio.senior.desafiosenior.enums.Situacao;
import com.desafio.senior.desafiosenior.enums.TipoProduto;
import com.desafio.senior.desafiosenior.exception.RegisterNotFoundException;
import com.desafio.senior.desafiosenior.model.ItensPedido;
import com.desafio.senior.desafiosenior.model.Pedido;
import com.desafio.senior.desafiosenior.model.Produto;
import com.desafio.senior.desafiosenior.repository.PedidoRepository;
import com.desafio.senior.desafiosenior.repository.ProdutoRepository;
import com.desafio.senior.desafiosenior.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    @Override
    public PedidoDTO save(PedidoForm pedidoForm) throws RegisterNotFoundException {
        Pedido pedido = new Pedido();
        List<ItensPedido> itensPedidos = new ArrayList<>();
        for (ItensPedidoForm itemPedidoForm : pedidoForm.getItensPedido()) {
            Produto produto = produtoRepository.findById(UUID.fromString(itemPedidoForm.getIdProduto()))
                    .orElseThrow(() -> new RegisterNotFoundException(UUID.fromString(itemPedidoForm.getIdProduto())));

            ItensPedido itemPedido = itemPedidoForm.toEntity(pedido, produto, itemPedidoForm.getQuantidade());
            itensPedidos.add(itemPedido);
        }

        pedidoRepository.save(pedidoForm.toEntity(pedido, itensPedidos));
        return new PedidoDTO(pedido);
    }

    @Override
    public Page<PedidoDTO> findAll(Pageable pageable, String descricao, Situacao situacao) {
        if (descricao != null || situacao != null) {
            return pedidoRepository.filterPedido(descricao, situacao, pageable);

        }
        return PedidoDTO.toDTOFromPage(pedidoRepository.findAll(pageable));
    }

    @Override
    public PedidoDTO findById(String id) throws RegisterNotFoundException {
        UUID uuid = UUID.fromString(id);
        return new PedidoDTO(pedidoRepository.findById(uuid).orElseThrow(() -> new RegisterNotFoundException(uuid)));
    }

    @Override
    public PedidoDTO aplicarDescontoPedido(String id, HashMap<String, BigDecimal> field) throws RegisterNotFoundException {
        BigDecimal desconto = field.get("desconto");
        Pedido pedido = pedidoRepository.findById(UUID.fromString(id)).get();

        if (Situacao.F.equals(pedido.getSituacao())) {
            throw new RuntimeException("A situação do pedido está fechada!");
        }

        BigDecimal valorTotalPedidoProdutos = BigDecimal.ZERO;
        for (ItensPedido item: pedido.getItensPedido()) {
            if (TipoProduto.P.equals(item.getProduto().getTipoProduto())) {
                valorTotalPedidoProdutos = item.getQuantidade().multiply(item.getProduto().getPreco());
            }
        }

        if (BigDecimal.ZERO.compareTo(valorTotalPedidoProdutos) < 0) {
            BigDecimal valorDescontoTotal = desconto.multiply(valorTotalPedidoProdutos).divide(new BigDecimal(100));

            pedido.setDescontoPercentual(desconto);
            pedido.setDescontoTotal(valorDescontoTotal);
            pedidoRepository.save(pedido);
            return new PedidoDTO(pedido);

        } else throw new RuntimeException("O desconto não foi aplicado, " +
                "pois ou o valor dos itens é zero, ou são produtos de serviço!");
    }


    @Override
    public void delete(String id) throws RegisterNotFoundException {
        UUID uuid = UUID.fromString(id);
        Pedido pedido = pedidoRepository.findById(uuid).orElseThrow(() -> new RegisterNotFoundException(uuid));
        pedidoRepository.delete(pedido);
    }

    //Ajustar
    @Override
    public PedidoDTO update(String id, PedidoForm pedidoForm) throws RegisterNotFoundException {
        UUID uuid = UUID.fromString(id);
        Pedido pedido = pedidoRepository.findById(uuid).orElseThrow(() -> new RegisterNotFoundException(uuid));

        List<ItensPedido> itensPedidos = new ArrayList<>();
        pedidoForm.getItensPedido().forEach(p -> {
            Produto produto = produtoRepository.findById(UUID.fromString(p.getIdProduto()))
                    .orElseThrow(() -> new RegisterNotFoundException(UUID.fromString(p.getIdProduto())));

            ItensPedido itemPedido = p.toEntity(pedido, produto, p.getQuantidade());
            itensPedidos.add(itemPedido);
        });

        pedido.setItensPedido(itensPedidos);
        PedidoDTO.updateEntity(pedidoForm, pedido);
        pedidoRepository.save(pedido);
        return new PedidoDTO(pedido);
    }
}
