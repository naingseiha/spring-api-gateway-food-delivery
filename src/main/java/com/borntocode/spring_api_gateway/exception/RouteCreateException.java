package com.borntocode.spring_api_gateway.exception;

public class RouteCreateException extends RuntimeException{
    public RouteCreateException(String message) {
        super(message);
    }
}
