package org.iimsa.company_service.domain.event;

import org.iimsa.company_service.domain.model.Associate;
import org.iimsa.company_service.domain.model.Company;

import java.util.UUID;

public record CompanyCreatedPayload(
        UUID companyId,
        String companyName,
        String companyType,
        UUID hubId,
        String address,
        UUID companyManagerId
) {
    public static CompanyCreatedPayload from(Company company) {
        Associate associate = company.getAssociate();

        if (associate == null || associate.getHub() == null || associate.getCompanyManager() == null) {
            throw new IllegalStateException("Company associate information is incomplete");
        }

        return new CompanyCreatedPayload(
                company.getId(),
                company.getCompanyName(),
                company.getCompanyType().name(),
                associate.getHub().getId(),
                company.getAddress(),
                associate.getCompanyManager().getCompanyManagerId()
        );
    }
}
