package com.borntocode.spring_api_gateway.service;

import com.borntocode.spring_api_gateway.dto.ApiRouteRequest;
import com.borntocode.spring_api_gateway.dto.ApiRouteResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApiRouteService {
    Mono<ApiRouteResponse> create(ApiRouteRequest apiRouteRequest);
    Mono<ApiRouteResponse> update(ApiRouteRequest apiRouteRequest, Long id);
    Mono<ApiRouteResponse> findById(Long id);
    Flux<ApiRouteResponse> findAll();
    Mono<Void> deleteById(Long id);
}
