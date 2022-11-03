package com.desafio.senior.desafiosenior.service;

import com.desafio.senior.desafiosenior.converter.PedidoDTOConverter;
import com.desafio.senior.desafiosenior.converter.PedidoFormConverter;
import com.desafio.senior.desafiosenior.dto.PedidoDTO;
import com.desafio.senior.desafiosenior.dto.form.ItensPedidoForm;
import com.desafio.senior.desafiosenior.dto.form.PedidoForm;
import com.desafio.senior.desafiosenior.enums.Situacao;
import com.desafio.senior.desafiosenior.enums.TipoProduto;
import com.desafio.senior.desafiosenior.exeption.ItemInativoException;
import com.desafio.senior.desafiosenior.exeption.RegisterNotFoundException;
import com.desafio.senior.desafiosenior.model.ItensPedido;
import com.desafio.senior.desafiosenior.model.Pedido;
import com.desafio.senior.desafiosenior.repository.PedidoRepository;
import com.desafio.senior.desafiosenior.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

@Service
public class PedidoService {

    @Inject
    private PedidoDTOConverter pedidoConverter;

    @Inject
    private PedidoFormConverter pedidoFormConverter;

    final PedidoRepository pedidoRepository;

    final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public PedidoDTO save(PedidoForm pedidoForm) throws RegisterNotFoundException {

        for (ItensPedidoForm itemPedido : pedidoForm.getItensPedido()) {
            if (!produtoRepository.findById(itemPedido.getProduto().getId()).isPresent()) {
                throw new RegisterNotFoundException(itemPedido.getProduto().getId());
            } else if (Boolean.TRUE.equals(itemPedido.getProduto().isInativo())) {
                throw new ItemInativoException();
            }
        }

        Pedido pedido = pedidoRepository.save(pedidoFormConverter.toEntity(pedidoForm));
        return pedidoConverter.toDTO(pedido);
    }
    public Page<PedidoDTO> findAll(int page, int size) {
        return pedidoConverter.toDTOFromPage(pedidoRepository.findAll(PageRequest.of(page, size)));
    }

    public PedidoDTO findById(String id) throws RegisterNotFoundException {
        UUID uuid = UUID.fromString(id);
        return pedidoConverter.toDTO(pedidoRepository.findById(uuid).orElseThrow(() -> new RegisterNotFoundException(uuid)));
    }

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
            return pedidoConverter.toDTO(pedido);

        } else throw new RuntimeException("O desconto não foi aplicado, " +
                "pois ou o valor dos itens é zero, ou são produtos de serviço!");
    }


    public void delete(String id) throws RegisterNotFoundException {
        UUID uuid = UUID.fromString(id);
        Pedido pedido = pedidoRepository.findById(uuid).orElseThrow(() -> new RegisterNotFoundException(uuid));
        pedidoRepository.delete(pedido);
    }

    public Object update(String id, PedidoForm pedidoForm) throws RegisterNotFoundException {
        UUID uuid = UUID.fromString(id);
        Pedido pedido = pedidoRepository.findById(uuid).orElseThrow(() -> new RegisterNotFoundException(uuid));
        pedidoFormConverter.updateEntity(pedidoForm, pedido);
        return pedidoRepository.save(pedido);
    }
}
