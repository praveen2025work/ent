package com.entitlement.service;

import com.entitlement.entity.FunctionalRole;
import com.entitlement.repository.FunctionalRoleRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FunctionalRoleService {
    private final FunctionalRoleRepository functionalRoleRepository;
    private final EntityManager entityManager;

    public FunctionalRole save(FunctionalRole role) {
        return functionalRoleRepository.save(role);
    }

    public List<FunctionalRole> findAll() {
        return functionalRoleRepository.findAll();
    }

    public FunctionalRole findById(Long id) {
        return functionalRoleRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        functionalRoleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<?> getAuditHistory(Long id) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        return reader.createQuery().forRevisionsOfEntity(FunctionalRole.class, false, true)
                .add(AuditEntity.id().eq(id)).getResultList();
    }
} 