package com.entitlement.service;

import com.entitlement.entity.SecurityUser;
import com.entitlement.repository.SecurityUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityUserService {
    private final SecurityUserRepository repository;

    public SecurityUser save(SecurityUser user) {
        return repository.save(user);
    }

    public List<SecurityUser> findAll() {
        return repository.findAll();
    }

    public SecurityUser findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
} 