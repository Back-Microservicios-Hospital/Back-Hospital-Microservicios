package com.historial.service.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI custonOpenAPI() {
		return new OpenAPI().info(new Info().title("API del servicio de Historial Médico")
											.version("1.0.0")
											.description("Este proyecto es parte del backend con microservicios para el sistema de Hospital, "));
	}
}
