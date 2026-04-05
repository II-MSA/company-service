package org.iimsa.company_service.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CompanyClientResponse(
        boolean success,
        String message,
        UserInfo data,
        String traceId
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record UserInfo(
            String id,
            String name,
            String role,
            String email,
            String slackId,
            String hubId,
            String hubName,
            Integer deliveryRotationOrder,
            String companyId,
            String companyName,
            String status
    ) {
    }
}
