package io.github.germanofortuna.domain.repository.specs;

import io.github.germanofortuna.domain.entity.Cliente;
import org.springframework.data.jpa.domain.Specification;

public abstract class ClienteSpecs {

    public static Specification<Cliente> nomeEqual(String nome) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal( root.get("nome"), nome );
    }

    public static Specification<Cliente> cpfEqual(String cpf) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal( root.get("cpf"), cpf );
    }
}
