package org.iimsa.company_service.presentation.dto.request;

import java.util.UUID;
import org.iimsa.company_service.domain.model.CompanyType;

public record PatchCompanyRequestDto(
        String companyName,
        CompanyType companyType,
        UUID hubId,
        UUID companyManagerId,
        String address,
        Double latitude,
        Double longitude
) {
}