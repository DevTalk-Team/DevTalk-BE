package com.devatalk.apigateway.apigatewayservice.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ErrorHandlerFilter implements GlobalFilter, Ordered {
    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange)
                .onErrorResume(Exception.class, e -> handleException(exchange, e));
    }

    private Mono<Void> handleException(ServerWebExchange exchange, Exception ex) {
        log.info("[ErrorHandlerFilter] 진입");
        Class<? extends Throwable> exceptionClass = ex.getClass();

        Map<String, Object> responseBody = new HashMap<>();
        if (exceptionClass == ExpiredJwtException.class) {
            // EXPIRED_TOKEN
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            responseBody.put("code", "01155");
            responseBody.put("message", "만료된 토큰");
        } else if (exceptionClass == SignatureException.class) {
            // INCORRECT_SIGNATURE
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            responseBody.put("code", "01154");
            responseBody.put("message", "잘못된 서명입니다.");
        } else if (exceptionClass == MalformedJwtException.class | exceptionClass == UnsupportedJwtException.class) {
            // UNSUPPORTED_TOKEN
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            responseBody.put("code", "01156");
            responseBody.put("message", "지원되지 않는 토큰");
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            responseBody.put("code", "00002");
            responseBody.put("message", ex.getMessage());
        }

        DataBuffer wrap = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            byte[] bytes = objectMapper.writeValueAsBytes(responseBody);
            wrap = exchange.getResponse().bufferFactory().wrap(bytes);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return wrap != null ?
                exchange.getResponse()
                        .writeWith(Flux.just(wrap)) :
                Mono.empty();
    }
}
