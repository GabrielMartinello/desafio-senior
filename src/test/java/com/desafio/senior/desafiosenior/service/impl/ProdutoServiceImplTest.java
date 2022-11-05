package com.desafio.senior.desafiosenior.service.impl;

import com.desafio.senior.desafiosenior.dto.PedidoDTO;
import com.desafio.senior.desafiosenior.dto.ProdutoDTO;
import com.desafio.senior.desafiosenior.dto.form.ProdutoForm;
import com.desafio.senior.desafiosenior.enums.TipoProduto;
import com.desafio.senior.desafiosenior.exception.RegisterNotFoundException;
import com.desafio.senior.desafiosenior.model.ItensPedido;
import com.desafio.senior.desafiosenior.model.Pedido;
import com.desafio.senior.desafiosenior.model.Produto;
import com.desafio.senior.desafiosenior.repository.ItensPedidoRepository;
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
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceImplTest {

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ItensPedidoRepository itensPedidoRepository;

    private ProdutoForm produtoForm;

    private Produto produto;

    private ProdutoDTO dto;
    private ItensPedido itensPedido;

    private Pedido pedido;

    @BeforeEach
    void setup() {
        produtoForm = new ProdutoForm();
        produtoForm.setTipoProduto(TipoProduto.P);
        produtoForm.setDescricao("Teste");
        produtoForm.setPreco(BigDecimal.TEN);
        produtoForm.setInativo(Boolean.FALSE);

        produto = new Produto();
        produto.setId(UUID.randomUUID());
        produto.setDescricao("Teste");
        produto.setTipoProduto(TipoProduto.P);
        produto.setInativo(Boolean.FALSE);
        produto.setPreco(BigDecimal.TEN);


        dto = new ProdutoDTO();
        dto.setDescricao("Teste");
        dto.setTipoProduto(TipoProduto.P);
        dto.setPreco(BigDecimal.TEN);
        dto.setInativo(Boolean.FALSE);

        Pedido pedido = new Pedido();
        pedido.setDescricao("Pedido Teste");


        itensPedido = new ItensPedido();
        itensPedido.setId(UUID.randomUUID());
        itensPedido.setPedido(pedido);
        itensPedido.setProduto(produto);
        itensPedido.setQuantidade(BigDecimal.TEN);

    }

    @Test
    void testSaveEntityProduto() {
        Mockito.when(produtoRepository.save(Mockito.any())).thenReturn(produto);
        ProdutoDTO produtoDTO = Assertions.assertDoesNotThrow(() -> produtoService.save(produtoForm));
        Assertions.assertEquals("Teste", produtoDTO.getDescricao());
    }

    @Test
    void testFindEntityProdutoByIdError() {
        Mockito.when(produtoRepository.findById(Mockito.any())).thenThrow(RegisterNotFoundException.class);
        Assertions.assertThrows(RegisterNotFoundException.class, () -> produtoService.findById(produto.getId().toString()));
    }

    @Test
    void testFindEntityProdutoById() {
        Mockito.when(produtoRepository.findById(Mockito.any())).thenReturn(Optional.of(produto));
        ProdutoDTO produtoDTO = Assertions.assertDoesNotThrow(() -> produtoService.findById(produto.getId().toString()));
        Assertions.assertEquals("Teste", produtoDTO.getDescricao());
    }

    @Test
    void testUpdateEntityProduto() {
        produtoForm.setDescricao("Produto Update");
        Mockito.when(produtoRepository.findById(Mockito.any())).thenReturn(Optional.of(produto));
        Mockito.when(produtoRepository.save(Mockito.any())).thenReturn(produto);
        ProdutoDTO produtoDTO = Assertions.assertDoesNotThrow(() -> produtoService.update(produto.getId().toString(), produtoForm));
        Assertions.assertEquals("Produto Update", produtoDTO.getDescricao());

    }

    @Test
    void testDelete() {
        Mockito.when(produtoRepository.findById(Mockito.any())).thenReturn(Optional.of(produto));
        Mockito.when(itensPedidoRepository.existsByProduto(Mockito.any())).thenReturn(Boolean.FALSE);

        Assertions.assertDoesNotThrow(() -> produtoService.delete(produto.getId().toString()));
        Mockito.verify(produtoRepository).delete(Mockito.any());
    }


}
