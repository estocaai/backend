package com.estocaai.backend.ListaDeCompras.controller;

import com.estocaai.backend.ListaDeCompras.service.ListaDeComprasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/casas/{casaId}/lista-de-compras")
public class ListaDeComprasController {

    private final ListaDeComprasService listaDeComprasService;

    public ListaDeComprasController(ListaDeComprasService listaDeComprasService) {
        this.listaDeComprasService = listaDeComprasService;
    }

    @GetMapping
    public ResponseEntity<?> getListaDeCompras(@PathVariable String casaId,
                                               @RequestHeader(value = "Authorization") String token) {
        return listaDeComprasService.getListaDeCompras(casaId, token);
    }

    @GetMapping("/produtos")
    public ResponseEntity<?> listarProdutos(@PathVariable String casaId,
                                            @RequestHeader(value = "Authorization") String token) {
        return listaDeComprasService.listarProdutos(casaId, token);
    }

    @PostMapping("/produtos/{produtoId}")
    public ResponseEntity<?> adicionarProduto(@PathVariable String casaId,
                                              @PathVariable String produtoId,
                                              @RequestParam int quantidade,
                                              @RequestHeader(value = "Authorization") String token) {
        return listaDeComprasService.adicionarProduto(casaId, produtoId, quantidade, token);
    }

    @DeleteMapping("/produtos/{produtoId}")
    public ResponseEntity<?> removerProduto(@PathVariable String casaId,
                                            @PathVariable String produtoId,
                                            @RequestParam int quantidade,
                                            @RequestHeader(value = "Authorization") String token) {
        return listaDeComprasService.removerProduto(casaId, produtoId, quantidade, token);
    }

    @PutMapping("/produtos/{produtoId}")
    public ResponseEntity<?> atualizarQuantidade(@PathVariable String casaId,
                                                 @PathVariable String produtoId,
                                                 @RequestParam int quantidade,
                                                 @RequestHeader(value = "Authorization") String token) {
        return listaDeComprasService.atualizarQuantidade(casaId, produtoId, quantidade, token);
    }
}
