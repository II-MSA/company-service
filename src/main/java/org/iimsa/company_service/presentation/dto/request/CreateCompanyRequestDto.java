package org.iimsa.company_service.presentation.dto.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.iimsa.company_service.domain.model.CompanyType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCompanyRequestDto {

    private String companyName;
    private CompanyType companyType;
    private String address;
    private Double latitude;
    private Double longitude;

    private UUID hubId;
    private UUID companyManagerId;
}
