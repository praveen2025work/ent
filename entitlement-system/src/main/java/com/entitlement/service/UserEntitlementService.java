package com.entitlement.service;

import com.entitlement.entity.UserEntitlementXref;
import com.entitlement.repository.UserEntitlementXrefRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserEntitlementService {
    private final UserEntitlementXrefRepository userEntitlementXrefRepository;
    private final EntityManager entityManager;

    public UserEntitlementXref save(UserEntitlementXref ent) {
        return userEntitlementXrefRepository.save(ent);
    }

    public List<UserEntitlementXref> findAll() {
        return userEntitlementXrefRepository.findAll();
    }

    public UserEntitlementXref findById(Long id) {
        return userEntitlementXrefRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        userEntitlementXrefRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<?> getAuditHistory(Long id) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        return reader.createQuery().forRevisionsOfEntity(UserEntitlementXref.class, false, true)
                .add(AuditReaderFactory.id().eq(id)).getResultList();
    }
} 