package org.iimsa.company_service.application.query;

import lombok.RequiredArgsConstructor;
import org.iimsa.company_service.domain.exception.CompanyNotFoundException;
import org.iimsa.company_service.domain.query.CompanyQueryDto;
import org.iimsa.company_service.domain.repository.CompanyId;
import org.iimsa.company_service.domain.repository.CompanyQueryRepository;
import org.iimsa.company_service.presentation.dto.response.GetCompanyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyQueryService {

    private final CompanyQueryRepository companyQueryRepository;

    // 1. 업체 단건 조회
    public GetCompanyResponseDto.Info getCompany(UUID companyId) {
        return companyQueryRepository.findById(CompanyId.of(companyId))
                .map(GetCompanyResponseDto.Info::from)
                .orElseThrow(() -> new CompanyNotFoundException(companyId));
    }

    // 2. 조건별 업체 목록 통합 검색 (Search)
    public Page<GetCompanyResponseDto.Info> searchCompanies(CompanyQueryDto.Search search, Pageable pageable) {
        Pageable validatedPageable = validatePageable(pageable);

        return companyQueryRepository.findAll(search, validatedPageable)
                .map(GetCompanyResponseDto.Info::from);
    }

    // 3. 특정 허브별 업체 목록 조회
    public Page<GetCompanyResponseDto.Info> searchCompaniesByHub(UUID hubId, Pageable pageable) {
        Pageable validatedPageable = validatePageable(pageable);

        return companyQueryRepository.findAllByHubId(hubId, validatedPageable)
                .map(GetCompanyResponseDto.Info::from);
    }

    // 4. 특정 담당자별 업체 목록 조회
    public Page<GetCompanyResponseDto.Info> searchCompaniesByCompanyManager(UUID companyManagerId, Pageable pageable) {
        Pageable validatedPageable = validatePageable(pageable);

        return companyQueryRepository.findAllByCompanyManagerId(companyManagerId, validatedPageable)
                .map(GetCompanyResponseDto.Info::from);
    }

    private Pageable validatePageable(Pageable pageable) {
        int pageSize = pageable.getPageSize();

        if (pageSize != 10 && pageSize != 30 && pageSize != 50) {
            return PageRequest.of(pageable.getPageNumber(), 10, pageable.getSort());
        }

        return pageable;
    }
}
