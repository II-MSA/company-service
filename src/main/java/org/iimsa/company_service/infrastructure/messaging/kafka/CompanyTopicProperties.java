package org.iimsa.company_service.infrastructure.messaging.kafka;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "topics.company")
public record CompanyTopicProperties(
        @NotBlank String update,
        @NotBlank String delete) {
}
