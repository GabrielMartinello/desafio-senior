package com.desafio.senior.desafiosenior.controller;

import com.desafio.senior.desafiosenior.dto.PedidoDTO;
import com.desafio.senior.desafiosenior.dto.form.PedidoForm;
import com.desafio.senior.desafiosenior.enums.Situacao;
import com.desafio.senior.desafiosenior.exception.RegisterNotFoundException;
import com.desafio.senior.desafiosenior.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/save")
    public ResponseEntity<PedidoDTO> save(@RequestBody @Valid PedidoForm pedido) throws RegisterNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.save(pedido));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<PedidoDTO>> findAll(@PageableDefault(sort = "descricao", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pageable,
                                                   @RequestParam(required = false) String descricao,
                                                   @RequestParam(required = false) Situacao situacao
                                                   ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(pedidoService.findAll(pageable, descricao, situacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> findOne(@PathVariable("id") String id) throws RegisterNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(pedidoService.findById(id));
    }

    @PostMapping("/desconto/{id}")
    public ResponseEntity<PedidoDTO> aplicarDescontoPedido(@PathVariable("id") String id, @RequestBody HashMap<String, BigDecimal> desconto) throws RegisterNotFoundException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(pedidoService.aplicarDescontoPedido(id,desconto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable("id") String id) throws RegisterNotFoundException {
        pedidoService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Produto %s excluído com sucesso", id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable("id") String id, @RequestBody PedidoForm pedidoForm) throws RegisterNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(pedidoService.update(id, pedidoForm));
    }
}
