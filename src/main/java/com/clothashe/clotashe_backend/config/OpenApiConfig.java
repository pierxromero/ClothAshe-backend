package com.clothashe.clotashe_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ClothAshe API")
                        .version("v1")
                        .description("REST API for an online clothing store. This documentation currently covers the available DTO models and will be expanded as the implementation progresses.")
                        .contact(new Contact()
                                .name("ClothAshe Development Team")
                                .email("pierofelixvisitacionromero@gmail.com")
                                .url("https://clothashe.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT"))
                );
    }
}
