package org.iimsa.company_service.domain.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CompanyManagerResponse(
        @JsonProperty("username") String name
) {
}
