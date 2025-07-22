package com.entitlement.service;

import com.entitlement.entity.Group;
import com.entitlement.repository.GroupRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final EntityManager entityManager;

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Group findById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<?> getAuditHistory(Long id) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        return reader.createQuery().forRevisionsOfEntity(Group.class, false, true)
                .add(AuditReaderFactory.id().eq(id)).getResultList();
    }
} 