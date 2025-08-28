package com.ee.md.moviedb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI movieDbOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MovieDB API")
                        .description("Spring Boot REST API for Movie Database")
                        .version("1.0"));
    }
}
