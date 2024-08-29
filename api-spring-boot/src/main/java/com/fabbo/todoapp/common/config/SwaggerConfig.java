package com.fabbo.todoapp.common.config;

import com.fabbo.todoapp.TodoappApplication;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(
        {
                TodoappApplication.NON_TEST_PROFILE,
                TodoappApplication.NON_WEB_TEST_PROFILE,
                TodoappApplication.NON_CONTAINER_TEST_PROFILE,
        }
)
public class SwaggerConfig {
    private static final String SWAGGER_TITLE = "Todo App API";

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(
                        new SecurityRequirement().
                                addList("Bearer Authentication")
                )
                .components(
                        new Components()
                                .addSecuritySchemes
                                        ("Bearer Authentication", createAPIKeyScheme())
                )
                .info(
                        new Info()
                                .title(SWAGGER_TITLE)
                );
    }
}
