package com.desafio.senior.desafiosenior.repository;

import com.desafio.senior.desafiosenior.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

}
