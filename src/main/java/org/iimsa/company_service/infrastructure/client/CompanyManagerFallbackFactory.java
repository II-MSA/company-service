package org.iimsa.company_service.infrastructure.client;

import jakarta.ws.rs.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CompanyManagerFallbackFactory implements FallbackFactory<CompanyManagerClient> {

    @Override
    public CompanyManagerClient create(Throwable e) {
        return companyManagerId -> {
            log.error("[User Service Fallback] ID: {} 조회 중 장애 발생. 사유: {}", companyManagerId, e.getMessage(), e);
            throw new InternalServerErrorException("User Service API 요청 처리 실패, 잠시 후 다시 시도해주세요.");
        };
    }
}
