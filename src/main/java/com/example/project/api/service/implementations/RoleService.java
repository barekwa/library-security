package com.example.project.api.service.implementations;

import com.example.project.model.Role;
import com.example.project.api.repository.RoleRepository;
import com.example.project.api.service.interfaces.RoleServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements RoleServiceAPI {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role save(Role role){
        return roleRepository.save(role);
    }

}
