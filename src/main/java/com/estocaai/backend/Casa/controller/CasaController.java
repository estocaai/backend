package com.estocaai.backend.Casa.controller;

import com.estocaai.backend.Casa.model.Casa;
import com.estocaai.backend.Casa.service.CasaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/casas")
public class CasaController {

    private final CasaService casaService;

    public CasaController(CasaService casaService) {
        this.casaService = casaService;
    }

    @GetMapping
    public ResponseEntity<?> listarCasas(@RequestHeader(value = "Authorization") String token) {
        return casaService.listarCasas(token);
    }

    @GetMapping("/{casaId}")
    public ResponseEntity<?> buscarCasaPorId(@PathVariable String casaId,
                                             @RequestHeader(value = "Authorization") String token) {
        return casaService.buscarCasaPorId(casaId, token);
    }

    @PostMapping
    public ResponseEntity<?> criarCasa(@RequestHeader(value = "Authorization") String token,
                                       @RequestBody Casa casa) {
        return casaService.criarCasa(casa, token);
    }

    @PutMapping("/{casaId}")
    public ResponseEntity<?> atualizarCasa(@PathVariable String casaId,
                                           @RequestHeader(value = "Authorization") String token,
                                           @RequestBody Casa casaAtualizada) {
        return casaService.atualizarCasa(casaId, casaAtualizada, token);
    }

    @DeleteMapping("/{casaId}")
    public ResponseEntity<?> deletarCasa(@PathVariable String casaId,
                                         @RequestHeader(value = "Authorization") String token) {
        return casaService.deletarCasa(casaId, token);
    }
}
