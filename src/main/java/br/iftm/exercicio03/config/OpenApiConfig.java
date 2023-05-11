package br.iftm.exercicio03.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Documentação da API da Terceira atividade de Spring Boot")
                        .version("Version 1 (v1)")
                        .description("Esta documentação apresenta os endpoints " +
                                "responsáveis pelas funcionalidades de Usuários, " +
                                "Emails e Grupos de Usuários.")
                        .termsOfService("http://mytersm.com")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://mylicences.com"))
                );
    }
}