package org.iimsa.company_service.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.iimsa.common.domain.BaseEntity;
import org.iimsa.common.exception.BadRequestException;
import org.iimsa.common.exception.ForbiddenException;
import org.iimsa.company_service.domain.service.*;
import org.springframework.util.StringUtils;

import java.util.UUID;

import static org.iimsa.company_service.domain.model.UserType.MASTER;

@Entity
@Table(name = "p_company")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;

    @Column(name = "company_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    @Embedded
    private Associate associate;

    @Column(name = "address", columnDefinition = "TEXT", nullable = false)
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
            UUID companyManagerId, CompanyManagerProvider companyManagerProvider) {

        if (!StringUtils.hasText(companyName)) {
            throw new BadRequestException("업체명은 필수입력 값 입니다.");
        }
        if (companyType == null) {
            throw new BadRequestException("업체 타입은 필수입력 값 입니다.");
        }
        if (!StringUtils.hasText(address)) {
            throw new BadRequestException("주소는 필수입력 값 입니다.");
        }

        this.companyName = companyName;
        this.companyType = companyType;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;

        this.associate = new Associate(hubId, companyManagerId, hubProvider, companyManagerProvider);
    }

    // update
    public void changeAssociate(UUID hubId, UUID companyManagerId,
                                RoleCheck roleCheck, HubProvider hubInfo, CompanyManagerProvider companyManagerInfo) {

        checkMaster(roleCheck);
        this.associate = new Associate(hubId, companyManagerId, hubInfo, companyManagerInfo);

    }

    public void changeCompanyType(CompanyType companyType, RoleCheck roleCheck) {

        checkMaster(roleCheck);

        if (companyType == null) {
            throw new BadRequestException("업체 타입은 필수입력 값 입니다.");
        }

        this.companyType = companyType;
    }

    public void changeAddress(String address, AddressResolver resolver, RoleCheck roleCheck) {

        checkMaster(roleCheck);

        if (!StringUtils.hasText(address)) {
            throw new BadRequestException("주소는 필수입력 값 입니다.");
        }

        Coordinates coordinates = resolver.resolve(address);
        if (coordinates == null) {
            throw new BadRequestException("유효하지 않은 주소입니다: " + address);
        }

        this.address = address;
        this.latitude = coordinates.latitude(); // 위도
        this.longitude = coordinates.longitude(); // 경도
    }

    // User 코드처럼 권한 체크 메서드 유지
    private void checkMasterId(String masterId) {
        if (!StringUtils.hasText(masterId)) {
            throw new BadRequestException("관리자 아이디가 누락되었습니다.");
        }
    }

    // softDelete
    public void delete(String userName, RoleCheck roleCheck) {

        checkUserName(userName);
        checkMaster(roleCheck);

        super.delete(userName);
    }

    // check username
    private void checkUserName(String userName) {
        if (!StringUtils.hasText(userName)) {
            throw new BadRequestException("요청 사용자 이름이 누락되었습니다.");
        }
    }

    // check isMaster
    private void checkMaster(RoleCheck roleCheck) {
        if (!roleCheck.hasRole(MASTER)) {
            throw new ForbiddenException(MASTER.getDescription() + " 권한이 필요합니다.");
        }
    }

}
