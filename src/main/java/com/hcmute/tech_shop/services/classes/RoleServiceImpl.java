package com.hcmute.tech_shop.services.classes;

import com.hcmute.tech_shop.entities.Role;
import com.hcmute.tech_shop.repositories.RoleRepository;
import com.hcmute.tech_shop.services.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Role not found"));
    }

    @Override
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }
}
