package com.estocaai.backend.ListaDeCompras.repository;

import com.estocaai.backend.ListaDeCompras.model.ListaDeCompras;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListaDeComprasRepository extends MongoRepository<ListaDeCompras, String> {
    Optional<ListaDeCompras> findByCasaId(String casaId);
}
