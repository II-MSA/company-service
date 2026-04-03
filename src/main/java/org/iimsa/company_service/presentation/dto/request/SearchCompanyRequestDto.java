package org.iimsa.company_service.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.iimsa.company_service.domain.query.CompanyQueryDto;
import org.iimsa.company_service.domain.repository.CompanyId;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchCompanyRequestDto {
    @Data
    public static class Search {

        private List<UUID> companyIds;

        private List<UUID> hubsIds;

        private List<UUID> companyManagersIds;

        private String companyName;

        private String hubName;

        private String companyManagerName;

        public CompanyQueryDto.Search toDomainDto() {
            return CompanyQueryDto.Search.builder()
                    .companyIds(companyIds != null
                            ? companyIds.stream().map(CompanyId::of).collect(Collectors.toList())
                            : null)
                    .hubsIds(hubsIds)
                    .companyManagersIds(companyManagersIds)
                    .companyName(companyName)
                    .hubName(hubName)
                    .companyManagerName(companyManagerName)
                    .build();
        }
    }
}
