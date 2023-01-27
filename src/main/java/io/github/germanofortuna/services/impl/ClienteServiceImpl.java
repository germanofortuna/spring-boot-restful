package io.github.germanofortuna.services.impl;

import io.github.germanofortuna.domain.entity.Cliente;
import io.github.germanofortuna.domain.repository.Clientes;
import io.github.germanofortuna.domain.repository.specs.ClienteSpecs;
import io.github.germanofortuna.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private Clientes repository;

    @Override
    public List<Cliente> buscarPorNome(String nome) {
        Specification<Cliente> nomeIs = ClienteSpecs.nomeEqual(nome);
        return repository.findAll(nomeIs).stream().collect(Collectors.toList());
    }

    @Override
    public List<Cliente> buscarPorCpf(String cpf) {
        Specification<Cliente> cpfIs = ClienteSpecs.nomeEqual(cpf);
        return repository.findAll(cpfIs).stream().collect(Collectors.toList());
    }
}
