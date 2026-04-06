package org.iimsa.company_service.domain.model;

import java.io.Serializable;
import java.util.UUID;

public record CompanyId(UUID id) implements Serializable {

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
