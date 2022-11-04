package com.desafio.senior.desafiosenior.service.impl;

import com.desafio.senior.desafiosenior.dto.PedidoDTO;
import com.desafio.senior.desafiosenior.dto.form.ItensPedidoForm;
import com.desafio.senior.desafiosenior.dto.form.PedidoForm;
import com.desafio.senior.desafiosenior.exception.RegisterNotFoundException;
import com.desafio.senior.desafiosenior.model.Produto;
import com.desafio.senior.desafiosenior.repository.PedidoRepository;
import com.desafio.senior.desafiosenior.repository.ProdutoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @InjectMocks
    private PedidoServiceImpl service;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    private PedidoForm pedidoForm;
    private List<ItensPedidoForm> itensPedido;
    private PedidoDTO dto;
    private Produto produto;

    @BeforeEach
    void setup() {
        ItensPedidoForm itemPedido = new ItensPedidoForm();
        itemPedido.setIdProduto(UUID.randomUUID().toString());
        itemPedido.setQuantidade(BigDecimal.TEN);

        pedidoForm = new PedidoForm();
        pedidoForm.setDescricao("DESC");
        pedidoForm.setItensPedido(List.of(itemPedido));

        dto = new PedidoDTO();
        dto.setDescricao("DESC");

        produto = new Produto();
        produto.setId(UUID.fromString(itemPedido.getIdProduto()));

    }

    @Test
    void testSaveEnityItemPedidoNotFound() {
        Mockito.when(produtoRepository.findById(Mockito.any())).thenThrow(RegisterNotFoundException.class);
        Assertions.assertThrows(RegisterNotFoundException.class, () -> service.save(pedidoForm));
    }

    @Test
    void testSave() {
        Mockito.when(produtoRepository.findById(Mockito.any())).thenReturn(Optional.of(produto));
        Mockito.when(pedidoRepository.save(Mockito.any())).thenReturn(dto);
        PedidoDTO retorno = Assertions.assertDoesNotThrow(() -> service.save(pedidoForm));
        Assertions.assertEquals("DESC", retorno.getDescricao());
    }
}
