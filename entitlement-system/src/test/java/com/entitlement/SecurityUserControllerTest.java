package com.entitlement;

import com.entitlement.repository.SecurityUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.entitlement.entity.SecurityUser;
import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.springframework.test.annotation.Rollback;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SecurityUserRepository repository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    void testCreateUser() throws Exception {
        String json = "{" +
                "\"username\":\"user2\"," +
                "\"email\":\"user2@email.com\"}";
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("user2")));
    }

    @Test
    void testCreateUserMissingUsername() throws Exception {
        String json = "{" +
                "\"email\":\"nousername@email.com\"}";
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateUserDuplicateUsername() throws Exception {
        String json = "{" +
                "\"username\":\"user1\"," +
                "\"email\":\"dup@email.com\"}";
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testGetNonExistentUser() throws Exception {
        mockMvc.perform(get("/api/users/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Rollback
    void testAuditRevisionsDirectly() {
        SecurityUser user = SecurityUser.builder().username("audituser").email("audit@user.com").build();
        user = repository.save(user);
        Long id = user.getUserId();
        user.setEmail("audit2@user.com");
        repository.save(user);
        repository.deleteById(id);
        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<Number> revs = reader.getRevisions(SecurityUser.class, id);
        org.junit.jupiter.api.Assertions.assertEquals(3, revs.size());
        List<Object[]> history = reader.createQuery().forRevisionsOfEntity(SecurityUser.class, false, true)
                .add(org.hibernate.envers.query.AuditEntity.id().eq(id)).getResultList();
        RevisionType[] expected = {RevisionType.ADD, RevisionType.MOD, RevisionType.DEL};
        for (int i = 0; i < history.size(); i++) {
            RevisionType revType = (RevisionType) history.get(i)[2];
            org.junit.jupiter.api.Assertions.assertEquals(expected[i], revType);
        }
        SecurityUser deleted = (SecurityUser) history.get(2)[0];
        org.junit.jupiter.api.Assertions.assertEquals("audit2@user.com", deleted.getEmail());
    }

    @Test
    void testBusinessRuleUsernameRequired() throws Exception {
        String json = "{" +
                "\"email\":\"nousername@email.com\"}";
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
} 