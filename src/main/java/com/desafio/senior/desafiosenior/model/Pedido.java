package com.desafio.senior.desafiosenior.model;

import com.desafio.senior.desafiosenior.enums.Situacao;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "PEDIDO")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Pedido implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "SITUACAO")
    @Enumerated(EnumType.STRING)
    private Situacao situacao;

    @Column(name = "DESCONTOPERCENTUAL")
    private BigDecimal descontoPercentual = BigDecimal.ZERO;

    @Column(name = "DESCONTOTOTAL")
    private BigDecimal descontoTotal = BigDecimal.ZERO;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItensPedido> itensPedido;

    public Pedido(String descricao, Situacao situacao) {
        this.descricao = descricao;
        this.situacao = situacao;
    }
}
