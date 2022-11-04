package com.desafio.senior.desafiosenior.repository;

import com.desafio.senior.desafiosenior.dto.ProdutoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProdutoRepositoryCustom {

    Page<ProdutoDTO> filterProduto(String descricao, BigDecimal preco, Pageable pageable);

}
