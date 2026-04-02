package org.iimsa.company_service.domain.exception;

import java.util.UUID;
import org.iimsa.common.exception.NotFoundException;

public class CompanyManagerNotFoundException extends NotFoundException {
    public CompanyManagerNotFoundException(UUID companyManagerId) {
        super("등록되지 않은 업체 관리자 정보입니다. COMPANYMANAGER ID: " + companyManagerId.toString());
    }
}
