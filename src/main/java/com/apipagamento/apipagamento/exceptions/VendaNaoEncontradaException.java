package com.apipagamento.apipagamento.exceptions;

public class VendaNaoEncontradaException extends RuntimeException {

    public VendaNaoEncontradaException(String id) {
        super("O id: " + id + " não foi encontrado no banco de dados...");
    }
}
