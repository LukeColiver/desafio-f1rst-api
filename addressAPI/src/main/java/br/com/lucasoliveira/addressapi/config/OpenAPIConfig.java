package br.com.lucasoliveira.addressapi.config;

import java.util.List;

import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AddressAPI Documentation")
                        .description("API para consultar informações de CEPs.")
                        .version("v1")
                        .contact(new Contact()
                                .name("Lucas Oliveira")
                                .email("lucas.oliveira@example.com")
                                .url("https://github.com/LukeColiver"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .externalDocs(new ExternalDocumentation()
                        .description("Projeto no GitHub")
                        .url("https://github.com/LukeColiver/desafio-f1rst-api"));
    }
}
