package com.estocaai.backend.Despensa.controller;

import org.springframework.web.bind.annotation.RequestBody;
import com.estocaai.backend.Despensa.service.DespensaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/casas/{casaId}/despensa")
public class DespensaController {

    private final DespensaService despensaService;

    public DespensaController(DespensaService despensaService) {
        this.despensaService = despensaService;
    }

    @GetMapping
    public ResponseEntity<?> getDespensa(@PathVariable String casaId,
                                         @RequestHeader(value = "Authorization") String token) {
        return despensaService.getDespensa(casaId, token);
    }

    @GetMapping("/produtos")
    public ResponseEntity<?> listarProdutos(@PathVariable String casaId,
                                            @RequestHeader(value = "Authorization") String token) {
        return despensaService.listarProdutos(casaId, token);
    }

    @PostMapping("/produtos/{produtoId}")
    public ResponseEntity<?> adicionarProduto(
            @PathVariable String casaId,
            @PathVariable String produtoId,
            @RequestBody Map<String, Integer> body, // Pegando do JSON enviado no frontend
            @RequestHeader(value = "Authorization") String token) {

        int quantidade = body.get("quantidade"); // Extraindo quantidade do JSON

        return despensaService.adicionarProduto(casaId, produtoId, quantidade, token);
    }

    @DeleteMapping("/produtos/{produtoId}")
    public ResponseEntity<?> removerProduto(@PathVariable String casaId,
                                            @PathVariable String produtoId,
                                            @RequestParam int quantidade,
                                            @RequestHeader(value = "Authorization") String token) {
        return despensaService.removerProduto(casaId, produtoId, quantidade, token);
    }

    @PutMapping("/produtos/{produtoId}")
    public ResponseEntity<?> atualizarQuantidade(
            @PathVariable String casaId,
            @PathVariable String produtoId,
            @RequestBody Map<String, Integer> requestBody, // Agora aceita JSON
            @RequestHeader(value = "Authorization") String token) {

        int quantidade = requestBody.getOrDefault("quantidade", -1); // Obtém a quantidade do JSON
        return despensaService.atualizarQuantidade(casaId, produtoId, quantidade, token);
    }

}
