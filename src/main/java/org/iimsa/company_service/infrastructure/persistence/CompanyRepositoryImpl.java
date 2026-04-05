package org.iimsa.company_service.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.domain.model.Company;
import org.iimsa.company_service.domain.repository.CompanyRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {
    private final JpaCompanyRepository jpaCompanyRepository;

    @Override
    public Company save(Company company) {
        return jpaCompanyRepository.save(company);
    }

    @Override
    public Optional<Company> findById(UUID companyId) {
        return jpaCompanyRepository.findById(companyId);
    }

    @Override
    public void deleteCompanyById(UUID companyId) {
        jpaCompanyRepository.deleteById(companyId);
    }

}
