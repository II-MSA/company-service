package org.iimsa.company_service.presentation.controller;

import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.application.service.CompanyService;
import org.iimsa.company_service.presentation.dto.request.CreateCompanyRequestDto;
import org.iimsa.company_service.presentation.dto.response.CreateCompanyResponseDto;
import org.iimsa.config.security.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    @PreAuthorize("hasAnyRole('MASTER','HUB_MANAGER')")
    @PostMapping
    public ResponseEntity<CreateCompanyResponseDto> save(@RequestBody CreateCompanyRequestDto requestDto,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        UUID newCompanyId = companyService.createCompany(requestDto, userDetails);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{companyId}")
                .buildAndExpand(newCompanyId)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
