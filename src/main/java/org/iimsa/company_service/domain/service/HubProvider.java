package org.iimsa.company_service.domain.service;

import java.util.UUID;

public interface HubProvider {
    HubData get(UUID hubId);
}
