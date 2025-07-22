package com.entitlement.controller;

import com.entitlement.entity.FunctionalRole;
import com.entitlement.service.FunctionalRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class FunctionalRoleController {
    private final FunctionalRoleService functionalRoleService;

    @GetMapping
    public List<FunctionalRole> getAll() {
        return functionalRoleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FunctionalRole> getById(@PathVariable Long id) {
        FunctionalRole role = functionalRoleService.findById(id);
        return role != null ? ResponseEntity.ok(role) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public FunctionalRole create(@RequestBody FunctionalRole role) {
        return functionalRoleService.save(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FunctionalRole> update(@PathVariable Long id, @RequestBody FunctionalRole role) {
        if (functionalRoleService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        role.setFunctionalRoleId(id);
        return ResponseEntity.ok(functionalRoleService.save(role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (functionalRoleService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        functionalRoleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/audit")
    public List<?> getAuditHistory(@PathVariable Long id) {
        return functionalRoleService.getAuditHistory(id);
    }

    // Onboard user to app (example endpoint)
    @PostMapping("/onboard")
    public ResponseEntity<String> onboardUserToApp(@RequestParam String userId, @RequestParam String appCode) {
        // Placeholder for onboarding logic
        return ResponseEntity.ok("User " + userId + " onboarded to app " + appCode);
    }
} 