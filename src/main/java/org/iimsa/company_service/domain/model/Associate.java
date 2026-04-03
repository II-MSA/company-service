package org.iimsa.company_service.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.iimsa.company_service.domain.service.CompanyManagerProvider;
import org.iimsa.company_service.domain.service.HubProvider;

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

    @Builder
    protected Associate(UUID hubId, UUID companyManagerId, HubProvider hubProvider, CompanyManagerProvider companyManagerProvider) {

        if (hubId == null || companyManagerId == null) {
            throw new IllegalArgumentException("업체의 허브 ID와 담당자 ID는 필수입니다.");
        }

        if (hubProvider == null || companyManagerProvider == null) {
            throw new IllegalArgumentException("외부 연동을 위한 Provider가 누락되었습니다.");
        }

        this.hub = new Hub(hubId, hubProvider);
        this.companyManager = new CompanyManager(companyManagerId, companyManagerProvider);
    }

}
