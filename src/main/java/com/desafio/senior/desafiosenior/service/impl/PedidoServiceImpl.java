package com.desafio.senior.desafiosenior.service.impl;

import com.desafio.senior.desafiosenior.dto.PedidoDTO;
import com.desafio.senior.desafiosenior.dto.form.ItensPedidoForm;
import com.desafio.senior.desafiosenior.dto.form.PedidoForm;
import com.desafio.senior.desafiosenior.enums.Situacao;
import com.desafio.senior.desafiosenior.enums.TipoProduto;
import com.desafio.senior.desafiosenior.exception.ItemInativoException;
import com.desafio.senior.desafiosenior.exception.RegisterNotFoundException;
import com.desafio.senior.desafiosenior.model.ItensPedido;
import com.desafio.senior.desafiosenior.model.Pedido;
import com.desafio.senior.desafiosenior.model.Produto;
import com.desafio.senior.desafiosenior.repository.ItensPedidoRepository;
import com.desafio.senior.desafiosenior.repository.PedidoRepository;
import com.desafio.senior.desafiosenior.repository.ProdutoRepository;
import com.desafio.senior.desafiosenior.util.DesafioSeniorUtil;
import com.desafio.senior.desafiosenior.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ItensPedidoRepository itensPedidoRepository;


    @Override
    @Transactional
    public PedidoDTO save(PedidoForm pedidoForm) throws RegisterNotFoundException {
        Pedido pedido = new Pedido();
        List<ItensPedido> itensPedidos = new ArrayList<>();
        for (ItensPedidoForm itemPedidoForm : pedidoForm.getItensPedido()) {
            Produto produto = produtoRepository.findById(DesafioSeniorUtil.toUUID(itemPedidoForm.getIdProduto()))
                    .orElseThrow(() -> new RegisterNotFoundException(DesafioSeniorUtil.toUUID(itemPedidoForm.getIdProduto())));

            if (Boolean.TRUE.equals(produto.isInativo())) {
                throw new ItemInativoException();
            }

            ItensPedido itemPedido = itemPedidoForm.toEntity(pedido, produto, itemPedidoForm.getQuantidade());
            itensPedidos.add(itemPedido);
        }

        pedidoRepository.save(pedidoForm.toEntity(pedido, itensPedidos));
        return new PedidoDTO(pedido);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PedidoDTO> findAll(Pageable pageable, String descricao, Situacao situacao) {
        if (descricao != null || situacao != null) {
            return pedidoRepository.filterPedido(descricao, situacao, pageable);

        }
        return PedidoDTO.toDTOFromPage(pedidoRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoDTO findById(String id) throws RegisterNotFoundException {
        return new PedidoDTO(pedidoRepository.findById(DesafioSeniorUtil.toUUID(id))
                .orElseThrow(() -> new RegisterNotFoundException(DesafioSeniorUtil.toUUID(id))));
    }

    @Override
    @Transactional
    public PedidoDTO aplicarDescontoPedido(String id, HashMap<String, BigDecimal> field) throws RegisterNotFoundException {
        BigDecimal desconto = field.get("desconto");
        Pedido pedido = pedidoRepository.findById(DesafioSeniorUtil.toUUID(id)).get();

        if (Situacao.F.equals(pedido.getSituacao())) {
            throw new RuntimeException("A situação do pedido está fechada!");
        }

        BigDecimal valorTotalPedidoProdutos = BigDecimal.ZERO;
        for (ItensPedido item : pedido.getItensPedido()) {
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
        }

        throw new RuntimeException("O desconto não foi aplicado, " +
                "pois ou o valor dos itens é zero, ou são produtos de serviço!");
    }


    @Override
    @Transactional
    public void delete(String id) throws RegisterNotFoundException {
        Pedido pedido = pedidoRepository.findById(DesafioSeniorUtil.toUUID(id))
                .orElseThrow(() -> new RegisterNotFoundException(DesafioSeniorUtil.toUUID(id)));
        pedidoRepository.delete(pedido);
    }
    @Override
    @Transactional
    public PedidoDTO update(String id, PedidoForm pedidoForm) throws RegisterNotFoundException {
        UUID uuid = DesafioSeniorUtil.toUUID(id);
        Pedido pedido = pedidoRepository.findById(uuid)
                .orElseThrow(() -> new RegisterNotFoundException(uuid));

        pedido.setDescricao(pedidoForm.getDescricao());
        pedido.setSituacao(pedidoForm.getSituacao());

        List<ItensPedido> itensPedidos = new ArrayList<>();

        if (!pedidoForm.getItensPedido().isEmpty()) {
            pedidoForm.getItensPedido().forEach(p -> {
                if(p.getIdProduto() != null && !p.getIdProduto().isEmpty()) {
                    ItensPedido item = new ItensPedido();
                    item.setId(DesafioSeniorUtil.toUUID(p.getIdItemPedido()));

                    Produto produto = produtoRepository
                            .findById(DesafioSeniorUtil.toUUID(p.getIdProduto()))
                            .orElseThrow(() -> new RegisterNotFoundException(DesafioSeniorUtil.toUUID(p.getIdProduto())));

                    item.setProduto(produto);
                    item.setQuantidade(p.getQuantidade());
                    item.setPedido(pedido);

                    itensPedidos.add(item);
                } else if (!p.getIdItemPedido().isEmpty() && p.getIdItemPedido() != null) {
                    itensPedidoRepository.deleteById(DesafioSeniorUtil.toUUID(p.getIdItemPedido()));
                }
            });
        } else {
            itensPedidoRepository.deleteByPedidoId(DesafioSeniorUtil.toUUID(id));
        }

        pedido.setItensPedido(itensPedidos);
        pedidoRepository.save(pedido);
        return new PedidoDTO(pedido);
    }
}
