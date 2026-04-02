package org.iimsa.company_service.presentation.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.application.service.CompanyService;
import org.iimsa.company_service.presentation.dto.request.CreateCompanyRequestDto;
import org.iimsa.company_service.presentation.dto.response.CreateCompanyResponseDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @PreAuthorize("hasAnyRole('MASTER','HUB_MANAGER')")
    @PostMapping
    public CreateCompanyResponseDto.Create createCompany(@RequestBody CreateCompanyRequestDto.Create requestDto) {
        UUID companyId = companyService.createCompany(requestDto.toDto());

        return new CreateCompanyResponseDto.Create(companyId);
    }

}
