package org.iimsa.company_service.infrastructure.client;

import org.iimsa.company_service.domain.service.dto.CompanyManagerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service")
public interface CompanyManagerClient {

    @GetMapping("/users/{userId}")
    CompanyManagerResponse getCompanyManager(@PathVariable("userId") UUID userId);

}
