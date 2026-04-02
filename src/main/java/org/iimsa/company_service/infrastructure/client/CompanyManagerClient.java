package org.iimsa.company_service.infrastructure.client;

import java.util.UUID;
import org.iimsa.company_service.infrastructure.client.dto.CompanyManagerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface CompanyManagerClient {

    @GetMapping("/users/{userId}")
    CompanyManagerResponse getCompanyManager(@PathVariable("userId") UUID userId);

}
