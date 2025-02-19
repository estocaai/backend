package com.estocaai.backend.ListaDeCompras.service;

import com.estocaai.backend.ListaDeCompras.model.ListaDeCompras;
import com.estocaai.backend.ListaDeCompras.repository.ListaDeComprasRepository;
import com.estocaai.backend.Produto.repository.ProdutoRepository;
import com.estocaai.backend.User.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ListaDeComprasService {

    private final ListaDeComprasRepository listaDeComprasRepository;
    private final ProdutoRepository produtoRepository;
    private final UserService userService;

    public ListaDeComprasService(ListaDeComprasRepository listaDeComprasRepository, ProdutoRepository produtoRepository, UserService userService) {
        this.listaDeComprasRepository = listaDeComprasRepository;
        this.produtoRepository = produtoRepository;
        this.userService = userService;
    }

    private boolean isTokenInvalido(String token) {
        return token == null || !userService.isTokenValid(token);
    }

    public ResponseEntity<?> getListaDeCompras(String casaId, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        ListaDeCompras lista = listaDeComprasRepository.findByCasaId(casaId)
                .orElseThrow(() -> new RuntimeException("Lista de compras não encontrada!"));
        return ResponseEntity.ok(lista);
    }

    public ResponseEntity<?> adicionarProduto(String casaId, String produtoId, int quantidade, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        ListaDeCompras lista = listaDeComprasRepository.findByCasaId(casaId)
                .orElseThrow(() -> new RuntimeException("Lista de compras não encontrada!"));
        if (!produtoRepository.existsById(produtoId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado!");
        }
        lista.adicionarProduto(produtoId, quantidade);
        listaDeComprasRepository.save(lista);
        return ResponseEntity.ok(lista);
    }

    public ResponseEntity<?> removerProduto(String casaId, String produtoId, int quantidade, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        ListaDeCompras lista = listaDeComprasRepository.findByCasaId(casaId)
                .orElseThrow(() -> new RuntimeException("Lista de compras não encontrada!"));

        int index = lista.getProdutosIds().indexOf(produtoId);
        if (index == -1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado na lista!");
        }
        int currQuantity = lista.getProdutosQuantidades().get(index);
        if (quantidade >= currQuantity) {
            // Remove completely if removal quantity is equal or higher than current
            lista.getProdutosIds().remove(index);
            lista.getProdutosQuantidades().remove(index);
        } else {
            // Otherwise, subtract the quantity
            lista.getProdutosQuantidades().set(index, currQuantity - quantidade);
        }
        listaDeComprasRepository.save(lista);
        return ResponseEntity.ok(lista);
    }

    public ResponseEntity<?> listarProdutos(String casaId, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        ListaDeCompras lista = listaDeComprasRepository.findByCasaId(casaId)
                .orElseThrow(() -> new RuntimeException("Lista de compras não encontrada!"));
        return ResponseEntity.ok(produtoRepository.findAllById(lista.getProdutosIds()));
    }

    public ResponseEntity<?> atualizarQuantidade(String casaId, String produtoId, int quantidade, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        ListaDeCompras lista = listaDeComprasRepository.findByCasaId(casaId)
                .orElseThrow(() -> new RuntimeException("Lista de compras não encontrada!"));
        int index = lista.getProdutosIds().indexOf(produtoId);
        if (index == -1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado na lista de compras!");
        }
        if (quantidade <= 0) {
            lista.getProdutosIds().remove(index);
            lista.getProdutosQuantidades().remove(index);
        } else {
            lista.getProdutosQuantidades().set(index, quantidade);
        }
        listaDeComprasRepository.save(lista);
        return ResponseEntity.ok(lista);
    }
}
