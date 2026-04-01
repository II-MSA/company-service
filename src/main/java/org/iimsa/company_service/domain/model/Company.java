package org.iimsa.company_service.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.iimsa.common.domain.BaseEntity;

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

    @Column(name = "hub_id")
    private UUID hubId;

    @Column(name = "hub_name", columnDefinition = "TEXT")
    private String hubName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_manager_id")
    private CompanyManager companyManager; // UUID

    @Column(name = "company_manager_name", length = 50)
    private String companyManagerName;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    // 기존 매니저를 다른 사람으로 변경
    public void changeManager(CompanyManager manager) {
        this.companyManager = manager;

        if(manager != null) {
            this.companyManagerName = manager.getCompanyManagerName();
        }
    }

    // 기존 매니저가 이름을 바꾸는 경우 (매니저 UUID는 동일)
    public void syncManagerName(String newManagerName) {
        this.companyManagerName = newManagerName;
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

}
