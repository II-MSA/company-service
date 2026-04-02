package org.iimsa.company_service.presentation.dto.response;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.iimsa.company_service.domain.model.Company;
import org.iimsa.company_service.domain.model.CompanyType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCompanyResponseDto {
    @AllArgsConstructor
    public static class Create {
        public UUID id;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Info {
        private UUID companyId;
        private String companyName;
        private CompanyType companyType;
        private UUID hubId;
        private String hubName;
        private UUID companyManagerId;
        private String companyManagerName;
        private String address;
        private Double latitude;
        private Double longitude;

        public static Info from(Company company) {

            return Info.builder()
                    .companyId(company.getId())
                    .companyName(company.getCompanyName())
                    .companyType(company.getCompanyType())
                    .hubId(company.getAssociate().getHub().getId())
                    .hubName(company.getAssociate().getHub().getName())
                    .companyManagerId(company.getAssociate().getCompanyManager().getCompanyManagerId())
                    .companyManagerName(company.getAssociate().getCompanyManager().getCompanyManagerName())
                    .address(company.getAddress())
                    .latitude(company.getLatitude())
                    .longitude(company.getLongitude())
                    .build();
        }
    }
}
