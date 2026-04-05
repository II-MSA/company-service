package org.iimsa.company_service.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HubClientResponse(
        UUID id,
        String name,
        String address,
        Double latitude,
        Double longitude,
        UUID hubManagerId
) {
}
