package io.github.germanofortuna.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration

public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("Pejetos Vendas")
                        .version("1.0")
                        .description("Vendas endpoints")
                        .termsOfService("https://springdoc.org/")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://springdoc.org/")
                        )
                        .contact(new Contact()
                                .name("Germano Antonio Fortuna")
                                .url("https://github.com/germanofortuna")
                                .email("germano.fortuna@outlook.com"))
                ).addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                );
    }

}

