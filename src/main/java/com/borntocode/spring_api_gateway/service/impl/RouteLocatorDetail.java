package com.borntocode.spring_api_gateway.service.impl;

import com.borntocode.spring_api_gateway.models.ApiRoute;
import com.borntocode.spring_api_gateway.repository.ApiRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RouteLocatorDetail implements RouteLocator {

    private final ApiRouteRepository apiRouteRepository;
    private final RouteLocatorBuilder routeLocatorBuilder;

    @Override
    public Flux<Route> getRoutes() {
        RouteLocatorBuilder.Builder builder = routeLocatorBuilder.routes();

        return apiRouteRepository.findAll()
                .map(apiRoute -> builder.route(apiRoute.getId().toString(), predicateSpec -> setPredicateSpec(apiRoute, predicateSpec)))
                .collectList()
                .flatMapMany(builders -> builder.build().getRoutes());
    }

    @Override
    public Flux<Route> getRoutesByMetadata(Map<String, Object> metadata) {
        return RouteLocator.super.getRoutesByMetadata(metadata);
    }

    public Buildable<Route> setPredicateSpec(ApiRoute apiRoute, PredicateSpec predicateSpec) {
        BooleanSpec booleanSpec = predicateSpec.path(apiRoute.getPath());

        if(!apiRoute.getMethod().isBlank()){
            booleanSpec.and().method(apiRoute.getMethod());
        }

        return booleanSpec.uri(apiRoute.getUri());
    }
}
