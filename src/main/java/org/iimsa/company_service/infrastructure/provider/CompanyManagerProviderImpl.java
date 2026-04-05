package org.iimsa.company_service.infrastructure.provider;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iimsa.company_service.domain.service.CompanyManagerData;
import org.iimsa.company_service.domain.service.CompanyManagerProvider;
import org.iimsa.company_service.domain.service.dto.CompanyManagerResponse;
import org.iimsa.company_service.infrastructure.client.CompanyManagerClient;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyManagerProviderImpl implements CompanyManagerProvider {

    private final CompanyManagerClient client;

    @Override
    public CompanyManagerData get(UUID companyManagerId) {
        try {
            CompanyManagerResponse res = client.getCompanyManager(companyManagerId);
            return res == null || res.name() == null ? null : new CompanyManagerData(res.name());

        } catch (FeignException.NotFound e) {
            log.warn("CompanyManager를 찾을 수 없습니다. companyManagerId={}", companyManagerId);
            return null;

        } catch (FeignException e) {
            log.error("CompanyManager 서비스 호출 실패: companyManagerId={}, Error={}", companyManagerId, e.getMessage());
            throw new RuntimeException("업체 담당자 정보 조회 중 외부 서비스 호출에 실패했습니다.", e);
        }
    }
}
