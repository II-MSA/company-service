package org.iimsa.company_service.domain.service;

import java.util.List;
import java.util.UUID;

public interface RoleCheck {
    boolean hasRole(String role);
    boolean hasRole(List<String> roles);
    boolean isMyHub(UUID hubId);
}
