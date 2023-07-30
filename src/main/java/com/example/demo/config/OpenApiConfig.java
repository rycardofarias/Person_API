package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("RESTfull API PERSON")
						.version("v1")
						.description("Spring Boot API")
						.termsOfService("https://demo.example.com")
						.license(new License()
								.name("Apache 2.0")
								.url("https://demo.example.com")
								)
						);
	}
}
