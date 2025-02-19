package com.estocaai.backend.Casa.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "casas")
public class Casa {

    @Id
    private String id;
    private String nome;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private Integer numero;
    private String complemento;
    private String usuarioId;  // Referência ao usuário dono da casa

    // Agora armazenamos apenas os IDs
    private String despensaId;
    private String listaDeComprasId;

    private LocalDateTime creationDate;

    public Casa(String nome, String estado, String cidade, String bairro, String rua, Integer numero, String complemento, String usuarioId) {
        this.nome = nome;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.usuarioId = usuarioId;
        this.creationDate = LocalDateTime.now();
    }

    // Getters e Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getDespensaId() {
        return despensaId;
    }

    public void setDespensaId(String despensaId) {
        this.despensaId = despensaId;
    }

    public String getListaDeComprasId() {
        return listaDeComprasId;
    }

    public void setListaDeComprasId(String listaDeComprasId) {
        this.listaDeComprasId = listaDeComprasId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
