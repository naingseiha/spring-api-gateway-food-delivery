package com.borntocode.spring_api_gateway.service.impl;

import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class GatewayRouteService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public GatewayRouteService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void refreshRoutes() {
        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
