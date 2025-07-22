package com.entitlement.service;

import com.entitlement.entity.SecurityApplication;
import com.entitlement.repository.SecurityApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityApplicationService {
    private final SecurityApplicationRepository repository;

    public SecurityApplication save(SecurityApplication app) {
        return repository.save(app);
    }

    public List<SecurityApplication> findAll() {
        return repository.findAll();
    }

    public SecurityApplication findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
} 