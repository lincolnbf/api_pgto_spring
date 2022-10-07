package com.apipagamento.apipagamento.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class VendaDTO {

    @JsonIgnore
    private String id;

    private String vendedorId;

    @JsonIgnore
    private LocalDateTime dataVenda;

    private List<String> items;

    @JsonIgnore
    private VendaStatusEnum vendaStatusEnum;

    public VendaDTO(String vendedorId, List<String> items) {
        this.id = UUID.randomUUID().toString();
        this.vendedorId = vendedorId;
        this.dataVenda = LocalDateTime.now();
        this.items = items;
        this.vendaStatusEnum = VendaStatusEnum.AGUARDANDO_PAGAMENTO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(String vendedorId) {
        this.vendedorId = vendedorId;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public VendaStatusEnum getVendaStatusEnum() {
        return vendaStatusEnum;
    }

    public void setVendaStatusEnum(VendaStatusEnum vendaStatusEnum) {
        this.vendaStatusEnum = vendaStatusEnum;
    }
}
