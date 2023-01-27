package io.github.germanofortuna.services;

import io.github.germanofortuna.domain.entity.Cliente;

import java.util.List;

public interface ClienteService {

    List<Cliente> buscarPorNome(String nome);

    List<Cliente> buscarPorCpf(String cpf);

}
