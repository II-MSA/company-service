package org.iimsa.company_service.domain.provider;

import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.domain.service.HubInfoResult;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HubProviderImpl {
    private final HubClient hubClient;

    @Override
    public HubInfoResult getHub(UUID hubId) {
        return hubClient.getHub(hubId);
    }

}
