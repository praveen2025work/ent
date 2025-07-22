package com.entitlement.controller;

import com.entitlement.entity.SecurityRole;
import com.entitlement.service.SecurityRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles2")
@RequiredArgsConstructor
public class SecurityRoleController {
    private final SecurityRoleService service;

    @GetMapping
    public List<SecurityRole> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityRole> getById(@PathVariable Long id) {
        SecurityRole role = service.findById(id);
        return role != null ? ResponseEntity.ok(role) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public SecurityRole create(@RequestBody SecurityRole role) {
        return service.save(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityRole> update(@PathVariable Long id, @RequestBody SecurityRole role) {
        if (service.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        role.setRoleId(id);
        return ResponseEntity.ok(service.save(role));
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