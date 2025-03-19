package com.estocaai.backend.Casa.service;

import com.estocaai.backend.Casa.model.Casa;
import com.estocaai.backend.Casa.repository.CasaRepository;
import com.estocaai.backend.Despensa.model.Despensa;
import com.estocaai.backend.Despensa.repository.DespensaRepository;
import com.estocaai.backend.ListaDeCompras.model.ListaDeCompras;
import com.estocaai.backend.ListaDeCompras.repository.ListaDeComprasRepository;
import com.estocaai.backend.User.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CasaService {

    private final CasaRepository casaRepository;
    private final DespensaRepository despensaRepository;
    private final ListaDeComprasRepository listaDeComprasRepository;
    private final UserService userService;

    public CasaService(CasaRepository casaRepository, DespensaRepository despensaRepository, ListaDeComprasRepository listaDeComprasRepository, UserService userService) {
        this.casaRepository = casaRepository;
        this.despensaRepository = despensaRepository;
        this.listaDeComprasRepository = listaDeComprasRepository;
        this.userService = userService;
    }

    private boolean isTokenInvalido(String token) {
        return token == null || !userService.isTokenValid(token);
    }

    public ResponseEntity<?> listarCasas(String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        String usuarioId = userService.getUsuarioIdFromToken(token);
        List<Casa> casas = casaRepository.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(casas);
    }

    public ResponseEntity<?> buscarCasaPorId(String casaId, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        Optional<Casa> casa = casaRepository.findById(casaId);
        if (casa.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Casa não encontrada!");
        }
        return ResponseEntity.ok(casa.get());
    }

    public ResponseEntity<?> criarCasa(Casa casa, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }

        String usuarioId = userService.getUsuarioIdFromToken(token);
        casa.setUsuarioId(usuarioId);

        Casa novaCasa = casaRepository.save(casa);

        if (novaCasa.getId() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar ID da casa.");
        }

        Despensa despensa = new Despensa(novaCasa.getId());
        despensa = despensaRepository.save(despensa);

        ListaDeCompras listaDeCompras = new ListaDeCompras(novaCasa.getId());
        listaDeCompras = listaDeComprasRepository.save(listaDeCompras);

        novaCasa.setDespensaId(despensa.getId());
        novaCasa.setListaDeComprasId(listaDeCompras.getId());

        casaRepository.save(novaCasa);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaCasa);
    }

    public ResponseEntity<?> atualizarCasa(String casaId, Casa casaAtualizada, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        Optional<Casa> casaOptional = casaRepository.findById(casaId);
        if (casaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Casa não encontrada!");
        }
        Casa casa = casaOptional.get();
        casa.setNome(casaAtualizada.getNome());
        casa.setEstado(casaAtualizada.getEstado());
        casa.setCidade(casaAtualizada.getCidade());
        casa.setBairro(casaAtualizada.getBairro());
        casa.setRua(casaAtualizada.getRua());
        casa.setNumero(casaAtualizada.getNumero());
        casa.setComplemento(casaAtualizada.getComplemento());
        casaRepository.save(casa);
        return ResponseEntity.ok(casa);
    }

    public ResponseEntity<?> deletarCasa(String casaId, String token) {
        if (isTokenInvalido(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou ausente!");
        }
        
        // Exclui a despensa associada à casa
        Optional<Despensa> despensaOpt = despensaRepository.findByCasaId(casaId);
        despensaOpt.ifPresent(despensaRepository::delete);

        // Exclui a lista de compras associada à casa
        Optional<ListaDeCompras> listaOpt = listaDeComprasRepository.findByCasaId(casaId);
        listaOpt.ifPresent(listaDeComprasRepository::delete);

        // Exclui a casa
        casaRepository.deleteById(casaId);
        return ResponseEntity.noContent().build();
    }
}
