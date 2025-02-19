package com.estocaai.backend.User.model;

import com.estocaai.backend.Casa.model.Casa;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;
import java.util.UUID;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String name;
    private String email;
    private String password;
    private String token;
    private String fotoPerfil;

    @DBRef // DBRef é uma anotação do Spring Data MongoDB que indica que a lista de casas é uma referência a outra coleção
    private List<String> casasIds;  // Lista dos IDs de casas do usuário

    private String casaEscolhida; // Última casa selecionada pelo usuário

    public User() {
        this.id = UUID.randomUUID().toString();
    }

    // Getters e Setters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public List<String> getCasasIds() {
        return casasIds;
    }

    public void setCasasIds(List<String> casasIds) {
        this.casasIds = casasIds;
    }

    public String getCasaEscolhida() {
        return casaEscolhida;
    }

    public void setCasaEscolhida(String casaEscolhida) {
        this.casaEscolhida = casaEscolhida;
    }
}