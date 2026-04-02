package org.iimsa.company_service.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.application.dto.CompanyServiceDto;
import org.iimsa.company_service.domain.model.Company;
import org.iimsa.company_service.domain.repository.CompanyRepository;
import org.iimsa.company_service.domain.service.CompanyManagerProvider;
import org.iimsa.company_service.domain.service.HubProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final HubProvider hubProvider;
    private final CompanyManagerProvider companyManagerProvider;
    private final CompanyRepository companyRepository;

    @Transactional
    public UUID createCompany(CompanyServiceDto.Create data) {

        Company company = Company.builder()
                .companyName(data.getName())
                .companyType(data.getCompanyType())
                .address(data.getAddress())
                .latitude(data.getLatitude())
                .longitude(data.getLongitude())
                .hubId(data.getHubId())
                .hubProvider(hubProvider)
                .companyManagerId(data.getCompanyManagerId())
                .type(data.getUserType())
                .companyManagerProvider(companyManagerProvider)
                .build();

        return companyRepository.save(company).getId();

    }
}
