package com.estocaai.backend.Produto.controller;

import com.estocaai.backend.Produto.model.Produto;
import com.estocaai.backend.Produto.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService; // private final para garantir que o atributo não será alterado

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<?> listarTodos(@RequestHeader(value = "Authorization") String token) {
        return produtoService.listarTodos(token);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable String id,
                                         @RequestHeader(value = "Authorization") String token) {
        return produtoService.buscarPorId(id, token);
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNome(@RequestParam String nome,
                                           @RequestHeader(value = "Authorization") String token) {
        return produtoService.buscarPorNome(nome, token);
    }

    @PostMapping
    public ResponseEntity<?> cadastrarProduto(@RequestBody Produto produto,
                                              @RequestHeader(value = "Authorization") String token) {
        return produtoService.cadastrarProduto(produto, token);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarProduto(@PathVariable String id,
                                            @RequestHeader(value = "Authorization") String token) {
        return produtoService.deletarProduto(id, token);
    }
}
