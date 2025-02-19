package com.estocaai.backend.Casa.repository;

import com.estocaai.backend.Casa.model.Casa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasaRepository extends MongoRepository<Casa, String> {
    List<Casa> findByUsuarioId(String usuarioId);
}
