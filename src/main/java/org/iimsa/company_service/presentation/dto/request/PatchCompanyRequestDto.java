package org.iimsa.company_service.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iimsa.company_service.domain.model.CompanyType;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatchCompanyRequestDto {

    @Data
    public static class ChangeAssociate {
        private UUID hubId;
        private UUID companyManagerId;
    }

    @Data
    public static class ChangeCompanyType {
        private CompanyType companyType;
    }

    @Data
    public static class ChangeCompanyAddress {
        private String address;
        private Double latitude;
        private Double longitude;
    }

}
