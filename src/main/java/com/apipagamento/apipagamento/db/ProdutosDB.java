package com.apipagamento.apipagamento.db;

import com.apipagamento.apipagamento.dtos.ProdutoDTO;

import java.util.List;

public class ProdutosDB {

    private final List<ProdutoDTO> produtoDTOS;

    public List<ProdutoDTO> getProdutoDTOS() {
        return produtoDTOS;
    }

    public ProdutosDB(List<ProdutoDTO> produtoDTOS) {
        this.produtoDTOS = List.of(
                new ProdutoDTO("1", "Computador", 1200.49),
                new ProdutoDTO("2", "Celular", 800.20),
                new ProdutoDTO("3", "Video Game XBOX", 1500.79),
                new ProdutoDTO("4", "Teclado Mecânico", 299.99));
    }
}
