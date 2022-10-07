package com.apipagamento.apipagamento.controllers;

import com.apipagamento.apipagamento.dtos.AtualizaVendaDTO;
import com.apipagamento.apipagamento.dtos.VendaDTO;
import com.apipagamento.apipagamento.exceptions.AlteracaoInvalidaStatusException;
import com.apipagamento.apipagamento.exceptions.VendaNaoEncontradaException;
import com.apipagamento.apipagamento.services.VendaService;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;

@RestController
public class VendaController {

    static Logger log = getLogger(VendaController.class.getName());
    @Autowired
    VendaService vendaService;

    // Trabalhando com conceito global...
    List<VendaDTO> vendaDTOList = new ArrayList<>();

    @GetMapping(value = "/venda")
    List<VendaDTO> buscaVenda() {
        return vendaDTOList;
    }

    @PostMapping(value = "/venda")
    VendaDTO registrarVenda(@RequestBody VendaDTO vendaDTO) {
        vendaDTOList.add(vendaDTO);
        return vendaDTO;
    }

    @PutMapping(value = "/venda")
    String atualizarVenda(@RequestBody AtualizaVendaDTO dto, HttpServletResponse response) throws IOException {
        try {
            vendaService.alterarVenda(vendaDTOList, dto);
        } catch (IllegalArgumentException | AlteracaoInvalidaStatusException exception) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
            log.error(exception.getMessage());
            return response.toString();
        } catch (VendaNaoEncontradaException exception) {
            response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
            log.error(exception.getMessage());
            return response.toString();
        }
        log.info("Produto {} atualizado com sucesso", dto.getId());
        return null;
    }
}
