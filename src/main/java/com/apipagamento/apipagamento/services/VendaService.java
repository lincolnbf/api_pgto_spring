package com.apipagamento.apipagamento.services;

import com.apipagamento.apipagamento.dtos.AtualizaVendaDTO;
import com.apipagamento.apipagamento.dtos.VendaDTO;
import com.apipagamento.apipagamento.dtos.VendaStatusEnum;
import com.apipagamento.apipagamento.exceptions.AlteracaoInvalidaStatusException;
import com.apipagamento.apipagamento.exceptions.VendaNaoEncontradaException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    public List<VendaDTO> alterarVenda(List<VendaDTO> vendaDTOList, AtualizaVendaDTO dto) {
        Optional<VendaDTO> optionalVenda = vendaDTOList.stream().filter(v -> v.getId().equals(dto.getId())).findFirst();
        if (optionalVenda.isPresent()) {

            VendaDTO venda = optionalVenda.get();

            VendaStatusEnum statusAntigo = venda.getVendaStatusEnum();
            try {
                VendaStatusEnum statusNovo = VendaStatusEnum.valueOf(dto.getStatus());

                if (
                        statusAntigo.equals(VendaStatusEnum.AGUARDANDO_PAGAMENTO) && statusNovo.equals(VendaStatusEnum.PAGAMENTO_APROVADO) ||
                                statusAntigo.equals(VendaStatusEnum.AGUARDANDO_PAGAMENTO) && statusNovo.equals(VendaStatusEnum.CANCELADA) ||
                                statusAntigo.equals(VendaStatusEnum.PAGAMENTO_APROVADO) && statusNovo.equals(VendaStatusEnum.ENVIADO_TRANSPORTADORA) ||
                                statusAntigo.equals(VendaStatusEnum.PAGAMENTO_APROVADO) && statusNovo.equals(VendaStatusEnum.CANCELADA) ||
                                statusAntigo.equals(VendaStatusEnum.ENVIADO_TRANSPORTADORA) && statusNovo.equals(VendaStatusEnum.ENTREGUE)
                ) {
                    venda.setVendaStatusEnum(VendaStatusEnum.valueOf(dto.getStatus()));
                } else {
                    throw new AlteracaoInvalidaStatusException(statusAntigo, statusNovo);
                }
            } catch (IllegalArgumentException exception) {
                throw new IllegalArgumentException("Status não existe");
            }
        } else {
            throw new VendaNaoEncontradaException(dto.getId());
        }

        return vendaDTOList;
    }
}
