package com.davidrt301.medicare.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI / Swagger para la documentación de la API REST.
 * Define la información general de la API, esquemas de seguridad JWT y detalles de contacto.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configura la documentación OpenAPI personalizada incluyendo seguridad JWT,
     * información de la API y esquema de componentes.
     *
     * @return OpenAPI configurado con autenticación Bearer JWT
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer JWT"))
                .components(new Components().addSecuritySchemes("Bearer JWT", createSecurityScheme()))
                .info(new Info()
                        .title("Hospital Management System API")
                        .version("1.0.0")
                        .description("Sistema de gestión hospitalaria integral. Proporciona endpoints para la administración de pacientes, " +
                                "empleados, especialidades médicas, citas médicas (atenciones) y autenticación basada en JWT. " +
                                "Incluye funcionalidades para la gestión completa del ciclo de vida de pacientes y personal médico.")
                        .contact(new Contact()
                                .name("David RT")
                                .email("davidrt3214434@gmal.com")
                                .url("https://github.com/davidrt301"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }

    /**
     * Define el esquema de seguridad JWT (Bearer Token).
     * Especifica que se utiliza autenticación HTTP con formato Bearer y esquema JWT.
     *
     * @return SecurityScheme configurado para JWT
     */
    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer")
                .description("Ingrese un token JWT válido para acceder a los endpoints protegidos");
    }
}
