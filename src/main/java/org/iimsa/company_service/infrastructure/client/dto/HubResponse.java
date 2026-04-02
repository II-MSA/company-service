package org.iimsa.company_service.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HubResponse(
        @JsonProperty("hubId") UUID id,
        @JsonProperty("hubName") String name,
        @JsonProperty("address") String address
) {}
