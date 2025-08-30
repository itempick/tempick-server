package com.tempick.tempickserver.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Tempick API Docs")
                    .description("Tempick API Docs")
                    .license(
                        License()
                            .name("Apache 2.0")
                            .url("https://www.apache.org/licenses/LICENSE-2.0"),
                    ),
            )
            .servers(
                listOf(
                    Server()
                        .url("https://api.tempick.com/")
                        .description("Production Server"),
                    Server()
                        .url("http://localhost:8080")
                        .description("Local Server"),
                ),
            )
            .components(
                Components().addSecuritySchemes(
                    "Authorization",
                    SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .`in`(SecurityScheme.In.HEADER)
                        .name("Authorization"),
                ),
            )
            .addSecurityItem(SecurityRequirement().addList("Authorization"))
    }
}
