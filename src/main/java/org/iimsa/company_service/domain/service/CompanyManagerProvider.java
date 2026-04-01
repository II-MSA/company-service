package org.iimsa.company_service.domain.service;

import java.util.UUID;

public interface CompanyManagerProvider {
    CompanyManagerInfoResult getCompanyManager(UUID companyManagerId);
}
