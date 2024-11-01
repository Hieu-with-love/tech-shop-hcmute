package com.hcmute.tech_shop.services.interfaces;

import com.hcmute.tech_shop.dtos.requests.RoleRequest;
import com.hcmute.tech_shop.entities.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(Long id);
    Role getRoleByName(String name);
    List<Role> getAllRoles();
    Role createRole(RoleRequest roleRequest);
    Role updateRole(Long id, RoleRequest roleRequest);
    void deleteRole(Long id);
}
