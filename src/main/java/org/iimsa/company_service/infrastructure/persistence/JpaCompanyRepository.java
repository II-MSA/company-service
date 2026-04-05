package org.iimsa.company_service.infrastructure.persistence;

import java.util.UUID;
import org.iimsa.company_service.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompanyRepository extends JpaRepository<Company, UUID> {
}
