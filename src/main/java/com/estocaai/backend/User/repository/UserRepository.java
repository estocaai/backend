package com.estocaai.backend.User.repository;

import com.estocaai.backend.User.model.User;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
}

