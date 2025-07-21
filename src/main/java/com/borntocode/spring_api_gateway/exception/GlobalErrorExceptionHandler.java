package com.borntocode.spring_api_gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalErrorExceptionHandler {
    @ExceptionHandler(RouteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ApiResponse<Object>> handlePathNotFoundException(PathNotFoundException exception) {
        return Mono.just(ApiResponse.error("404", exception.getMessage()));
    }

    @ExceptionHandler(RouteCreateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ApiResponse<Object>> handleRouteCreateException(RouteNotFoundException exception) {
        return Mono.just(ApiResponse.error("500", exception.getMessage()));
    }
}
