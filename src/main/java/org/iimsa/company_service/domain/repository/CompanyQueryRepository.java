package org.iimsa.company_service.domain.repository;

import java.util.Optional;
import java.util.UUID;
import org.iimsa.company_service.domain.model.Company;
import org.iimsa.company_service.domain.query.CompanyQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyQueryRepository {
    Optional<Company> findById(CompanyId companyId);
    Page<Company> findAllByHubId(UUID hubId, Pageable pageable);
    Page<Company> findAllByCompanyManagerId(UUID companyManagerId, Pageable pageable);
    Page<Company> findAll(CompanyQueryDto .Search search, Pageable pageable);
}
