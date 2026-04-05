package org.iimsa.company_service.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record CompanyId(
        @JdbcTypeCode(SqlTypes.UUID)
        @Column(length = 36, name = "company_id")
        UUID id
) implements Serializable {
    public static CompanyId of(UUID id) {
        return new CompanyId(id);
    }

    public static CompanyId of() {
        return new CompanyId(UUID.randomUUID());
    }

    public static CompanyId fromString(String sub) {
        return new CompanyId(UUID.fromString(sub));
    }
}
