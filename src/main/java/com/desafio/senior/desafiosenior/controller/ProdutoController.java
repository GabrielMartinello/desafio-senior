package com.desafio.senior.desafiosenior.controller;

import com.desafio.senior.desafiosenior.dto.ProdutoDTO;
import com.desafio.senior.desafiosenior.dto.form.ProdutoForm;
import com.desafio.senior.desafiosenior.exeption.RegisterNotFoundException;
import com.desafio.senior.desafiosenior.model.Produto;
import com.desafio.senior.desafiosenior.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/save")
    public ResponseEntity<ProdutoDTO> save(@RequestBody @Valid ProdutoForm produtoForm) {;
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.save(produtoForm));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ProdutoDTO>> findAll(@RequestParam("page") int page,
                                                    @RequestParam("size") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.findAll(page, size));
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
