package io.github.germanofortuna.services.impl;

import io.github.germanofortuna.domain.entity.Cliente;
import io.github.germanofortuna.domain.entity.ItemPedido;
import io.github.germanofortuna.domain.entity.Pedido;
import io.github.germanofortuna.domain.entity.Produto;
import io.github.germanofortuna.domain.enums.StatusPedido;
import io.github.germanofortuna.domain.repository.Clientes;
import io.github.germanofortuna.domain.repository.ItensPedido;
import io.github.germanofortuna.domain.repository.Pedidos;
import io.github.germanofortuna.domain.repository.Produtos;
import io.github.germanofortuna.exception.PedidoNaoEncontradoException;
import io.github.germanofortuna.exception.RegraNegocioException;
import io.github.germanofortuna.rest.dto.ItemPedidoDTO;
import io.github.germanofortuna.rest.dto.PedidoDTO;
import io.github.germanofortuna.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //gera construtor dos os attributos que são obrigatórios (final), injetando eles ao inves de usar o @autowired
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItensPedido itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository.findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido!"));

        Pedido pedido = new Pedido();
        pedido.setDataPedido(LocalDate.now());
        pedido.setTotal(dto.getTotal());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedidos = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemPedidos);
        pedido.setItens(itemPedidos);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return repository.findByIdFetchItems(id);
    }

    @Override
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow( () -> new PedidoNaoEncontradoException());
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
        if(items == null || items.isEmpty()) {
            throw new RegraNegocioException(("Não é possível realizar pedido sem itens!"));
        }

        return items
                .stream()
                .map(dto -> {
                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository
                            .findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Código de produto inválido!"
                            ));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantitade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
