package com.example.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

@Component
@Slf4j
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient;
    @Value("${auth.service.url}") String url;

    public JwtValidationGatewayFilterFactory(
            @LoadBalanced WebClient.Builder webClientBuilder
    ) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public GatewayFilter apply(Object config) {

        return (exchange, chain) -> {
            if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
                exchange.getResponse().getHeaders().add("Access-Control-Allow-Origin", "http://localhost:4200");
                exchange.getResponse().getHeaders().add("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
                exchange.getResponse().getHeaders().add("Access-Control-Allow-Headers", "Authorization,Content-Type,X-User-Email");
                exchange.getResponse().getHeaders().add("Access-Control-Allow-Credentials", "true");
                return exchange.getResponse().setComplete();
            }
            String token = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (token == null || !token.toLowerCase().startsWith("bearer ")) {
                log.warn("Missing or malformed Authorization header: {}", token);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String jwt = token.substring(7).trim();

            return webClient.get()
                    .uri(url + "/auth/validate")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    .toBodilessEntity()
                    .flatMap(response -> {
                        String userEmail = response.getHeaders().getFirst("X-User-Email");

                        if (userEmail != null) {
                            ServerWebExchange mutatedExchange = exchange.mutate()
                                    .request(r -> r.headers(headers -> {
                                        headers.set("X-User-Email", userEmail);
                                        headers.set(HttpHeaders.AUTHORIZATION, token);
                                    }))
                                    .build();
                            return chain.filter(mutatedExchange);
                        } else {
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }
                    })
                    .onErrorResume(e -> {
                        log.error("Error validating JWT token", e);
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    });
        };
    }
}
