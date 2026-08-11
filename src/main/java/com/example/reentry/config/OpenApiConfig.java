package com.example.reentry.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI reEntryOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("ReEntry API")
                        .description("Family reminder and calendar API — create, view, and update calendar events, and see which events apply to which family members.")
                        .version("v1"));
    }
}
