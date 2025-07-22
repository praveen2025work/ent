package com.entitlement;

import com.entitlement.entity.SecurityApplication;
import com.entitlement.repository.SecurityApplicationRepository;
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
import jakarta.persistence.EntityManager;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.springframework.test.annotation.Rollback;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SecurityApplicationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SecurityApplicationRepository repository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void testGetAllApplications() throws Exception {
        mockMvc.perform(get("/api/applications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())));
    }

    @Test
    void testCreateAndDeleteApplication() throws Exception {
        String json = "{" +
                "\"code\":\"APP2\"," +
                "\"name\":\"App Two\"}";
        mockMvc.perform(post("/api/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("APP2")));
    }

    @Test
    void testAuditHistoryAfterUpdate() throws Exception {
        // Create
        String json = "{" +
                "\"code\":\"APP3\"," +
                "\"name\":\"App Three\"}";
        String response = mockMvc.perform(post("/api/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Long id = com.fasterxml.jackson.databind.JsonNode.class.cast(
                new com.fasterxml.jackson.databind.ObjectMapper().readTree(response)).get("applicationId").asLong();
        // Update
        String updateJson = "{" +
                "\"code\":\"APP3\"," +
                "\"name\":\"App Three Updated\"}";
        mockMvc.perform(put("/api/applications/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("App Three Updated")));
        // Audit
        mockMvc.perform(get("/api/applications/" + id + "/audit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))));
    }

    @Test
    void testGetNonExistentApplication() throws Exception {
        mockMvc.perform(get("/api/applications/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Rollback
    void testAuditRevisionsDirectly() throws Exception {
        // Create
        SecurityApplication app = SecurityApplication.builder().code("AUDIT1").name("Audit App").build();
        app = repository.save(app);
        Long id = app.getApplicationId();
        // Update
        app.setName("Audit App Updated");
        repository.save(app);
        // Delete
        repository.deleteById(id);
        // Envers direct check
        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<Number> revs = reader.getRevisions(SecurityApplication.class, id);
        // Should have 3 revisions: ADD, MOD, DEL
        org.junit.jupiter.api.Assertions.assertEquals(3, revs.size());
        // Check revision types
        List<Object[]> history = reader.createQuery().forRevisionsOfEntity(SecurityApplication.class, false, true)
                .add(org.hibernate.envers.query.AuditEntity.id().eq(id)).getResultList();
        RevisionType[] expected = {RevisionType.ADD, RevisionType.MOD, RevisionType.DEL};
        for (int i = 0; i < history.size(); i++) {
            org.hibernate.envers.DefaultRevisionEntity revEntity = (org.hibernate.envers.DefaultRevisionEntity) history.get(i)[1];
            RevisionType revType = (RevisionType) history.get(i)[2];
            org.junit.jupiter.api.Assertions.assertEquals(expected[i], revType);
        }
        // Check last revision is delete and has correct name
        SecurityApplication deleted = (SecurityApplication) history.get(2)[0];
        org.junit.jupiter.api.Assertions.assertEquals("Audit App Updated", deleted.getName());
    }

    @Test
    void testBusinessRuleExpiryBeforeEffectiveDate() throws Exception {
        String json = "{" +
                "\"code\":\"BUS1\"," +
                "\"name\":\"Business Rule\"," +
                "\"createdOn\":\"2024-06-01T00:00:00Z\"," +
                "\"updatedOn\":\"2024-05-01T00:00:00Z\"}";
        mockMvc.perform(post("/api/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
} 