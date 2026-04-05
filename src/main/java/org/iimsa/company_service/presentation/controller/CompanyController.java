package org.iimsa.company_service.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.application.query.CompanyQueryService;
import org.iimsa.company_service.application.service.CompanyMasterService;
import org.iimsa.company_service.presentation.dto.request.CreateCompanyRequestDto;
import org.iimsa.company_service.presentation.dto.request.PatchCompanyRequestDto;
import org.iimsa.company_service.presentation.dto.request.SearchCompanyRequestDto;
import org.iimsa.company_service.presentation.dto.response.CreateCompanyResponseDto;
import org.iimsa.company_service.presentation.dto.response.GetCompanyResponseDto;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
@Tag(name = "업체 API", description = "업체 생성 및 관리, 조회 API")
public class CompanyController {

    private final CompanyMasterService companyMasterService;
    private final CompanyQueryService companyQueryService;

    // Create
    @Operation(summary = "업체 생성", description = "MASTER 사용자가 새로운 업체를 등록합니다.")
    @PreAuthorize("hasAnyRole('MASTER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCompanyResponseDto.Create createCompany(@RequestBody CreateCompanyRequestDto.Create requestDto) {
        UUID companyId = companyMasterService.createCompany(requestDto.toDto());

        return new CreateCompanyResponseDto.Create(companyId);
    }

    // Read
    @Operation(summary = "업체 단건 상세 조회", description = "ID를 통해 특정 업체의 상세 정보를 조회합니다.")
    @GetMapping("/{companyId}")
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
    @GetMapping("/companyManagers/{companyManagerId}")
    @PageableAsQueryParam
    public Page<GetCompanyResponseDto.Info> searchCompaniesByManager(
            @Parameter(description = "업체 담당자 ID", required = true) @PathVariable UUID companyManagerId,
            @Parameter(hidden = true) Pageable pageable) {
        return companyQueryService.searchCompaniesByCompanyManager(companyManagerId, pageable);
    }

    // Update
    @Operation(summary = "소속 허브 / 업체 매니저 변경", description = "소속된 허브와 지정된 업체 담당자(Company_Manager)를 변경합니다.")
    @PreAuthorize("hasRole('MASTER')")
    @PatchMapping("/{companyId}/associate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAssociate(
            @PathVariable UUID companyId, @RequestBody PatchCompanyRequestDto.ChangeAssociate request) {

        companyMasterService.changeAssociate(companyId, request.getHubId(), request.getCompanyManagerId());
    }

    @Operation(summary = "업체 타입 변경", description = "업체의 타입(SUPPLIER/RECEIVER)을 변경합니다.")
    @PreAuthorize("hasRole('MASTER')")
    @PatchMapping("/{companyId}/companyType")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeCompanyType(
            @PathVariable UUID companyId, @RequestBody PatchCompanyRequestDto.ChangeCompanyType request) {

        companyMasterService.changeCompanyType(companyId, request.getCompanyType());
    }

    @Operation(summary = "업체 주소 변경", description = "업체의 주소를 변경합니다.")
    @PreAuthorize("hasRole('MASTER')")
    @PatchMapping("/{companyId}/companyAddress")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAddress(
            @PathVariable UUID companyId, @RequestBody PatchCompanyRequestDto.ChangeCompanyAddress request) {

        companyMasterService.changeAddress(companyId, request.getAddress());
    }

    // Delete
    @PreAuthorize("hasRole('MASTER')")
    @Operation(summary = "업체 삭제", description = "등록된 업체를 삭제합니다.")
    @DeleteMapping("/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID companyId) {
        companyMasterService.deleteCompany(companyId);
    }

}
