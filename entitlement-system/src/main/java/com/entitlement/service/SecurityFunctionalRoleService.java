package com.entitlement.service;

import com.entitlement.entity.SecurityFunctionalRole;
import com.entitlement.repository.SecurityFunctionalRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityFunctionalRoleService {
    private final SecurityFunctionalRoleRepository repository;

    public SecurityFunctionalRole save(SecurityFunctionalRole funcRole) {
        return repository.save(funcRole);
    }

    public List<SecurityFunctionalRole> findAll() {
        return repository.findAll();
    }

    public SecurityFunctionalRole findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
} 