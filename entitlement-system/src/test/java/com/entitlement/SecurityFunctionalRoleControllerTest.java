package com.entitlement;

import com.entitlement.repository.SecurityFunctionalRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.entitlement.entity.SecurityFunctionalRole;
import com.entitlement.entity.SecurityApplication;
import com.entitlement.repository.SecurityApplicationRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.springframework.test.annotation.Rollback;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityFunctionalRoleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SecurityFunctionalRoleRepository repository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SecurityApplicationRepository appRepository;

    @Test
    void testGetAllFunctionalRoles() throws Exception {
        mockMvc.perform(get("/api/functional-roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    void testCreateFunctionalRoleWithValidFK() throws Exception {
        String json = "{" +
                "\"application\": {\"applicationId\": 1}," +
                "\"entityType\": \"TYPE2\", " +
                "\"createdBy\": \"test\"}";
        mockMvc.perform(post("/api/functional-roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.application.applicationId", is(1)));
    }

    @Test
    void testCreateFunctionalRoleWithInvalidFK() throws Exception {
        String json = "{" +
                "\"application\": {\"applicationId\": 9999}," +
                "\"entityType\": \"TYPE2\", " +
                "\"createdBy\": \"test\"}";
        mockMvc.perform(post("/api/functional-roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testGetNonExistentFunctionalRole() throws Exception {
        mockMvc.perform(get("/api/functional-roles/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Rollback
    void testAuditRevisionsDirectly() {
        SecurityApplication app = appRepository.save(SecurityApplication.builder().code("AUDITFUNCAPP").name("Audit Func App").build());
        SecurityFunctionalRole fr = SecurityFunctionalRole.builder().application(app).entityType("TYPEA").build();
        fr = repository.save(fr);
        Long id = fr.getFunctionalRoleId();
        fr.setEntityType("TYPEB");
        repository.save(fr);
        repository.deleteById(id);
        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<Number> revs = reader.getRevisions(SecurityFunctionalRole.class, id);
        org.junit.jupiter.api.Assertions.assertEquals(3, revs.size());
        List<Object[]> history = reader.createQuery().forRevisionsOfEntity(SecurityFunctionalRole.class, false, true)
                .add(org.hibernate.envers.query.AuditEntity.id().eq(id)).getResultList();
        RevisionType[] expected = {RevisionType.ADD, RevisionType.MOD, RevisionType.DEL};
        for (int i = 0; i < history.size(); i++) {
            RevisionType revType = (RevisionType) history.get(i)[2];
            org.junit.jupiter.api.Assertions.assertEquals(expected[i], revType);
        }
        SecurityFunctionalRole deleted = (SecurityFunctionalRole) history.get(2)[0];
        org.junit.jupiter.api.Assertions.assertEquals("TYPEB", deleted.getEntityType());
    }

    @Test
    void testBusinessRuleApplicationRequired() throws Exception {
        String json = "{" +
                "\"entityType\":\"TYPEB\"}";
        mockMvc.perform(post("/api/functional-roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
} 