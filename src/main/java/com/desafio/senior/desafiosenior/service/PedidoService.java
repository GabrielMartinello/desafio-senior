package com.desafio.senior.desafiosenior.service;

import com.desafio.senior.desafiosenior.dto.PedidoDTO;
import com.desafio.senior.desafiosenior.dto.form.PedidoForm;
import com.desafio.senior.desafiosenior.enums.Situacao;
import com.desafio.senior.desafiosenior.exception.RegisterNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.HashMap;

public interface PedidoService {
    PedidoDTO save(PedidoForm pedidoForm) throws RegisterNotFoundException;

    Page<PedidoDTO> findAll(Pageable pageable, String descricao, Situacao situacao);

    PedidoDTO findById(String id) throws RegisterNotFoundException;

    void delete(String id) throws RegisterNotFoundException;

    PedidoDTO update(String id,  PedidoForm pedidoForm) throws RegisterNotFoundException;

    PedidoDTO aplicarDescontoPedido(String id, HashMap<String, BigDecimal> field) throws RegisterNotFoundException;
}
