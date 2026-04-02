package org.iimsa.company_service.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;
import org.iimsa.common.exception.BadRequestException;
import org.iimsa.company_service.domain.exception.CompanyManagerNotFoundException;
import org.iimsa.company_service.domain.exception.HubNotFoundException;
import org.iimsa.company_service.domain.service.CompanyManagerData;
import org.iimsa.company_service.domain.service.CompanyManagerProvider;
import org.iimsa.company_service.domain.service.HubData;
import org.iimsa.company_service.domain.service.HubProvider;

@Getter
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CompanyManager {
    @Column(length = 36, name = "company_manager_id", nullable = false)
    private UUID companyManagerId;

    @Column(name = "company_manager_name", length = 50, nullable = false)
    private String companyManagerName;

    protected CompanyManager(UUID companyManagerId, CompanyManagerProvider companyManagerInfo) {
        if (companyManagerId == null || companyManagerInfo == null) {
            throw new BadRequestException("업체 관리자 등록/수정을 위한 필수 항목이 누락되었습니다.");
        }

        CompanyManagerData companyManager = companyManagerInfo.get(companyManagerId);
        if (companyManager == null) {
            throw new CompanyManagerNotFoundException(companyManagerId);
        }

        this.companyManagerId = companyManagerId;
        this.companyManagerName = companyManager.name();
    }
}
