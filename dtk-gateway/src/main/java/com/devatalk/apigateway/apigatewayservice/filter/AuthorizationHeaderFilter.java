package com.devatalk.apigateway.apigatewayservice.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Base64;
import java.util.Objects;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    Environment env;
    @Value("${jwt.token.secret}")
    String secretKey;
    Key key;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = Objects.requireNonNull(request.getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
            String jwt = authorizationHeader.substring(7);

            // JWT로부터 email 추출 -> header에 실어보내기
            String email = isJwtValid(jwt);

            if (email == null || email.isEmpty()) {
                return onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED);
            }
            log.info("jwt로부터 email 추출 성공 -> {}", email);

            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                    .header("User-Email", email)
                    .build();

            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(modifiedRequest)
                    .build();

            return chain.filter(modifiedExchange);
        };
    }

    // mono, flux -> webflux : 단위 값이면 mono로 반환
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(err);
        return response.setComplete();
    }

    private String isJwtValid(String jwt) {
        key = Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(secretKey.getBytes()).getBytes());
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwt)
                    .getBody()
                    .get("email")
                    .toString();
        } catch (Exception ex) {
            log.info("isJwtValid ex.getClass() = {}", ex.getClass());
            log.info("isJwtValid ex.getMessage() = {}", ex.getMessage());
            throw ex;
        }
    }

}