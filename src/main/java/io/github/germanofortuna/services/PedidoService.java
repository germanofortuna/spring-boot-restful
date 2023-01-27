package io.github.germanofortuna.services;

import io.github.germanofortuna.domain.entity.Pedido;
import io.github.germanofortuna.domain.enums.StatusPedido;
import io.github.germanofortuna.rest.dto.PedidoDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    @Transactional
    void atualizaStatus(Integer id, StatusPedido statusPedido);

}
