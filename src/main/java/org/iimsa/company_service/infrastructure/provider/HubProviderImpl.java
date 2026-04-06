package org.iimsa.company_service.infrastructure.provider;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iimsa.company_service.domain.service.HubData;
import org.iimsa.company_service.domain.service.HubProvider;
import org.iimsa.company_service.domain.service.dto.HubResponse;
import org.iimsa.company_service.infrastructure.client.HubClient;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubProviderImpl implements HubProvider {

    private final HubClient client;

    @Override
    public HubData get(UUID hubId) {
        if (hubId == null) {
            throw new IllegalArgumentException("hubId must not be null");
        }

        try {
            HubResponse res = client.getHub(hubId);

            if (res == null || res.id() == null) {
                return null;
            }

            return new HubData(res.name(), res.address());

        } catch (FeignException.NotFound e) {
            log.warn("Hub를 찾을 수 없습니다. hubId={}", hubId);
            return null;

        } catch (FeignException e) {
            log.error("Hub 서비스 호출 실패: hubId={}, Error={}", hubId, e.getMessage());
            throw new RuntimeException("허브 정보 조회 중 외부 서비스 호출에 실패했습니다.", e);
        }
    }
}
