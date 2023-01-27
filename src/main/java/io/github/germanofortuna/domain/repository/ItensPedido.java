package io.github.germanofortuna.domain.repository;

import io.github.germanofortuna.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensPedido extends JpaRepository<ItemPedido, Integer> {
}
