package com.ecommerce.shopapp.services.impl;

import com.ecommerce.shopapp.entity.Role;
import com.ecommerce.shopapp.repositories.RoleRepository;
import com.ecommerce.shopapp.services.IRoleService;
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
