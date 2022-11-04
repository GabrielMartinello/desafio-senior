package com.desafio.senior.desafiosenior.service;

import com.desafio.senior.desafiosenior.dto.ProdutoDTO;
import com.desafio.senior.desafiosenior.dto.form.ProdutoForm;
import com.desafio.senior.desafiosenior.exception.RegisterNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProdutoService {

    ProdutoDTO save(ProdutoForm produto);

    Page<ProdutoDTO> findAll(Pageable pageable, String descricao, BigDecimal preco);

    ProdutoDTO findById(String id) throws RegisterNotFoundException;

    void delete(String id) throws RegisterNotFoundException;

    Object update(String id, ProdutoForm produtoForm) throws RegisterNotFoundException;

}
