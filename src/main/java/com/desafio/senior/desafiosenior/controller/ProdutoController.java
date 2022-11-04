package com.desafio.senior.desafiosenior.controller;

import com.desafio.senior.desafiosenior.dto.ProdutoDTO;
import com.desafio.senior.desafiosenior.dto.form.ProdutoForm;
import com.desafio.senior.desafiosenior.exception.RegisterNotFoundException;
import com.desafio.senior.desafiosenior.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @PostMapping("/save")
    public ResponseEntity<ProdutoDTO> save(@RequestBody @Valid ProdutoForm produtoForm) {;
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.save(produtoForm));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ProdutoDTO>> findAll(
            @PageableDefault(sort = "preco", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable page,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) BigDecimal preco) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.findAll(page, descricao,preco));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable("id") String id) throws RegisterNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) throws RegisterNotFoundException {
        produtoService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Produto %s deletado com sucesso!", id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody ProdutoForm produtoForm) throws RegisterNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.update(id, produtoForm));
    }
}
