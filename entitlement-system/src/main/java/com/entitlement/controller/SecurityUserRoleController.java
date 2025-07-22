package com.entitlement.controller;

import com.entitlement.entity.SecurityUserRole;
import com.entitlement.service.SecurityUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
@RequiredArgsConstructor
public class SecurityUserRoleController {
    private final SecurityUserRoleService service;

    @GetMapping
    public List<SecurityUserRole> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityUserRole> getById(@PathVariable Long id) {
        SecurityUserRole userRole = service.findById(id);
        return userRole != null ? ResponseEntity.ok(userRole) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public SecurityUserRole create(@RequestBody SecurityUserRole userRole) {
        return service.save(userRole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityUserRole> update(@PathVariable Long id, @RequestBody SecurityUserRole userRole) {
        if (service.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        userRole.setUserRoleId(id);
        return ResponseEntity.ok(service.save(userRole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 