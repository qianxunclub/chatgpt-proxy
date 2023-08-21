package com.qianxunclub.chatgptproxy.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import lombok.AllArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;


@Configuration
@AllArgsConstructor
public class SpringDocConfig {

    private final AppProperties appProperties;

    @Bean
    public OpenAPI apiInfo() {
        List<Server> servers = new ArrayList<>();
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        servers.add(server);
        List<String> swaggerBaseUrl = appProperties.getSwaggerBaseUrl();
        swaggerBaseUrl.forEach(url -> {
            Server s = new Server();
            s.setUrl(url);
            servers.add(s);
        });

        OpenAPI openAPI = new OpenAPI().info(new Info().title("后端接口文档")).servers(servers);
        openAPI.addSecurityItem(new SecurityRequirement().addList(HttpHeaders.AUTHORIZATION));
        openAPI.components(new Components()
                .addSecuritySchemes(
                        HttpHeaders.AUTHORIZATION,
                        new SecurityScheme().type(Type.APIKEY).in(In.HEADER).name(HttpHeaders.AUTHORIZATION)
                ));

        return openAPI;
    }

    @Bean
    public GroupedOpenApi httpApi() {
        return GroupedOpenApi.builder()
                .group("colloquial")
                .pathsToMatch("/**")
                .build();
    }

}