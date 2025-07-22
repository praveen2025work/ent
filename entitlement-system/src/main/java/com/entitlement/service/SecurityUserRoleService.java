package com.entitlement.service;

import com.entitlement.entity.SecurityUserRole;
import com.entitlement.repository.SecurityUserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityUserRoleService {
    private final SecurityUserRoleRepository repository;

    public SecurityUserRole save(SecurityUserRole userRole) {
        return repository.save(userRole);
    }

    public List<SecurityUserRole> findAll() {
        return repository.findAll();
    }

    public SecurityUserRole findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
} 