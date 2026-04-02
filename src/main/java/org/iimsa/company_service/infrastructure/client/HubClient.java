package org.iimsa.company_service.infrastructure.client;

import java.util.UUID;
import org.iimsa.company_service.infrastructure.client.dto.HubResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub-service")
public interface HubClient {

    @GetMapping("/hubs/{hubId}")
    HubResponse getHub(@PathVariable("hubId") UUID hubId);

}
