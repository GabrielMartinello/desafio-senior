package com.desafio.senior.desafiosenior.service;

import com.desafio.senior.desafiosenior.converter.ProdutoDTOConverter;
import com.desafio.senior.desafiosenior.converter.ProdutoFormConverter;
import com.desafio.senior.desafiosenior.dto.ProdutoDTO;
import com.desafio.senior.desafiosenior.dto.form.ProdutoForm;
import com.desafio.senior.desafiosenior.exeption.RegisterNotFoundException;
import com.desafio.senior.desafiosenior.model.Produto;
import com.desafio.senior.desafiosenior.repository.ItensPedidoRepository;
import com.desafio.senior.desafiosenior.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItensPedidoRepository itensPedidoRepository;

    @Inject
    private ProdutoDTOConverter produtoDTOConverter;

    @Inject
    private ProdutoFormConverter produtoFormConverter;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public ProdutoDTO save(ProdutoForm produto) {;
        return produtoDTOConverter.toDTO(produtoRepository.save(produtoFormConverter.toEntity(produto)));
    }

    public Page<ProdutoDTO> findAll(int page, int size) {
        return produtoDTOConverter.toDTOFromPage(produtoRepository.findAll(PageRequest.of(page, size)));
    }

    public ProdutoDTO findById(String id) throws RegisterNotFoundException {
        UUID uuid = UUID.fromString(id);
        return produtoDTOConverter.toDTO(produtoRepository.findById(uuid).orElseThrow(() -> new RegisterNotFoundException(uuid)));
    }
    @Transactional
    public void delete(String id) throws RegisterNotFoundException {
        UUID uuid = UUID.fromString(id);
        Produto produto = produtoRepository.findById(uuid).orElseThrow(() -> new RegisterNotFoundException(uuid));
        if (itensPedidoRepository.existsByProduto(produto)) {
            throw new RuntimeException("Este produto já está incluído em um pedido!");
        }

        produtoRepository.delete(produto);
    }

    @Transactional
    public Object update(String id, ProdutoForm produtoForm) throws RegisterNotFoundException {
        UUID uuid = UUID.fromString(id);
        Produto produto = produtoRepository.findById(uuid).orElseThrow(() -> new RegisterNotFoundException(uuid));
        produtoFormConverter.updateEntity(produtoForm, produto);
        return produtoRepository.save(produto);
    }
}
