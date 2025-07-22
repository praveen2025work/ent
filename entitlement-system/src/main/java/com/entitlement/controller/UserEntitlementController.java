package com.entitlement.controller;

import com.entitlement.entity.UserEntitlementXref;
import com.entitlement.service.UserEntitlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-entitlements")
@RequiredArgsConstructor
public class UserEntitlementController {
    private final UserEntitlementService userEntitlementService;

    @GetMapping
    public List<UserEntitlementXref> getAll() {
        return userEntitlementService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntitlementXref> getById(@PathVariable Long id) {
        UserEntitlementXref ent = userEntitlementService.findById(id);
        return ent != null ? ResponseEntity.ok(ent) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public UserEntitlementXref create(@RequestBody UserEntitlementXref ent) {
        return userEntitlementService.save(ent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntitlementXref> update(@PathVariable Long id, @RequestBody UserEntitlementXref ent) {
        if (userEntitlementService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        ent.setUserEntitlementXrefId(id);
        return ResponseEntity.ok(userEntitlementService.save(ent));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (userEntitlementService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        userEntitlementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/audit")
    public List<?> getAuditHistory(@PathVariable Long id) {
        return userEntitlementService.getAuditHistory(id);
    }

    // Assign entitlement to user (example endpoint)
    @PostMapping("/assign")
    public ResponseEntity<String> assignEntitlement(@RequestParam String userId, @RequestParam Long entitlementId) {
        // Placeholder for assignment logic
        return ResponseEntity.ok("Entitlement " + entitlementId + " assigned to user " + userId);
    }
} 