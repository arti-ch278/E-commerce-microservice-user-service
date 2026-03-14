package com.artichourey.ecommerce.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI userServiceAPI() {

        SecurityScheme securityScheme = new SecurityScheme()
                .name("bearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .info(new Info()
                        .title("User Service API")
                        .description("User management APIs for E-commerce Platform")
                        .version("1.0"))

                .components(new Components()
                        .addSecuritySchemes("bearerAuth", securityScheme))

                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth"));
    }
}
