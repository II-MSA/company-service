package org.iimsa.company_service.domain.event;

import org.iimsa.company_service.domain.model.Company;

public interface CompanyEvents {
    void updated(Company company);

    void deleted(Company company);
}
