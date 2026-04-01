package org.iimsa.company_service.domain.event;

import org.iimsa.company_service.domain.model.Company;

public interface CompanyEvents {
    void created(Company company);
    void updated(Company company);
    void deleted(Company company);
}
