package org.iimsa.company_service.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.iimsa.common.domain.BaseEntity;

@Entity
@Table(name = "p_company_manager")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyManager extends BaseEntity {

    @Id
    private UUID id;

    @Column(name = "company_manager_name", length = 50, nullable = false)
    private String companyManagerName;

    @OneToMany(mappedBy = "companyManager")
    @Builder.Default
    private List<Company> companies = new ArrayList<>();

    // softDelete
    public void softDelete(String userName) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = userName;
    }

}
