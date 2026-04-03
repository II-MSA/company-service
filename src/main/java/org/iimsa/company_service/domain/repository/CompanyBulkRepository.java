package org.iimsa.company_service.domain.repository;

import org.iimsa.company_service.domain.model.CompanyManager;
import org.iimsa.company_service.domain.model.Hub;

public interface CompanyBulkRepository {
    long bulkUpdateHubInfo(Hub hub);

    long bulkUpdateCompanyManagerInfo(CompanyManager companyManager);
}
