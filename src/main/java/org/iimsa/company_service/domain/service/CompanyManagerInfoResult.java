package org.iimsa.company_service.domain.service;

import java.util.UUID;

public record CompanyManagerInfoResult(
        UUID companyManagerId,
        String companyManagerName
) {
}
