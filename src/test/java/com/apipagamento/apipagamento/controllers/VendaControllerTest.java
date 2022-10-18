package com.apipagamento.apipagamento.controllers;

import com.apipagamento.apipagamento.dtos.AtualizaVendaDTO;
import com.apipagamento.apipagamento.dtos.VendaDTO;
import com.apipagamento.apipagamento.dtos.VendaStatusEnum;
import com.apipagamento.apipagamento.exceptions.AlteracaoInvalidaStatusException;
import com.apipagamento.apipagamento.exceptions.VendaNaoEncontradaException;
import com.apipagamento.apipagamento.services.VendaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class VendaControllerTest {

    @Autowired
    VendaService vendaService;

    @Test
    public void alteraVendaTest() {
        List<VendaDTO> vendaDTOList = new ArrayList<>();
        vendaDTOList.add(new VendaDTO("1", Collections.singletonList("Celular")));
        vendaDTOList.add(new VendaDTO("1", Collections.singletonList("Televisão")));

        vendaDTOList = vendaService.alterarVenda(vendaDTOList, new AtualizaVendaDTO(VendaStatusEnum.PAGAMENTO_APROVADO.name(), vendaDTOList.get(0).getId()));

        assert vendaDTOList.size() == 2;
        assert vendaDTOList.get(0).getVendaStatusEnum() == VendaStatusEnum.PAGAMENTO_APROVADO;
        assert vendaDTOList.get(1).getVendaStatusEnum() == VendaStatusEnum.AGUARDANDO_PAGAMENTO;

        vendaDTOList = vendaService.alterarVenda(vendaDTOList, new AtualizaVendaDTO(VendaStatusEnum.PAGAMENTO_APROVADO.name(), vendaDTOList.get(1).getId()));
        assert vendaDTOList.size() == 2;
        assert vendaDTOList.get(0).getVendaStatusEnum() == VendaStatusEnum.PAGAMENTO_APROVADO;
        assert vendaDTOList.get(1).getVendaStatusEnum() == VendaStatusEnum.PAGAMENTO_APROVADO;

    }

    @Test
    public void alteraVendaTestIdNaoExiste() {
        List<VendaDTO> vendaDTOList = new ArrayList<>();
        vendaDTOList.add(new VendaDTO("1", Collections.singletonList("Celular")));
        vendaDTOList.add(new VendaDTO("1", Collections.singletonList("Televisão")));

        final String idNaoExiste = UUID.randomUUID().toString();
        assertThrows(
                VendaNaoEncontradaException.class,
                () -> vendaService.alterarVenda(vendaDTOList, new AtualizaVendaDTO(VendaStatusEnum.PAGAMENTO_APROVADO.name(), idNaoExiste))
        );
    }

    @Test
    public void alteraStatusInvalido() {
        List<VendaDTO> vendaDTOList = new ArrayList<>();
        vendaDTOList.add(new VendaDTO("1", Collections.singletonList("Celular")));
        vendaDTOList.add(new VendaDTO("1", Collections.singletonList("Televisão")));

        List<VendaDTO> finalVendaDTOList = vendaDTOList;
        assertThrows(
                AlteracaoInvalidaStatusException.class,
                () -> vendaService.alterarVenda(finalVendaDTOList, new AtualizaVendaDTO(VendaStatusEnum.ENTREGUE.name(), finalVendaDTOList.get(0).getId()))
        );

        assertThrows(
                AlteracaoInvalidaStatusException.class,
                () -> vendaService.alterarVenda(finalVendaDTOList, new AtualizaVendaDTO(VendaStatusEnum.ENVIADO_TRANSPORTADORA.name(), finalVendaDTOList.get(0).getId()))
        );

        vendaDTOList = vendaService.alterarVenda(vendaDTOList, new AtualizaVendaDTO(VendaStatusEnum.PAGAMENTO_APROVADO.name(), finalVendaDTOList.get(0).getId()));
        assert vendaDTOList.get(0).getVendaStatusEnum() == VendaStatusEnum.PAGAMENTO_APROVADO;

        vendaDTOList = vendaService.alterarVenda(vendaDTOList, new AtualizaVendaDTO(VendaStatusEnum.ENVIADO_TRANSPORTADORA.name(), finalVendaDTOList.get(0).getId()));
        assert vendaDTOList.get(0).getVendaStatusEnum() == VendaStatusEnum.ENVIADO_TRANSPORTADORA;

        List<VendaDTO> vendaCopyList = vendaDTOList;
        assertThrows(AlteracaoInvalidaStatusException.class,
                () -> vendaService.alterarVenda(vendaCopyList, new AtualizaVendaDTO(VendaStatusEnum.PAGAMENTO_APROVADO.name(), vendaCopyList.get(0).getId()))
        );

        assertThrows(AlteracaoInvalidaStatusException.class,
                () -> vendaService.alterarVenda(vendaCopyList, new AtualizaVendaDTO(VendaStatusEnum.AGUARDANDO_PAGAMENTO.name(), vendaCopyList.get(0).getId()))
        );
    }

    @Test
    public void adicionaVenda() {

        List<VendaDTO> vendaDTOList = new ArrayList<>();

        vendaDTOList = vendaService.adicionarVenda(vendaDTOList, new VendaDTO("1", List.of("Celular", "Televisão")));
        assert vendaDTOList.size() == 1;
        assert Objects.equals(vendaDTOList.get(0).getVendedorId(), "1");
        assert vendaDTOList.get(0).getItems().containsAll(List.of("Celular", "Televisão"));
        assertFalse(vendaDTOList.get(0).getItems().contains("VideoGame"));

        vendaDTOList = vendaService.adicionarVenda(vendaDTOList, new VendaDTO("2", List.of("VideoGame")));
        assert vendaDTOList.size() == 2;
        assertTrue(vendaDTOList.get(1).getItems().contains("VideoGame"));

    }
}
