package com.entitlement.controller;

import com.entitlement.entity.SecurityApplication;
import com.entitlement.service.SecurityApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class SecurityApplicationController {
    private final SecurityApplicationService service;

    @GetMapping
    public List<SecurityApplication> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecurityApplication> getById(@PathVariable Long id) {
        SecurityApplication app = service.findById(id);
        return app != null ? ResponseEntity.ok(app) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public SecurityApplication create(@RequestBody SecurityApplication app) {
        return service.save(app);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecurityApplication> update(@PathVariable Long id, @RequestBody SecurityApplication app) {
        if (service.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        app.setApplicationId(id);
        return ResponseEntity.ok(service.save(app));
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