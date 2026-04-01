package org.iimsa.company_service.domain.event;

import org.iimsa.company_service.domain.model.Company;

import java.util.UUID;

public record CompanyUpdatedPayload(
        UUID companyId,
        String companyName,
        String companyType,
        UUID hubId,
        String address,
        UUID companyManagerId
) {
    public static CompanyUpdatedPayload from(Company company) {
        return new CompanyUpdatedPayload(
                company.getId(),
                company.getCompanyName(),
                company.getCompanyType().name(),
                company.getHubId(),
                company.getAddress(),
                company.getCompanyManager() != null ? company.getCompanyManager().getId() : null
        );
    }
}
