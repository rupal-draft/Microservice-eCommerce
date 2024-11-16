package com.microservice.eCommerce.api_gateway.api_gateway.filters;

import com.microservice.eCommerce.api_gateway.api_gateway.service.JwtService;
import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final JwtService jwtService;

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            String authorizationHeader = exchange
                    .getRequest()
                    .getHeaders()
                    .getFirst("Authorization");
            if(authorizationHeader == null){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            String token = authorizationHeader.split("Bearer ")[1];
            Long id = jwtService.getIdFromToken(token);
            exchange
                    .getRequest()
                    .mutate()
                    .header("X-User-Id",id.toString())
                    .build();
            return chain.filter(exchange);
        });
    }

    @Data
    public static class Config{}
}
