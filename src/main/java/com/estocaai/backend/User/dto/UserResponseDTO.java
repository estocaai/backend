//Esta classe só existe para não expor a senha do usuário em respostas da API.
//Ela é usada para retornar apenas os dados necessários do usuário.

package com.estocaai.backend.User.dto;

public class UserResponseDTO {
    private String name;
    private String email;
    private String fotoPerfil;
    private String casaEscolhida;

    public UserResponseDTO(String name, String email, String fotoPerfil, String casaEscolhida) {
        this.name = name;
        this.email = email;
        this.fotoPerfil = fotoPerfil;
        this.casaEscolhida = casaEscolhida;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCasaEscolhida() {
        return casaEscolhida;
    }

    public void setCasaEscolhida(String casaEscolhida) {
        this.casaEscolhida = casaEscolhida;
    }
}
