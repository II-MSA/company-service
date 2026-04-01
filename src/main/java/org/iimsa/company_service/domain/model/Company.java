package org.iimsa.company_service.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.iimsa.common.domain.BaseEntity;

import org.iimsa.company_service.domain.service.HubProvider;
import org.iimsa.company_service.domain.service.CompanyManagerProvider;
import org.iimsa.company_service.domain.service.RoleCheck;

@Entity
@Table(name = "p_company")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type", nullable = false)
    private CompanyType companyType;

    @Embedded
    private Associate associate;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Builder
    public Company(
            String companyName, CompanyType companyType,
            String address, Double latitude, Double longitude,
            UUID hubId, HubProvider hubProvider,
            UUID companyManagerId, CompanyManagerProvider companyManagerProvider,
            RoleCheck roleCheck) {

        checkAuthority(roleCheck);

        this.companyName = companyName;
        this.companyType = companyType;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.associate = new Associate(hubId, companyManagerId, hubProvider, companyManagerProvider);
    }

    // create
    public static Company create(
            String companyName,
            UUID hubId, UUID companyManagerId,
            String address, Double latitude, Double longitude,
            HubProvider hubProvider, CompanyManagerProvider companyManagerProvider,
            RoleCheck roleCheck
    ) {
        if (roleCheck.hasRole("MASTER")) { // MASTER 관리자는 권한 체크 필요 없음
            return Company.builder()
                    .companyName(companyName)
                    .associate(new Associate(hubId, companyManagerId, hubProvider, companyManagerProvider))
                    .address(address)
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
        }

        if (roleCheck.hasRole("HUB_MANAGER")) {
            return Company.builder()
                    .companyName(companyName)
                    .associate(new Associate(hubId, companyManagerId, hubProvider, companyManagerProvider))
                    .address(address)
                    .latitude(latitude)
                    .longitude(longitude)
                    .build();
        }

        return null;
    }

    // patch
    public void updateCompanyInfo(
            String companyName,
            CompanyType companyType,
            String address,
            Double latitude,
            Double longitude
    ) {
        if (companyName != null) this.companyName = companyName;
        if (companyType != null) this.companyType = companyType;
        if (address != null) this.address = address;
        if (latitude != null) this.latitude = latitude;
        if (longitude != null) this.longitude = longitude;
    }

    // softDelete
    public void softDelete(String userName) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = userName;
    }

    // checkRole
    private void checkAuthority(RoleCheck roleCheck) {
        if (roleCheck.hasRole("MASTER")) { // MASTER 관리자는 권한 체크 필요 없음
            return;
        }

        if (!(roleCheck.hasRole("HUB_MANAGER") && roleCheck.isMyHub(this.associate.getHub().getId()))) {
            throw new RuntimeException("처리할 권한이 없습니다.");
        }
    }

    // 허브나 업체 매니저가 변경되는 경우
    public void changeAffiliation(
            UUID newHubId,
            UUID newCompanyManagerId,
            HubProvider hubProvider,
            CompanyManagerProvider companyManagerProvider,
            RoleCheck roleCheck
    ) {
        checkAuthority(roleCheck);
        this.associate = new Associate(newHubId, newCompanyManagerId, hubProvider, companyManagerProvider);
    }

}
