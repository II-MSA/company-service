package org.iimsa.company_service.domain.event;

import java.util.UUID;
import org.iimsa.company_service.domain.model.Company;

public record CompanyCreatedPayload(
        UUID companyId,
        String companyName,
        String companyType,
        UUID hubId,
        String address,
        UUID companyManagerId
) {
    public static CompanyCreatedPayload from(Company company) {
        return new CompanyCreatedPayload(
                company.getId(),
                company.getCompanyName(),
                company.getCompanyType().name(),
                company.getHubId(),
                company.getAddress(),
                company.getCompanyManager() != null ? company.getCompanyManager().getId() : null
        );
    }
}
