package org.iimsa.company_service.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.iimsa.common.exception.BadRequestException;
import org.iimsa.company_service.domain.exception.HubNotFoundException;
import org.iimsa.company_service.domain.service.HubData;
import org.iimsa.company_service.domain.service.HubProvider;

import java.util.UUID;

@Getter
@ToString
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hub {
    @Column(length = 36, name = "hub_id", nullable = false)
    private UUID id;

    @Column(name = "hub_name", columnDefinition = "TEXT", nullable = false)
    private String name;

    @Column(name = "hub_address")
    private String address;

    protected Hub(UUID id, HubProvider hubInfo) {
        if (id == null || hubInfo == null) {
            throw new BadRequestException("소속 허브 등록/수정을 위한 필수 항목이 누락되었습니다.");
        }

        HubData hub = hubInfo.get(id);
        if (hub == null) {
            throw new HubNotFoundException(id);
        }

        this.id = id;
        this.name = hub.name();
        this.address = hub.address();
    }

}
