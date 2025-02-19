package com.estocaai.backend.Despensa.service;

import com.estocaai.backend.Despensa.model.Despensa;
import com.estocaai.backend.Despensa.repository.DespensaRepository;
import com.estocaai.backend.Produto.repository.ProdutoRepository;
import com.estocaai.backend.User.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DespensaService {

    private final DespensaRepository despensaRepository;
    private final ProdutoRepository produtoRepository;
    private final UserService userService;

    public DespensaService(DespensaRepository despensaRepository, ProdutoRepository produtoRepository, UserService userService) {
        this.despensaRepository = despensaRepository;
        this.produtoRepository = produtoRepository;
        this.userService = userService;
    }

    private boolean isTokenInvalido(String token) {
        return token == null || !userService.isTokenValid(token);
    }

    public ResponseEntity<?> getDespensa(String casaId, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        Despensa despensa = despensaRepository.findByCasaId(casaId)
                .orElseThrow(() -> new RuntimeException("Despensa não encontrada!"));
        return ResponseEntity.ok(despensa);
    }

    public ResponseEntity<?> adicionarProduto(String casaId, String produtoId, int quantidade, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        Despensa despensa = despensaRepository.findByCasaId(casaId)
                .orElseThrow(() -> new RuntimeException("Despensa não encontrada!"));
        if (!produtoRepository.existsById(produtoId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
        }
        int index = despensa.getProdutosIds().indexOf(produtoId);
        if (index != -1) {
            int currentQuantity = despensa.getProdutosQuantidades().get(index);
            despensa.getProdutosQuantidades().set(index, currentQuantity + quantidade);
        } else {
            despensa.getProdutosIds().add(produtoId);
            despensa.getProdutosQuantidades().add(quantidade);
        }
        despensaRepository.save(despensa);
        return ResponseEntity.ok(despensa);
    }

    public ResponseEntity<?> removerProduto(String casaId, String produtoId, int quantidade, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        Despensa despensa = despensaRepository.findByCasaId(casaId)
                .orElseThrow(() -> new RuntimeException("Despensa não encontrada!"));
        despensa.removerProduto(produtoId, quantidade);
        despensaRepository.save(despensa);
        return ResponseEntity.ok(despensa);
    }

    public ResponseEntity<?> listarProdutos(String casaId, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        Despensa despensa = despensaRepository.findByCasaId(casaId)
                .orElseThrow(() -> new RuntimeException("Despensa não encontrada!"));
        return ResponseEntity.ok(produtoRepository.findAllById(despensa.getProdutosIds()));
    }

    public ResponseEntity<?> atualizarQuantidade(String casaId, String produtoId, int quantidade, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        Despensa despensa = despensaRepository.findByCasaId(casaId)
                .orElseThrow(() -> new RuntimeException("Despensa não encontrada!"));
        int index = despensa.getProdutosIds().indexOf(produtoId);
        if (index == -1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado na despensa!");
        }
        if (quantidade <= 0) {
            despensa.getProdutosIds().remove(index);
            despensa.getProdutosQuantidades().remove(index);
        } else {
            despensa.getProdutosQuantidades().set(index, quantidade);
        }
        despensaRepository.save(despensa);
        return ResponseEntity.ok(despensa);
    }
}
