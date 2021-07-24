package com.nextstudio.mvcrest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI springShoOpenAPI() {

        Contact contact = new Contact();
        contact.setName("Moses");
        contact.setEmail("katsande.moses@gmail.com");
        contact.setUrl("http://nexstudio.com");

        return new OpenAPI()
                .info(new Info().title("RestApi Demo")
                        .description("Demo of a RESTful webservice for Spring")
                        .version("1.0.0")
                        .termsOfService("https://termsofservice.com/url")
                        .contact(contact)
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("More about SpringDoc and OpenAPI 3")
                        .url("https://springdoc.org/"));
    }
}
