package com.entitlement.service;

import com.entitlement.entity.SecurityRole;
import com.entitlement.repository.SecurityRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityRoleService {
    private final SecurityRoleRepository repository;

    public SecurityRole save(SecurityRole role) {
        return repository.save(role);
    }

    public List<SecurityRole> findAll() {
        return repository.findAll();
    }

    public SecurityRole findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
} 