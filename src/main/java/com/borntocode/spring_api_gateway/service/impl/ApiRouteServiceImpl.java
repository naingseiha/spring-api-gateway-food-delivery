package com.borntocode.spring_api_gateway.service.impl;
import com.borntocode.spring_api_gateway.dto.ApiRouteRequest;
import com.borntocode.spring_api_gateway.dto.ApiRouteResponse;
import com.borntocode.spring_api_gateway.exception.RouteCreateException;
import com.borntocode.spring_api_gateway.exception.RouteNotFoundException;
import com.borntocode.spring_api_gateway.models.ApiRoute;
import com.borntocode.spring_api_gateway.repository.ApiRouteRepository;
import com.borntocode.spring_api_gateway.service.ApiRouteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Slf4j
@AllArgsConstructor
public class ApiRouteServiceImpl implements ApiRouteService {

    private final ApiRouteRepository apiRouteRepository;

    @Override
    public Mono<ApiRouteResponse> create(ApiRouteRequest apiRouteRequest) {
        ApiRoute apiRoute = convertRouteRequestToApiRoute(apiRouteRequest);
        return apiRouteRepository.save(apiRoute)
                .doOnSuccess(route-> log.info("Route created successfully: {}", route))
                .map(this::convertApiRouteToRouteResponse)
                .onErrorMap(ex-> {
                    log.error("Error while creating route: {}", ex.getMessage());
                    throw new RouteCreateException("An error occurred while creating the route: " + ex.getMessage());
                });
    }

    @Override
    public Mono<ApiRouteResponse> update(ApiRouteRequest apiRouteRequest, Long id) {
        return apiRouteRepository.updateRoute(
                id,
                apiRouteRequest.uri(),
                apiRouteRequest.path(),
                apiRouteRequest.method(),
                apiRouteRequest.description(),
                apiRouteRequest.status(),
                "admin"
        ).switchIfEmpty(Mono.error(new RouteNotFoundException("Route with id " + id + " not found.")))
                .doOnSuccess(route-> log.info("Route updated successfully: {}", route))
                .map(this::convertApiRouteToRouteResponse);
    }

    @Override
    public Mono<ApiRouteResponse> findById(Long id) {
        return apiRouteRepository.findFirstById(id)
                .switchIfEmpty(Mono.error(new RouteNotFoundException("Route with id " + id + " not found.")))
                .map(this::convertApiRouteToRouteResponse)
                .onErrorResume(RouteNotFoundException.class, ex -> {
                    log.error("Route with id {} not found: {}", id, ex.getMessage());
                    return Mono.error(ex);
                }).onErrorResume(ex->{
                    log.error("Error while finding route: {}", ex.getMessage());
                    return Mono.error(new RouteNotFoundException("Route with id " + id + " not found."));
                });
    }

    @Override
    public Flux<ApiRouteResponse> findAll() {
        return apiRouteRepository.findAll()
                .map(this::convertApiRouteToRouteResponse)
                .onErrorResume(ex -> {
                    log.error("Error while finding all routes: {}", ex.getMessage());
                    return Flux.error(new RouteNotFoundException("Routes not found."));
                });
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return apiRouteRepository.deleteAllById(id)
                .doOnSuccess(aVoid -> log.info("Route with id {} deleted successfully", id))
                .onErrorResume(ex -> {
                    log.error("Error while deleting route with id {}: {}", id, ex.getMessage());
                    return Mono.error(new RouteNotFoundException("Route with id " + id + " not found."));
                });
    }

    public ApiRoute convertRouteRequestToApiRoute(ApiRouteRequest apiRouteRequest) {
        return ApiRoute.builder()
                .id(apiRouteRequest.id())
                .uri(apiRouteRequest.uri())
                .path(apiRouteRequest.path())
                .method(apiRouteRequest.method())
                .description(apiRouteRequest.description())
                .groupCode(apiRouteRequest.groupCode())
                .status(apiRouteRequest.status())
                .createdBy("admin")
                .createdAt(LocalDate.now())
                .build();
    }

    public ApiRouteResponse convertApiRouteToRouteResponse(ApiRoute apiRoute) {
        return ApiRouteResponse.builder()
                .id(apiRoute.getId())
                .uri(apiRoute.getUri())
                .path(apiRoute.getPath())
                .method(apiRoute.getMethod())
                .description(apiRoute.getDescription())
                .groupCode(apiRoute.getGroupCode())
                .status(apiRoute.getStatus())
                .createdAt(apiRoute.getCreatedAt().toString())
                .createdBy(apiRoute.getCreatedBy())
                .updatedAt(apiRoute.getUpdatedAt() != null ? apiRoute.getUpdatedAt().toString() : LocalDate.now().toString())
                .updatedBy(apiRoute.getUpdatedBy() != null ? apiRoute.getUpdatedBy() : "admin")
                .build();
    }
}
