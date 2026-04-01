package org.iimsa.company_service.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Hub {
    @Column(length = 36, name = "hub_id", nullable = false)
    private UUID id;

    @Column(name = "hub_name", columnDefinition = "TEXT", nullable = false)
    private String name;
}
