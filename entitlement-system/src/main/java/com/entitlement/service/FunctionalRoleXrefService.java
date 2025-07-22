package com.entitlement.service;

import com.entitlement.entity.FunctionalRoleXref;
import com.entitlement.repository.FunctionalRoleXrefRepository;
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
public class FunctionalRoleXrefService {
    private final FunctionalRoleXrefRepository functionalRoleXrefRepository;
    private final EntityManager entityManager;

    public FunctionalRoleXref save(FunctionalRoleXref xref) {
        return functionalRoleXrefRepository.save(xref);
    }

    public List<FunctionalRoleXref> findAll() {
        return functionalRoleXrefRepository.findAll();
    }

    public FunctionalRoleXref findById(Long id) {
        return functionalRoleXrefRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        functionalRoleXrefRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<?> getAuditHistory(Long id) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        return reader.createQuery().forRevisionsOfEntity(FunctionalRoleXref.class, false, true)
                .add(AuditEntity.id().eq(id)).getResultList();
    }
} 