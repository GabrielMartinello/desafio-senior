package com.desafio.senior.desafiosenior.controller;

import com.desafio.senior.desafiosenior.dto.PedidoDTO;
import com.desafio.senior.desafiosenior.dto.form.PedidoForm;
import com.desafio.senior.desafiosenior.exception.RegisterNotFoundException;
import com.desafio.senior.desafiosenior.service.PedidoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @InjectMocks
    private PedidoController controller;

    @Mock
    private PedidoService pedidoService;

    private PedidoForm pedido;
    private PedidoDTO dto;
    private UUID pedidoId = UUID.randomUUID();

    @BeforeEach
    void setup() {
        pedido = new PedidoForm();
        pedido.setDescricao("DESC PEDIDO");

        dto = new PedidoDTO();
        dto.setDescricao("DESC PEDIDO");
    }

    @Test
    void testSave() {
        Mockito.when(pedidoService.save(Mockito.any())).thenReturn(dto);
        ResponseEntity<PedidoDTO> retorno = Assertions.assertDoesNotThrow(() -> controller.save(pedido));
        Assertions.assertEquals("DESC PEDIDO", retorno.getBody().getDescricao());
    }

    @Test
    void testFindOne() {
        Mockito.when(pedidoService.findById(Mockito.any())).thenReturn(dto);
        ResponseEntity<PedidoDTO> retorno = Assertions.assertDoesNotThrow(() -> controller.findOne(UUID.randomUUID().toString()));
        Assertions.assertEquals("DESC PEDIDO", retorno.getBody().getDescricao());
    }

    @Test
    void testDeletePedido() {
        ResponseEntity<String> retorno = Assertions.assertDoesNotThrow(() -> controller.deletePedido(pedidoId.toString()));
        Mockito.verify(pedidoService).delete(Mockito.any());
        Assertions.assertEquals("Produto ".concat(pedidoId.toString()).concat( " excluído com sucesso"), retorno.getBody());
    }

    @Test
    void testUpdatePedido() {
        Mockito.when(pedidoService.update(Mockito.any(), Mockito.any())).thenReturn(dto);
        ResponseEntity<PedidoDTO> retorno = Assertions.assertDoesNotThrow(() -> controller.updatePedido(pedidoId.toString(), pedido));
        Assertions.assertEquals("DESC PEDIDO", retorno.getBody().getDescricao());
    }

    @Test
    void testUpdatePedidoError() {
        Mockito.when(pedidoService.update(Mockito.any(), Mockito.any())).thenThrow(RegisterNotFoundException.class);
        Assertions.assertThrows(RegisterNotFoundException.class, () -> controller.updatePedido(pedidoId.toString(), pedido));
    }
}
