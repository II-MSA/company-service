package org.iimsa.company_service.infrastructure.provider;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.domain.service.HubData;
import org.iimsa.company_service.domain.service.HubProvider;
import org.iimsa.company_service.infrastructure.client.HubClient;
import org.iimsa.company_service.infrastructure.client.dto.HubResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubProviderImpl implements HubProvider {

    private final HubClient client;

    @Override
    public HubData get(UUID hubId) {
        HubResponse res = client.getHub(hubId);
        return res == null || res.id() == null ? null : new HubData(res.name(), res.address());
    }
}
