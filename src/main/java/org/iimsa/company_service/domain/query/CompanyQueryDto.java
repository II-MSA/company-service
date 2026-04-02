package org.iimsa.company_service.domain.query;

import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.iimsa.company_service.domain.repository.CompanyId;

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
