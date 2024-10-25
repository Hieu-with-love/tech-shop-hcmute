package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.entities.Role;

public interface RoleService {
    Role getRoleById(Long id);
    Role createRole(Role role);
}
