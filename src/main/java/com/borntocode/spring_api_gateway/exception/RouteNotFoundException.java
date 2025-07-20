package com.borntocode.spring_api_gateway.exception;

public class RouteNotFoundException extends RuntimeException{
    public RouteNotFoundException(String message) {
        super(message);
    }
}
