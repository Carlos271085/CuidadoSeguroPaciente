package com.pacientes.pacientes.config;

// Importaciones OpenAPI
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

// Importaciones Spring
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Marca esta clase como configuración
@Configuration
public class OpenApiConfig {

    // Configura información personalizada de Swagger/OpenAPI
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()

                // Información general de la API
                .info(new Info()

                        // Nombre de la API
                        .title("Microservicio de Pacientes - Cuidado Seguro")

                        // Versión de la API
                        .version("1.0")

                        // Descripción
                        .description("API encargada de la gestión de pacientes del sistema Cuidado Seguro"));
    }
}