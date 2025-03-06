package com.estocaai.backend.Produto.repository;

import com.estocaai.backend.Produto.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {

    Optional<Produto> findByNome(String nome);

    // Método para buscar produtos cujo nome contenha a string informada (ignora maiúsc./minúsc.)
    Page<Produto> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}