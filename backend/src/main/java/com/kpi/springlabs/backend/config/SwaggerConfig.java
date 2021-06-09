package com.kpi.springlabs.backend.config;

import com.kpi.springlabs.backend.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;

import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kpi.springlabs.backend.controllers"))
                .paths(PathSelectors.regex("/api.*"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Warehouse Inventory System REST API")
                .description("This API allows performing all operations related to the inventory system of goods")
                .version("1.0")
                .build();
    }

    private SecurityScheme apiKey() {
        return new ApiKey(Constants.AccessTokenType.BEARER, HttpHeaders.AUTHORIZATION, ApiKeyVehicle.HEADER.getValue());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference(Constants.AccessTokenType.BEARER, authorizationScopes));
    }
}
