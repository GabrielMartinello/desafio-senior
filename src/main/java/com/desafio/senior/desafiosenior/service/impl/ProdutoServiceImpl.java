package com.desafio.senior.desafiosenior.service.impl;

import com.desafio.senior.desafiosenior.dto.ProdutoDTO;
import com.desafio.senior.desafiosenior.dto.form.ProdutoForm;
import com.desafio.senior.desafiosenior.exception.RegisterNotFoundException;
import com.desafio.senior.desafiosenior.model.Produto;
import com.desafio.senior.desafiosenior.repository.ItensPedidoRepository;
import com.desafio.senior.desafiosenior.repository.ProdutoRepository;
import com.desafio.senior.desafiosenior.util.DesafioSeniorUtil;
import com.desafio.senior.desafiosenior.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItensPedidoRepository itensPedidoRepository;

    @Override
    @Transactional
    public ProdutoDTO save(ProdutoForm produto) {
        return new ProdutoDTO(produtoRepository.save(produto.toEntity()));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProdutoDTO> findAll(Pageable pageable, String descricao, BigDecimal preco) {
        if (descricao != null || preco != null) {
            return produtoRepository.filterProduto(descricao, preco, pageable);
        }
        return ProdutoDTO.toDTOFromPage(produtoRepository.findAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public ProdutoDTO findById(String id) throws RegisterNotFoundException {
        UUID uuid = DesafioSeniorUtil.toUUID(id);
        return new ProdutoDTO(produtoRepository.findById(uuid).orElseThrow(() -> new RegisterNotFoundException(uuid)));
    }
    @Override
    @Transactional
    public void delete(String id) throws RegisterNotFoundException {
        UUID uuid = DesafioSeniorUtil.toUUID(id);
        Produto produto = produtoRepository.findById(uuid).orElseThrow(() -> new RegisterNotFoundException(uuid));
        if (itensPedidoRepository.existsByProduto(produto)) {
            throw new RuntimeException("Este produto j?? est?? inclu??do em um pedido!");
        }

        produtoRepository.delete(produto);
    }

    @Override
    @Transactional
    public ProdutoDTO update(String id, ProdutoForm produtoForm) throws RegisterNotFoundException {
        UUID uuid = DesafioSeniorUtil.toUUID(id);
        Produto produto = produtoRepository.findById(uuid).orElseThrow(() -> new RegisterNotFoundException(uuid));
        ProdutoForm.updateEntity(produtoForm, produto);
        return new ProdutoDTO(produtoRepository.save(produto));
    }
}
