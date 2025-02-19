package com.estocaai.backend.ListaDeCompras.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "listas_de_compras")
public class ListaDeCompras {

    @Id
    private String id;
    private String casaId; // Cada lista pertence a uma casa

    private List<String> produtosIds = new ArrayList<>();
    private List<Integer> produtosQuantidades = new ArrayList<>();

    public ListaDeCompras(String casaId) {
        this.casaId = casaId;
    }

    public String getId() {
        return id;
    }

    public String getCasaId() {
        return casaId;
    }

    public void setCasaId(String casaId) {
        this.casaId = casaId;
    }

    public List<String> getProdutosIds() {
        return produtosIds;
    }

    public List<Integer> getProdutosQuantidades() {
        return produtosQuantidades;
    }

    public void setProdutosIds(List<String> produtosIds) {
        this.produtosIds = produtosIds;
    }

    public void setProdutosQuantidades(List<Integer> produtosQuantidades) {
        this.produtosQuantidades = produtosQuantidades;
    }

    // Método para adicionar um produto à lista de compras
    public void adicionarProduto(String produtoId, int quantidade) {
        int index = produtosIds.indexOf(produtoId);
        if (index != -1) {
            produtosQuantidades.set(index, produtosQuantidades.get(index) + quantidade);
        } else {
            produtosIds.add(produtoId);
            produtosQuantidades.add(quantidade);
        }
    }

    // Método para remover um produto da lista de compras
    public void removerProduto(String produtoId, int quantidade) {
        int index = produtosIds.indexOf(produtoId);
        if (index != -1) {
            int novaQuantidade = produtosQuantidades.get(index) - quantidade;
            if (novaQuantidade <= 0) {
                produtosIds.remove(index);
                produtosQuantidades.remove(index);
            } else {
                produtosQuantidades.set(index, novaQuantidade);
            }
        }
    }
}
