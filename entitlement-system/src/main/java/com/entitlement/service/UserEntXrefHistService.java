package com.entitlement.service;

import com.entitlement.entity.UserEntXrefHist;
import com.entitlement.repository.UserEntXrefHistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserEntXrefHistService {
    private final UserEntXrefHistRepository userEntXrefHistRepository;

    public UserEntXrefHist save(UserEntXrefHist hist) {
        return userEntXrefHistRepository.save(hist);
    }

    public List<UserEntXrefHist> findAll() {
        return userEntXrefHistRepository.findAll();
    }

    public UserEntXrefHist findById(Long id) {
        return userEntXrefHistRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        userEntXrefHistRepository.deleteById(id);
    }
} 