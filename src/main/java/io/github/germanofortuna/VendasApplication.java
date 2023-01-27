package io.github.germanofortuna;

import io.github.germanofortuna.domain.entity.Cliente;
import io.github.germanofortuna.domain.entity.Pedido;
import io.github.germanofortuna.domain.entity.Produto;
import io.github.germanofortuna.domain.repository.Clientes;
import io.github.germanofortuna.domain.repository.Pedidos;
import io.github.germanofortuna.domain.repository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication extends SpringBootServletInitializer  {
/*
    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes,
                                  @Autowired Pedidos pedidos,
                                  @Autowired Produtos produtos) {
        return args -> {
            /* criando clientes * /
            Cliente c1 = new Cliente(null, "Germano");
            clientes.save(c1);
            Cliente c2 = new Cliente(null, "Antonio");
            clientes.save(c2);


            Produto p1 = new Produto("Camisa Listrara", BigDecimal.valueOf(100));
            produtos.save(p1);
            Produto p2 = new Produto("Bermuda Marrom", BigDecimal.valueOf(85));
            produtos.save(p2);
        };
    }
*/
    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}