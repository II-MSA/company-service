package org.iimsa.company_service.application.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iimsa.common.exception.UnAuthorizedException;
import org.iimsa.common.util.SecurityUtil;
import org.iimsa.company_service.application.dto.CompanyServiceDto;
import org.iimsa.company_service.domain.model.Company;
import org.iimsa.company_service.domain.model.CompanyManager;
import org.iimsa.company_service.domain.model.CompanyType;
import org.iimsa.company_service.domain.model.Hub;
import org.iimsa.company_service.domain.repository.CompanyBulkRepository;
import org.iimsa.company_service.domain.repository.CompanyRepository;
import org.iimsa.company_service.domain.service.AddressResolver;
import org.iimsa.company_service.domain.service.CompanyManagerProvider;
import org.iimsa.company_service.domain.service.HubProvider;
import org.iimsa.company_service.domain.service.RoleCheck;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
// All Services requiring MASTER role
public class CompanyMasterService {

    private final HubProvider hubProvider;
    private final CompanyManagerProvider companyManagerProvider;
    private final CompanyRepository companyRepository;
    private final CompanyBulkRepository companyBulkRepository;
    private final AddressResolver addressResolver;
    private final RoleCheck roleCheck;
    private final EntityManager em;

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
                .companyManagerProvider(companyManagerProvider)
                .build();

        return companyRepository.save(company).getId();
    }

    private Company getCompany(UUID companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 업체를 찾을 수 없습니다."));
    }

    @Transactional
    public void changeAssociate(UUID companyId, UUID hubId, UUID companyManagerId) {

        Company company = getCompany(companyId);
        company.changeAssociate(hubId, companyManagerId,
                roleCheck, hubProvider, companyManagerProvider);
    }

    @Transactional
    public void changeCompanyType(UUID companyId, CompanyType companyType) {
        Company company = getCompany(companyId);
        company.changeCompanyType(companyType, roleCheck);
    }

    @Transactional
    public void changeAddress(UUID companyId, String address) {
        Company company = getCompany(companyId);
        company.changeAddress(address, addressResolver, roleCheck);
    }

    @Transactional
    public void deleteCompany(UUID companyId) {
        Company company = getCompany(companyId);
        company.delete(getMasterId(), roleCheck);
    }

    private String getMasterId() {
        return SecurityUtil.getCurrentUsername()
                .orElseThrow(() -> new UnAuthorizedException("관리자 인증 정보가 유효하지 않습니다."));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void syncHubInfo(Hub hub) {
        long updatedCount = companyBulkRepository.bulkUpdateHubInfo(hub);
        em.clear();

        log.info("[BulkUpdate] Hub ID: {} - {} companies updated", hub.getId(), updatedCount);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void syncCompanyManagerInfo(CompanyManager companyManager) {
        long updatedCount = companyBulkRepository.bulkUpdateCompanyManagerInfo(companyManager);
        em.clear();

        log.info("[BulkUpdate] CompanyManager ID: {} - {} companies updated", companyManager.getCompanyManagerId(), updatedCount);
    }


}
