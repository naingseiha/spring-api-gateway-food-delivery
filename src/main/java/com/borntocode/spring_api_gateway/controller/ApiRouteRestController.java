package com.borntocode.spring_api_gateway.controller;

import com.borntocode.spring_api_gateway.dto.ApiRouteRequest;
import com.borntocode.spring_api_gateway.dto.ApiRouteResponse;
import com.borntocode.spring_api_gateway.exception.ApiResponse;
import com.borntocode.spring_api_gateway.exception.RouteNotFoundException;
import com.borntocode.spring_api_gateway.service.ApiRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(
        value = "/v1/api/routes",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)

public class ApiRouteRestController {
    private final ApiRouteService apiRouteService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<ApiResponse<ApiRouteResponse>> create(@RequestBody ApiRouteRequest apiRouteRequest) {
        log.info("Received request to create route: {}", apiRouteRequest);

        return apiRouteService.create(apiRouteRequest)
                .map(ApiResponse::success)
                .onErrorResume(e-> {
                    log.error("Error while creating route: {}", apiRouteRequest, e);
                    return Mono.just(ApiResponse.error("500", "Failed to create route"));
                });
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ApiResponse<ApiRouteResponse>> update(@PathVariable Long id,
                                                      @RequestBody ApiRouteRequest apiRouteRequest) {

        log.info("Received request to update route with ID {}: {}", id, apiRouteRequest);

        return apiRouteService.update(apiRouteRequest, id)
                .map(ApiResponse::success)
                .onErrorResume(RouteNotFoundException.class, e-> {
                    log.error("Route not found for ID {}: {}", id, e.getMessage());
                    return Mono.just(ApiResponse.error("404", "Route not found"));
                })
                .onErrorResume(e-> {
                    log.error("Error while creating route: {}", apiRouteRequest, e);
                    return Mono.just(ApiResponse.error("500", "Failed to update route"));
                });
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ApiResponse<ApiRouteResponse>> findById(@PathVariable Long id) {
        log.info("Received request to find route with ID {}", id);
        return apiRouteService.findById(id)
                .map(ApiResponse::success)
                .onErrorResume(RouteNotFoundException.class, e-> {
                    log.error("Route not found for ID {}: {}", id, e.getMessage());
                    return Mono.just(ApiResponse.error("404", "Route not found"));
                })
                .onErrorResume(e-> {
                    log.error("Error while finding route with ID {}: {}", id, e.getMessage());
                    return Mono.just(ApiResponse.error("500", "Failed to find route"));
                });
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<ApiResponse<List<ApiRouteResponse>>> findAll() {
        return apiRouteService.findAll()
                .collectList()
                .map(ApiResponse::success)
                .onErrorResume(e-> {
                    log.error("Error while finding all routes: {}", e.getMessage());
                    return Mono.just(ApiResponse.error("500", "Failed to find routes"));
                });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> delete(@PathVariable Long id) {
        log.info("Received request to delete route with ID {}", id);
        return apiRouteService.deleteById(id);
    }
}
