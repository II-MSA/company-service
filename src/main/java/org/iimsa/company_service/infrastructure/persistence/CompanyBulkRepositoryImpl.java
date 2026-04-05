package org.iimsa.company_service.infrastructure.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.domain.model.CompanyManager;
import org.iimsa.company_service.domain.model.Hub;
import org.iimsa.company_service.domain.repository.CompanyBulkRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static org.iimsa.company_service.domain.model.QCompany.company;

@Repository
@RequiredArgsConstructor
public class CompanyBulkRepositoryImpl implements CompanyBulkRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public long bulkUpdateHubInfo(Hub hub) {
        return queryFactory.update(company)
                .set(company.associate.hub.name, hub.getName())
                .set(company.associate.hub.address, hub.getAddress())
                .set(company.modifiedAt, LocalDateTime.now())
                .set(company.modifiedBy, "SYSTEM")
                .where(company.associate.hub.id.eq(hub.getId()))
                .execute();
    }

    @Override
    public long bulkUpdateCompanyManagerInfo(CompanyManager companyManager) {
        return queryFactory.update(company)
                .set(company.associate.companyManager.companyManagerName, companyManager.getCompanyManagerName())
                .set(company.modifiedAt, LocalDateTime.now())
                .set(company.modifiedBy, "SYSTEM")
                .where(company.associate.companyManager.companyManagerId.eq(companyManager.getCompanyManagerId()))
                .execute();
    }
}
