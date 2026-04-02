package org.iimsa.company_service.application.dto;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.iimsa.company_service.domain.model.CompanyType;
import org.iimsa.company_service.domain.model.UserType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyServiceDto {

    @Getter
    @Builder
    public static class Create {
        private final String name;
        private final CompanyType companyType;

        private final UUID hubId;
        private final UUID companyManagerId;
        private final UserType userType;
        private final String address;
        private final Double latitude;
        private final Double longitude;
    }
}
