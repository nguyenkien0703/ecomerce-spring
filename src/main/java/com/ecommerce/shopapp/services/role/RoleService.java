package com.ecommerce.shopapp.services.role;

import com.ecommerce.shopapp.entity.Role;
import com.ecommerce.shopapp.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor

public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
