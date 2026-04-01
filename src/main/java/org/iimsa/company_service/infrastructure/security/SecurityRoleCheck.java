package org.iimsa.company_service.infrastructure.security;

import org.iimsa.company_service.domain.service.RoleCheck;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class SecurityRoleCheck implements RoleCheck {
    @Override
    public boolean hasRole(String role) {
        return false;
    }

    @Override
    public boolean hasRole(List<String> roles) {
        return false;
    }

    @Override
    public boolean isMyHub(UUID hubId) {
        return false;
    }
}
