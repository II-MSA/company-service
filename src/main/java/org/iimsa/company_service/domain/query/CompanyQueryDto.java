package org.iimsa.company_service.domain.query;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.iimsa.company_service.domain.model.CompanyId;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompanyQueryDto {
    @Getter
    @Builder
    public static class Search {
        private List<CompanyId> companyIds;
        private List<UUID> hubsIds;
        private List<UUID> companyManagersIds;
        private String companyName;
        private String hubName;
        private String companyManagerName;
    }
}
