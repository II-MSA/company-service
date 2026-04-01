package org.iimsa.company_service.domain.repository;

import java.util.Optional;
import java.util.UUID;
import org.iimsa.company_service.domain.model.Company;

public interface CompanyRepository {
    Company save(Company company);
    Optional<Company> findById(UUID companyId);
    void deleteCompanyById(UUID companyId);
}
