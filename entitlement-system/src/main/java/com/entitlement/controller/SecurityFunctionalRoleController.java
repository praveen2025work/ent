package com.entitlement.controller;

import com.entitlement.entity.SecurityFunctionalRole;
import com.entitlement.service.SecurityFunctionalRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/functional-roles")
@RequiredArgsConstructor
public class SecurityFunctionalRoleController {
    private final SecurityFunctionalRoleService service;

    @GetMapping
    public List<SecurityFunctionalRole> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityFunctionalRole> getById(@PathVariable Long id) {
        SecurityFunctionalRole funcRole = service.findById(id);
        return funcRole != null ? ResponseEntity.ok(funcRole) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public SecurityFunctionalRole create(@RequestBody SecurityFunctionalRole funcRole) {
        return service.save(funcRole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityFunctionalRole> update(@PathVariable Long id, @RequestBody SecurityFunctionalRole funcRole) {
        if (service.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        funcRole.setFunctionalRoleId(id);
        return ResponseEntity.ok(service.save(funcRole));
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