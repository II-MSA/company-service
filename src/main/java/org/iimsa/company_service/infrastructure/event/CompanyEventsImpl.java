package org.iimsa.company_service.infrastructure.event;

import lombok.RequiredArgsConstructor;
import org.iimsa.common.event.Events;
import org.iimsa.company_service.domain.event.CompanyEvents;
import org.iimsa.company_service.domain.model.Company;
import org.iimsa.company_service.infrastructure.messaging.kafka.CompanyTopicProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(CompanyTopicProperties.class)
public class CompanyEventsImpl implements CompanyEvents {

    private final CompanyTopicProperties properties;

    @Override
    public void created(Company company) {
        Events.trigger(UUID.randomUUID().toString(), "COMPANY", "CREATED", properties.create(), company);
    }

    @Override
    public void updated(Company company) {
        Events.trigger(UUID.randomUUID().toString(), "COMPANY", "UPDATED", properties.update(), company);
    }

    @Override
    public void deleted(Company company) {
        Events.trigger(UUID.randomUUID().toString(), "COMPANY", "DELETED", properties.delete(), company);
    }
}
