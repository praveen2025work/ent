package com.entitlement.controller;

import com.entitlement.entity.FunctionalRoleXref;
import com.entitlement.service.FunctionalRoleXrefService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-xrefs")
@RequiredArgsConstructor
public class FunctionalRoleXrefController {
    private final FunctionalRoleXrefService functionalRoleXrefService;

    @GetMapping
    public List<FunctionalRoleXref> getAll() {
        return functionalRoleXrefService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FunctionalRoleXref> getById(@PathVariable Long id) {
        FunctionalRoleXref xref = functionalRoleXrefService.findById(id);
        return xref != null ? ResponseEntity.ok(xref) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public FunctionalRoleXref create(@RequestBody FunctionalRoleXref xref) {
        return functionalRoleXrefService.save(xref);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FunctionalRoleXref> update(@PathVariable Long id, @RequestBody FunctionalRoleXref xref) {
        if (functionalRoleXrefService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        xref.setFunctionalRoleXrefId(id);
        return ResponseEntity.ok(functionalRoleXrefService.save(xref));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (functionalRoleXrefService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        functionalRoleXrefService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/audit")
    public List<?> getAuditHistory(@PathVariable Long id) {
        return functionalRoleXrefService.getAuditHistory(id);
    }
} 