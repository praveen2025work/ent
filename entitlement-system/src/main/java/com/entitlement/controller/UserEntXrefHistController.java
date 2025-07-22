package com.entitlement.controller;

import com.entitlement.entity.UserEntXrefHist;
import com.entitlement.service.UserEntXrefHistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-ent-xref-hist")
@RequiredArgsConstructor
public class UserEntXrefHistController {
    private final UserEntXrefHistService userEntXrefHistService;

    @GetMapping
    public List<UserEntXrefHist> getAll() {
        return userEntXrefHistService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntXrefHist> getById(@PathVariable Long id) {
        UserEntXrefHist hist = userEntXrefHistService.findById(id);
        return hist != null ? ResponseEntity.ok(hist) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public UserEntXrefHist create(@RequestBody UserEntXrefHist hist) {
        return userEntXrefHistService.save(hist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntXrefHist> update(@PathVariable Long id, @RequestBody UserEntXrefHist hist) {
        if (userEntXrefHistService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        hist.setUserEntitlementXrefHistId(id);
        return ResponseEntity.ok(userEntXrefHistService.save(hist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (userEntXrefHistService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        userEntXrefHistService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 