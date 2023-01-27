package io.github.germanofortuna.rest.controller;

import io.github.germanofortuna.domain.entity.ItemPedido;
import io.github.germanofortuna.domain.entity.Pedido;
import io.github.germanofortuna.domain.enums.StatusPedido;
import io.github.germanofortuna.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.germanofortuna.rest.dto.InformacaoItemPedidoDTO;
import io.github.germanofortuna.rest.dto.InformacoesPedidoDTO;
import io.github.germanofortuna.rest.dto.PedidoDTO;
import io.github.germanofortuna.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO dto) {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id) {
        return service.obterPedidoCompleto(id)
                .map(p -> converter(p) )
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrato"));
    }

    private InformacoesPedidoDTO converter (Pedido pedido) {
        //.builder gerado pela annotation @Builder no objeto InformacoesPedidoDTO
        return InformacoesPedidoDTO.builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyy")))
                .cpf(pedido.getCliente().getCpf())
                .total(pedido.getTotal())
                .nomeCliente(pedido.getCliente().getNome())
                .status(pedido.getStatus().name())
                .items(converter(pedido.getItens())).build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
        if(CollectionUtils.isEmpty(itens)) return Collections.EMPTY_LIST;

        return itens.stream().map( item -> InformacaoItemPedidoDTO.builder()
                .descricaoProduto(item.getProduto().getDescricao())
                .precoUnitario(item.getProduto().getPreco())
                .quantitade(item.getQuantitade())
                .build()).collect(Collectors.toList());
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto) {
        service.atualizaStatus(id, StatusPedido.valueOf(dto.getNovoStatus()));
    }
}
