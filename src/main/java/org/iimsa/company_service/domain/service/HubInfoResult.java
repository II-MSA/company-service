package org.iimsa.company_service.domain.service;

import java.util.UUID;

public record HubInfoResult(
        UUID hubId,
        String hubName
) {
}
