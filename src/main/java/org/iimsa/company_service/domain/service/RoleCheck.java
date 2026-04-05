package org.iimsa.company_service.domain.service;

import java.util.List;
import org.iimsa.company_service.domain.model.UserType;

public interface RoleCheck {
    boolean hasRole(UserType type);
    boolean hasRole(List<UserType> types);
}
