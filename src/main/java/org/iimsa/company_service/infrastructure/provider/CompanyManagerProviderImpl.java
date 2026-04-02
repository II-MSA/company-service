package org.iimsa.company_service.infrastructure.provider;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.domain.service.CompanyManagerData;
import org.iimsa.company_service.domain.service.CompanyManagerProvider;
import org.iimsa.company_service.infrastructure.client.CompanyManagerClient;
import org.iimsa.company_service.infrastructure.client.dto.CompanyManagerResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyManagerProviderImpl implements CompanyManagerProvider {

    private final CompanyManagerClient client;

    @Override
    public CompanyManagerData get(UUID companyManagerId) {
        CompanyManagerResponse res = client.getCompanyManager(companyManagerId);
        return res == null || res.name() == null ? null : new CompanyManagerData(res.name());
    }
}
