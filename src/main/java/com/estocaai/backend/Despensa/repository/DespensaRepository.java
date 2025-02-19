package com.estocaai.backend.Despensa.repository;

import com.estocaai.backend.Despensa.model.Despensa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DespensaRepository extends MongoRepository<Despensa, String> {
    Optional<Despensa> findByCasaId(String casaId);
}
