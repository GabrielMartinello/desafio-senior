package com.desafio.senior.desafiosenior.repository;

import com.desafio.senior.desafiosenior.dto.PedidoDTO;
import com.desafio.senior.desafiosenior.enums.Situacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidoRepositoryCustom {
    Page<PedidoDTO> filterPedido(String descricao, Situacao situacao, Pageable pageable);
}
