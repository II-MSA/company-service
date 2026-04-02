package org.iimsa.company_service.presentation.dto.request;

import java.util.UUID;
import lombok.NoArgsConstructor;
import org.iimsa.company_service.application.dto.CompanyServiceDto;
import org.iimsa.company_service.domain.model.CompanyType;
import org.iimsa.company_service.domain.model.UserType;

@NoArgsConstructor
public class CreateCompanyRequestDto {

    public static class Create {
        private String companyName;
        private CompanyType companyType;
        private UUID hubId;
        private UUID companyManagerId;
        private UserType userType;
        private String address;
        private Double latitude;
        private Double longitude;

        public CompanyServiceDto.Create toDto() {
            return CompanyServiceDto.Create.builder()
                    .name(companyName)
                    .companyType(companyType)
                    .hubId(hubId)
                    .companyManagerId(companyManagerId)
                    .userType(userType)
                    .address(address)
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
        }
    }
}
