package com.entitlement.service;

import com.entitlement.entity.GroupMap;
import com.entitlement.repository.GroupMapRepository;
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
public class GroupMapService {
    private final GroupMapRepository groupMapRepository;
    private final EntityManager entityManager;

    public GroupMap save(GroupMap groupMap) {
        return groupMapRepository.save(groupMap);
    }

    public List<GroupMap> findAll() {
        return groupMapRepository.findAll();
    }

    public GroupMap findById(Long id) {
        return groupMapRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        groupMapRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<?> getAuditHistory(Long id) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        return reader.createQuery().forRevisionsOfEntity(GroupMap.class, false, true)
                .add(AuditEntity.id().eq(id)).getResultList();
    }
} 