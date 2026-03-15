package kbtu.kz.sis5.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student Management API")
                        .version("v1")
                        .description("API for managing students at KBTU")
                        .contact(new Contact()
                                .name("KBTU Backend Team")
                                .email("backend@kbtu.kz")));
    }
}