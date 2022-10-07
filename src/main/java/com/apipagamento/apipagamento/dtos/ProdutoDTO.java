package com.apipagamento.apipagamento.dtos;

public class ProdutoDTO {

    private String id;
    private String name;
    private Double valor;

    public ProdutoDTO(String id, String name, Double valor) {
        this.id = id;
        this.name = name;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
