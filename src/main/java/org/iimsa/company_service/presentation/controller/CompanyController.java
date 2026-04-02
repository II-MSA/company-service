package org.iimsa.company_service.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.application.query.CompanyQueryService;
import org.iimsa.company_service.application.service.CompanyService;
import org.iimsa.company_service.presentation.dto.request.CreateCompanyRequestDto;
import org.iimsa.company_service.presentation.dto.request.SearchCompanyRequestDto;
import org.iimsa.company_service.presentation.dto.response.CreateCompanyResponseDto;
import org.iimsa.company_service.presentation.dto.response.GetCompanyResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
@Tag(name = "업체 API", description = "업체 생성 및 관리, 조회 API")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyQueryService companyQueryService;

    @Operation(summary = "업체 생성", description = "MASTER 사용자가 새로운 업체를 등록합니다.")
    @PreAuthorize("hasAnyRole('MASTER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCompanyResponseDto.Create createCompany(@RequestBody CreateCompanyRequestDto.Create requestDto) {
        UUID companyId = companyService.createCompany(requestDto.toDto());

        return new CreateCompanyResponseDto.Create(companyId);
    }

    @Operation(summary = "업체 단건 상세 조회", description = "ID를 통해 특정 업체의 상세 정보를 조회합니다.")
    @GetMapping("/{companyId}/details")
    public GetCompanyResponseDto.Info getCompany(
            @Parameter(description = "조회할 업체 ID", required = true) @PathVariable UUID companyId) {
        return companyQueryService.getCompany(companyId);
    }

    @Operation(summary = "전체 업체 목록 조건 검색", description = "다양한 검색 조건(업체명, 허브명 등)과 페이징을 통해 업체 목록을 조회합니다. (페이지 크기 10/30/50 제한)")
    @GetMapping
    @PageableAsQueryParam
    public Page<GetCompanyResponseDto.Info> searchCompanies(
            @ParameterObject SearchCompanyRequestDto.Search request,
            @Parameter(hidden = true) Pageable pageable) {
        return companyQueryService.searchCompanies(request.toDomainDto(), pageable);
    }

    @Operation(summary = "특정 허브 소속 업체 목록 조회", description = "지정된 허브 ID에 소속된 업체들의 목록을 페이징하여 조회합니다.")
    @GetMapping("/hubs/{hubId}")
    @PageableAsQueryParam
    public Page<GetCompanyResponseDto.Info> searchCompaniesByHub(
            @Parameter(description = "허브 ID", required = true) @PathVariable UUID hubId,
            @Parameter(hidden = true) Pageable pageable) {
        return companyQueryService.searchCompaniesByHub(hubId, pageable);
    }

    @Operation(summary = "특정 담당자가 관리하는 업체 목록 조회", description = "지정된 업체 담당자(Manager) ID가 관리하는 업체들의 목록을 조회합니다.")
    @GetMapping("/managers/{companyManagerId}")
    @PageableAsQueryParam
    public Page<GetCompanyResponseDto.Info> searchCompaniesByManager(
            @Parameter(description = "업체 담당자 ID", required = true) @PathVariable UUID companyManagerId,
            @Parameter(hidden = true) Pageable pageable) {
        return companyQueryService.searchCompaniesByCompanyManager(companyManagerId, pageable);
    }
}
