package com.desafio.senior.desafiosenior.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "ITENSPEDIDO")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItensPedido {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "PRODUTO", referencedColumnName = "ID")
    private Produto produto;

    @Column(name = "QUANTIDADE")
    private BigDecimal quantidade;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "PEDIDO", referencedColumnName = "ID")
    private Pedido pedido;

    public ItensPedido(Produto produto,Pedido pedido, BigDecimal quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.pedido = pedido;
    }

}
