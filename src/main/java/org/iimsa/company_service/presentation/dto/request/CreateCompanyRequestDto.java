package org.iimsa.company_service.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.iimsa.company_service.application.dto.CompanyServiceDto;
import org.iimsa.company_service.domain.model.CompanyType;
import org.iimsa.company_service.domain.model.UserType;

import java.util.UUID;

@NoArgsConstructor
public class CreateCompanyRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Create {
        @NotBlank
        private String companyName;
        @NotNull
        private CompanyType companyType;
        @NotNull
        private UUID hubId;
        @NotNull
        private UUID companyManagerId;
        @NotNull
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
