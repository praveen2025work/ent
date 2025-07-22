package com.entitlement;

import com.entitlement.repository.SecurityUserRoleRepository;
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
import com.entitlement.entity.SecurityUserRole;
import com.entitlement.entity.SecurityUser;
import com.entitlement.entity.SecurityRole;
import com.entitlement.entity.SecurityApplication;
import com.entitlement.repository.SecurityUserRepository;
import com.entitlement.repository.SecurityRoleRepository;
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
public class SecurityUserRoleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SecurityUserRoleRepository repository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SecurityUserRepository userRepository;
    @Autowired
    private SecurityRoleRepository roleRepository;
    @Autowired
    private SecurityApplicationRepository appRepository;

    @Test
    void testGetAllUserRoles() throws Exception {
        mockMvc.perform(get("/api/user-roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    void testCreateUserRoleWithValidFKs() throws Exception {
        String json = "{" +
                "\"user\": {\"userId\": 1}," +
                "\"role\": {\"roleId\": 1}," +
                "\"application\": {\"applicationId\": 1}," +
                "\"createdBy\": \"test\"}";
        mockMvc.perform(post("/api/user-roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.userId", is(1)));
    }

    @Test
    void testCreateUserRoleWithInvalidFKs() throws Exception {
        String json = "{" +
                "\"user\": {\"userId\": 9999}," +
                "\"role\": {\"roleId\": 9999}," +
                "\"application\": {\"applicationId\": 9999}," +
                "\"createdBy\": \"test\"}";
        mockMvc.perform(post("/api/user-roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testGetNonExistentUserRole() throws Exception {
        mockMvc.perform(get("/api/user-roles/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Rollback
    void testAuditRevisionsDirectly() {
        SecurityUser user = userRepository.save(SecurityUser.builder().username("audituserrole").build());
        SecurityRole role = roleRepository.save(SecurityRole.builder().roleName("AUDITUSERROLE").build());
        SecurityApplication app = appRepository.save(SecurityApplication.builder().code("AUDITURAPP").name("Audit UR App").build());
        SecurityUserRole ur = SecurityUserRole.builder().user(user).role(role).application(app).build();
        ur = repository.save(ur);
        Long id = ur.getUserRoleId();
        ur.setCreatedBy("updated");
        repository.save(ur);
        repository.deleteById(id);
        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<Number> revs = reader.getRevisions(SecurityUserRole.class, id);
        org.junit.jupiter.api.Assertions.assertEquals(3, revs.size());
        List<Object[]> history = reader.createQuery().forRevisionsOfEntity(SecurityUserRole.class, false, true)
                .add(org.hibernate.envers.query.AuditEntity.id().eq(id)).getResultList();
        RevisionType[] expected = {RevisionType.ADD, RevisionType.MOD, RevisionType.DEL};
        for (int i = 0; i < history.size(); i++) {
            RevisionType revType = (RevisionType) history.get(i)[2];
            org.junit.jupiter.api.Assertions.assertEquals(expected[i], revType);
        }
        SecurityUserRole deleted = (SecurityUserRole) history.get(2)[0];
        org.junit.jupiter.api.Assertions.assertEquals("updated", deleted.getCreatedBy());
    }

    @Test
    void testBusinessRuleFKsRequired() throws Exception {
        String json = "{" +
                "\"createdBy\":\"test\"}";
        mockMvc.perform(post("/api/user-roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
} 