package com.estocaai.backend.Produto.service;

import com.estocaai.backend.Produto.model.Produto;
import com.estocaai.backend.Produto.repository.ProdutoRepository;
import com.estocaai.backend.User.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final UserService userService;

    public ProdutoService(ProdutoRepository produtoRepository, UserService userService) {
        this.produtoRepository = produtoRepository;
        this.userService = userService;
    }

    private boolean isTokenInvalido(String token) {
        return token == null || !userService.isTokenValid(token);
    }

    public ResponseEntity<?> listarTodos(String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        List<Produto> produtos = produtoRepository.findAll();
        return ResponseEntity.ok(produtos);
    }

    public ResponseEntity<?> buscarPorId(String id, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return ResponseEntity.ok(produto);
    }

    public ResponseEntity<?> buscarPorNome(String nome, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        Optional<Produto> produtoOpt = produtoRepository.findByNome(nome);
        if (produtoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        Produto produto = produtoOpt.get();
        return ResponseEntity.ok(produto);
    }

    public ResponseEntity<?> cadastrarProduto(Produto produto, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        Produto novoProduto = produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    public ResponseEntity<?> deletarProduto(String id, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
