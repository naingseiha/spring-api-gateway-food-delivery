package com.borntocode.spring_api_gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ApiRouteRequest(
        Long id,
        String uri,
        String path,
        String method,
        String description,
        @JsonProperty("group_code")
        String groupCode,
        @JsonProperty("rate_limit")
        Integer rateLimit,
        @JsonProperty("rate_limit_duration")
        Integer rateLimitDuration,
        String status,
        String createdBy,
        String updatedBy
) {
}
