package org.iimsa.company_service.infrastructure.client;

import java.util.UUID;
import org.iimsa.company_service.domain.service.HubInfoResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub-service")
public interface HubClient {

    @GetMapping("/api/v1/hubs/{hubId}")
    HubInfoResult getHubInfo(@PathVariable("hubId") UUID hubId);

}
