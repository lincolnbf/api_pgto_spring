package com.apipagamento.apipagamento.exceptions;

import com.apipagamento.apipagamento.dtos.VendaStatusEnum;

public class AlteracaoInvalidaStatusException extends RuntimeException {

    public AlteracaoInvalidaStatusException(VendaStatusEnum antigo, VendaStatusEnum novo) {
        super("Alteração de status: " + antigo.name() + " para status: " + novo.name() + " é inválida");
    }
}
