package com.borntocode.spring_api_gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ApiRouteResponse(
        Long id,
        String uri,
        String path,
        String method,
        String description,
        @JsonProperty("group_code") String groupCode,
        String status,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("created_by") String createdBy,
        @JsonProperty("updated_at") String updatedAt,
        @JsonProperty("updated_by") String updatedBy
) {
}
