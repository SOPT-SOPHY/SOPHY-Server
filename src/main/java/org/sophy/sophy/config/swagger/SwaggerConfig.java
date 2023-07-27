package org.sophy.sophy.config.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "JWT Auth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Sophy")
                .description("Sophy Server API 문서")
                .version("1.0.0");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
