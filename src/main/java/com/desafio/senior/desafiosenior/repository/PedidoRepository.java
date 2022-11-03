package com.desafio.senior.desafiosenior.repository;

import com.desafio.senior.desafiosenior.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> { }
