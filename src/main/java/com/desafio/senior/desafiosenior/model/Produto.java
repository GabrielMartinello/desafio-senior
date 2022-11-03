package com.desafio.senior.desafiosenior.model;

import com.desafio.senior.desafiosenior.enums.TipoProduto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "PRODUTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "PRECO", nullable = false)
    private BigDecimal preco;

    @Column(name = "TIPOPRODUTO", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoProduto tipoProduto;

    @Column(name = "INATIVO")
    private boolean inativo;

    public Produto(String descricao, BigDecimal preco, TipoProduto tipoProduto, boolean inativo) {
        this.descricao = descricao;
        this.preco = preco;
        this.tipoProduto = tipoProduto;
        this.inativo = inativo;
    }
}
