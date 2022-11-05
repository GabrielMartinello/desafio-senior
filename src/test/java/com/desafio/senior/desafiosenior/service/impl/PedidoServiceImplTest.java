package com.desafio.senior.desafiosenior.service.impl;

import com.desafio.senior.desafiosenior.dto.ItensPedidoDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @InjectMocks
    private PedidoServiceImpl service;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    private PedidoForm pedidoForm;
    private ItensPedidoForm itemPedido;
    private PedidoDTO dto;
    private Produto produto;
    private Pedido pedido;
    private Pageable pageable;
    private Page<Pedido> pedidoPage;

    @BeforeEach
    void setup() {
        itemPedido = new ItensPedidoForm();
        itemPedido.setIdProduto(UUID.randomUUID().toString());
        itemPedido.setQuantidade(BigDecimal.TEN);

        pedidoForm = new PedidoForm();
        pedidoForm.setDescricao("DESC");
        pedidoForm.setSituacao(Situacao.A);
        pedidoForm.setItensPedido(List.of(itemPedido));


        produto = new Produto();
        produto.setId(UUID.fromString(itemPedido.getIdProduto()));
        produto.setTipoProduto(TipoProduto.P);
        produto.setInativo(Boolean.FALSE);
        produto.setPreco(BigDecimal.TEN);

        ItensPedido itemPedEntity = new ItensPedido();
        itemPedEntity.setProduto(produto);

        pedido = new Pedido();
        pedido.setId(UUID.randomUUID());
        pedido.setDescricao("DESC");
        pedido.setItensPedido(List.of(itemPedEntity));

        dto = new PedidoDTO();
        dto.setDescricao("DESC");
        dto.setSituacao(Situacao.A);
        dto.setItensPedido(ItensPedidoDTO.toDTOFromList(List.of(itemPedEntity)));

        pageable = Mockito.mock(Pageable.class);

        pedidoPage = new Page<Pedido>() {
            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 1;
            }

            @Override
            public <U> Page<U> map(Function<? super Pedido, ? extends U> converter) {
                return Mockito.mock(Page.class);
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 1;
            }

            @Override
            public int getNumberOfElements() {
                return 1;
            }

            @Override
            public List<Pedido> getContent() {
                return List.of(pedido);
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<Pedido> iterator() {
                return null;
            }
        };
    }

    @Test
    void testSaveEntityItemPedidoNotFound() {
        Mockito.when(produtoRepository.findById(Mockito.any())).thenThrow(RegisterNotFoundException.class);
        Assertions.assertThrows(RegisterNotFoundException.class, () -> service.save(pedidoForm));
    }

    @Test
    void testSaveEntityItemPedidoInativo() {
        produto.setInativo(Boolean.TRUE);
        Mockito.when(produtoRepository.findById(Mockito.any())).thenReturn(Optional.of(produto));
        Assertions.assertThrows(ItemInativoException.class, () -> service.save(pedidoForm));
    }

    @Test
    void testSave() {
        Mockito.when(produtoRepository.findById(Mockito.any())).thenReturn(Optional.of(produto));
        Mockito.when(pedidoRepository.save(Mockito.any())).thenReturn(pedido);
        PedidoDTO retorno = Assertions.assertDoesNotThrow(() -> service.save(pedidoForm));
        Assertions.assertEquals("DESC", retorno.getDescricao());
    }

    @Test
    void testDelete() {
        Mockito.when(pedidoRepository.findById(Mockito.any())).thenReturn(Optional.of(pedido));
        Assertions.assertDoesNotThrow(() -> service.delete(pedido.getId().toString()));
        Mockito.verify(pedidoRepository).delete(Mockito.any());
    }

    @Test
    void testDeleteError() {
        Mockito.when(pedidoRepository.findById(Mockito.any())).thenThrow(RegisterNotFoundException.class);
        Assertions.assertThrows(RegisterNotFoundException.class, () -> service.delete(pedido.getId().toString()));
    }

    @Test
    void testFindallDescricaoSituacaoNulos() {
        Mockito.when(pedidoRepository.findAll(pageable)).thenReturn(pedidoPage);
        Assertions.assertDoesNotThrow(() -> service.findAll(pageable, null, null));
    }

    @Test
    void testFindall() {
        Mockito.when(pedidoRepository.filterPedido(Mockito.any(),Mockito.any(), Mockito.any())).thenReturn(Mockito.mock(Page.class));
        Assertions.assertDoesNotThrow(() -> service.findAll(pageable,  "DESCRICAO", Situacao.A));
    }

    @Test
    void testUpdate() {
        pedidoForm.setDescricao("UPDATE");
        itemPedido.setIdItemPedido(pedido.getId().toString());
        Mockito.when(produtoRepository.findById(Mockito.any())).thenReturn(Optional.of(produto));
        Mockito.when(pedidoRepository.findById(Mockito.any())).thenReturn(Optional.of(pedido));
        Mockito.when(pedidoRepository.save(Mockito.any())).thenReturn(pedido);
        PedidoDTO retorno = Assertions.assertDoesNotThrow(() -> service.update(pedido.getId().toString(), pedidoForm));
        Assertions.assertEquals("UPDATE", retorno.getDescricao());
    }
}
