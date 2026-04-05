package org.iimsa.company_service.domain.event;

import org.iimsa.company_service.domain.model.Company;

import java.time.LocalDateTime;
import java.util.UUID;

public record CompanyDeletedPayload(
        UUID companyId,
        String companyName,
        LocalDateTime deletedAt,
        String deletedBy
) {
    public static CompanyDeletedPayload from(Company company) {
        return new CompanyDeletedPayload(
                company.getId(),
                company.getCompanyName(),
                company.getDeletedAt(),
                company.getDeletedBy()
        );
    }
}