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
        String status,
        String createdBy,
        String updatedBy
) {
}
