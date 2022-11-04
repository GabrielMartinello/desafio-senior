package com.desafio.senior.desafiosenior.repository;

import com.desafio.senior.desafiosenior.model.Produto;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Primary
public interface ProdutoRepository extends JpaRepository<Produto, UUID>, ProdutoRepositoryCustom {

}
