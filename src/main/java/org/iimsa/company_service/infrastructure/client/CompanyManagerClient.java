package org.iimsa.company_service.infrastructure.client;

import java.util.UUID;
import org.iimsa.company_service.domain.service.CompanyManagerInfoResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface CompanyManagerClient {

    @GetMapping("/api/v1/user/{userId}")
    CompanyManagerInfoResult getCompanyManagerInfo(@PathVariable("userId") UUID userId);
}
