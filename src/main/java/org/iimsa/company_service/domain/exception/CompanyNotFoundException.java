package org.iimsa.company_service.domain.exception;

import java.util.UUID;
import org.iimsa.common.exception.NotFoundException;

public class CompanyNotFoundException extends NotFoundException {
    public CompanyNotFoundException(UUID companyId) {
        super("등록되지 않은 업체 정보입니다. COMPANYMANAGER ID: " + companyId.toString());
    }
}
