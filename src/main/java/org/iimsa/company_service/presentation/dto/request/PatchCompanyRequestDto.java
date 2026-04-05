package org.iimsa.company_service.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iimsa.company_service.domain.model.CompanyType;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatchCompanyRequestDto {

    @Data
    public static class ChangeAssociate {
        @NotNull
        private UUID hubId;
        @NotNull
        private UUID companyManagerId;
    }

    @Data
    public static class ChangeCompanyType {
        @NotNull
        private CompanyType companyType;
    }

    @Data
    public static class ChangeCompanyAddress {
        @NotNull
        private String address;
        private Double latitude;
        private Double longitude;
    }

}
