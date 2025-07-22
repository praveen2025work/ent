package com.entitlement;

import com.entitlement.repository.SecurityRoleRepository;
import com.entitlement.entity.SecurityRole;
import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityRoleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SecurityRoleRepository repository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void testGetAllRoles() throws Exception {
        mockMvc.perform(get("/api/roles2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    void testCreateRole() throws Exception {
        String json = "{" +
                "\"roleName\":\"USER\"}";
        mockMvc.perform(post("/api/roles2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName", is("USER")));
    }

    @Test
    @Rollback
    void testAuditRevisionsDirectly() {
        SecurityRole role = SecurityRole.builder().roleName("AUDITROLE").build();
        role = repository.save(role);
        Long id = role.getRoleId();
        role.setRoleName("AUDITROLE2");
        repository.save(role);
        repository.deleteById(id);
        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<Number> revs = reader.getRevisions(SecurityRole.class, id);
        org.junit.jupiter.api.Assertions.assertEquals(3, revs.size());
        List<Object[]> history = reader.createQuery().forRevisionsOfEntity(SecurityRole.class, false, true)
                .add(org.hibernate.envers.query.AuditEntity.id().eq(id)).getResultList();
        RevisionType[] expected = {RevisionType.ADD, RevisionType.MOD, RevisionType.DEL};
        for (int i = 0; i < history.size(); i++) {
            RevisionType revType = (RevisionType) history.get(i)[2];
            org.junit.jupiter.api.Assertions.assertEquals(expected[i], revType);
        }
        SecurityRole deleted = (SecurityRole) history.get(2)[0];
        org.junit.jupiter.api.Assertions.assertEquals("AUDITROLE2", deleted.getRoleName());
    }

    @Test
    void testBusinessRuleRoleNameRequired() throws Exception {
        String json = "{" +
                "\"department\":\"IT\"}";
        mockMvc.perform(post("/api/roles2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
} 