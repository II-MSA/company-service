package org.iimsa.company_service.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.domain.model.Company;
import org.iimsa.company_service.domain.repository.CompanyRepository;
import org.iimsa.company_service.domain.service.CompanyManagerProvider;
import org.iimsa.company_service.domain.service.HubProvider;
import org.iimsa.company_service.domain.service.RoleCheck;
import org.iimsa.company_service.presentation.dto.request.CreateCompanyRequestDto;
import org.iimsa.config.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final HubProvider hubProvider;
    private final CompanyManagerProvider companyManagerProvider;
    private final RoleCheck roleCheck;

    @Transactional
    public UUID createCompany(CreateCompanyRequestDto request, UserDetailsImpl userDetails) {

        Company company = Company.create(
                request.getCompanyName(),
                request.getCompanyType(),
                request.getHubId(),
                request.getCompanyManagerId(),
                request.getAddress(),
                request.getLatitude(),
                request.getLongitude(),
                hubProvider,
                companyManagerProvider,
                roleCheck
        );

        Company result = companyRepository.save(company);

        return result.getId();
    }
}
