package com.entitlement.controller;

import com.entitlement.entity.SecurityUser;
import com.entitlement.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class SecurityUserController {
    private final SecurityUserService service;

    @GetMapping
    public List<SecurityUser> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityUser> getById(@PathVariable Long id) {
        SecurityUser user = service.findById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public SecurityUser create(@RequestBody SecurityUser user) {
        return service.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityUser> update(@PathVariable Long id, @RequestBody SecurityUser user) {
        if (service.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        user.setUserId(id);
        return ResponseEntity.ok(service.save(user));
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