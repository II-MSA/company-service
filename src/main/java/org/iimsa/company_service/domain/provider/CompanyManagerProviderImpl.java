package org.iimsa.company_service.domain.provider;

import org.iimsa.company_service.domain.service.CompanyManagerInfoResult;
import org.iimsa.company_service.domain.service.CompanyManagerProvider;

import java.util.UUID;

public class CompanyManagerProviderImpl {
    private final CompanyManagerClient companyManagerClient;

    @Override
    public CompanyManagerInfoResult getCompanyManager(UUID companyManagerId) {
        return companyManagerClient.getCompanyManager(companyManagerId);
    }
}
