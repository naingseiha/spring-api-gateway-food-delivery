package com.borntocode.spring_api_gateway.config.logs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RequestLoggingFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        log.info("Request URI: {} {}", request.getMethod(), request.getURI());
        log.info("Request Method: {}", request.getMethod());
        log.info("Request Headers: {}", request.getHeaders());

        if(HttpMethod.POST.equals(request.getMethod()) || HttpMethod.PUT.equals(request.getMethod())) {
            return exchange.getRequest().getBody()
                    .collectList()
                    .map(list-> {
                        String body = list.toString();
                        log.info("Request Body: {}", body);
                        return body;
                    })
                    .flatMap(body -> {
                        // Continue with the filter chain
                        return chain.filter(exchange);
                    });
        }

        return chain.filter(exchange);
    }
}
