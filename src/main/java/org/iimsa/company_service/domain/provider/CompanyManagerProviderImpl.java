package org.iimsa.company_service.domain.provider;

import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.domain.service.CompanyManagerInfoResult;
import org.iimsa.company_service.domain.service.CompanyManagerProvider;

import java.util.UUID;
import org.iimsa.company_service.infrastructure.client.CompanyManagerClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyManagerProviderImpl implements CompanyManagerProvider {
    private final CompanyManagerClient companyManagerClient;

    @Override
    public CompanyManagerInfoResult getCompanyManager(UUID companyManagerId) {
        return companyManagerClient.getCompanyManagerInfo(companyManagerId);
    }
}
