package com.apipagamento.apipagamento.db;

import com.apipagamento.apipagamento.dtos.VendedorDTO;

import java.util.List;

public class VendedorDB {
    private final List<VendedorDTO> vendedorDTOS;

    public List<VendedorDTO> getVendedorDTOS() {
        return vendedorDTOS;
    }

    public VendedorDB(List<VendedorDB> vendedorDBS) {
        this.vendedorDTOS = List.of(
                new VendedorDTO("1", "65538519481", "Antônio Carlos Silva", "antonio@carlos.com", "16977776633"),
                new VendedorDTO("2", "14915237273", "Mariana Araujo", "mariana@araujo.com", "169686989941"),
                new VendedorDTO("3", "00045216745", "Joice Oliveira", "joice@oliveira.com", "169454546886")
        );
    }
}
