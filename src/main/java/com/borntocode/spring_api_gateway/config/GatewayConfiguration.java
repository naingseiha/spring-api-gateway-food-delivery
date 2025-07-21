package com.borntocode.spring_api_gateway.config;

import com.borntocode.spring_api_gateway.repository.ApiRouteRepository;
import com.borntocode.spring_api_gateway.service.impl.RouteLocatorDetail;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
    @Bean
    public RouteLocator routeLocator(ApiRouteRepository apiRouteRepository, RouteLocatorBuilder routeLocatorBuilder) {
        return new RouteLocatorDetail(apiRouteRepository, routeLocatorBuilder);
    }
}
