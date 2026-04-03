package org.iimsa.company_service.infrastructure.messaging.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "topics.company")
public record CompanyTopicProperties(
        String update,
        String delete) {
}
