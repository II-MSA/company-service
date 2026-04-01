package org.iimsa.company_service.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.iimsa.company_service.domain.service.HubInfoResult;
import org.iimsa.company_service.domain.service.HubProvider;
import org.iimsa.company_service.domain.service.CompanyManagerInfoResult;
import org.iimsa.company_service.domain.service.CompanyManagerProvider;

import java.util.UUID;

@Getter
@ToString
@Embeddable
@NoArgsConstructor
public class Associate {
    @Embedded
    private Hub hub;

    @Embedded
    private CompanyManager companyManager;

    protected Associate(UUID hubId, UUID companyManagerId, HubProvider hubProvider, CompanyManagerProvider companyManagerProvider) {
        if (hubId == null || hubProvider == null)  {
            throw new IllegalArgumentException("허브 ID 및 처리 Provider는 필수 항목 입니다.");
        }
        if (companyManagerId == null || companyManagerProvider == null)  {
            throw new IllegalArgumentException("업체 매니저 ID 및 처리 Provider는 필수 항목 입니다.");
        }

        HubInfoResult hubResult = hubProvider.getHub(hubId);
        CompanyManagerInfoResult companyManagerResult = companyManagerProvider.getCompanyManager(companyManagerId);

        if (hubResult == null) {
            throw new IllegalArgumentException("허브를 찾을수 없습니다. ID: " + hubId.toString());
        }
        if (companyManagerResult == null) {
            throw new IllegalArgumentException("업체 매니저를 찾을수 없습니다. ID: " + companyManagerId.toString());
        }

        this.hub = new Hub(hubId, hubResult.hubName());
        this.companyManager = new CompanyManager(companyManagerId, companyManagerResult.companyManagerName());
    }
}
