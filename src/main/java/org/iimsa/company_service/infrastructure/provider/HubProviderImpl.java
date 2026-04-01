package org.iimsa.company_service.infrastructure.provider;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.domain.service.HubInfoResult;
import org.iimsa.company_service.domain.service.HubProvider;
import org.iimsa.company_service.infrastructure.client.HubClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HubProviderImpl implements HubProvider {
    private final HubClient hubClient;

    @Override
    public HubInfoResult getHub(UUID hubId) {
        return hubClient.getHubInfo(hubId);
    }
}
