package org.iimsa.company_service.infrastructure.provider;

import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.domain.service.HubData;
import org.iimsa.company_service.domain.service.HubProvider;
import org.iimsa.company_service.domain.service.dto.HubResponse;
import org.iimsa.company_service.infrastructure.client.HubClient;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HubProviderImpl implements HubProvider {

    private final HubClient client;

    @Override
    public HubData get(UUID hubId) {
        if (hubId == null) {
            throw new IllegalArgumentException("hubId must not be null");
        }

        HubResponse res = client.getHub(hubId);

        if (res == null || res.id() == null) {
            throw new IllegalStateException("Hub not found: " + hubId);
        }

        return new HubData(res.name(), res.address());
    }
}
