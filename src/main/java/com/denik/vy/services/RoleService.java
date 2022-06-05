package com.denik.vy.services;

import com.denik.vy.models.Role;
import com.denik.vy.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public String roleWithoutPrefix(String role){
        return role.substring(5);
    }
    public List<Role> rolesWithoutPrefix(){
        return roleRepository.findAll().stream().map(w-> new Role(w.getId(), roleWithoutPrefix(w.getName()))).collect(Collectors.toList());
    }
//    public String rolesWithoutPrefixToString(List<Role> roles){
//        return roles.stream().map(w-> roleWithoutPrefix(w.getName())).collect(Collectors.joining(", "));
//    }

    public List<Role> roles(){
        return  roleRepository.findAll();
    }
}
