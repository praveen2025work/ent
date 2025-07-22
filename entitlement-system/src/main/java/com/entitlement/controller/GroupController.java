package com.entitlement.controller;

import com.entitlement.entity.Group;
import com.entitlement.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    public List<Group> getAll() {
        return groupService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getById(@PathVariable Long id) {
        Group group = groupService.findById(id);
        return group != null ? ResponseEntity.ok(group) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Group create(@RequestBody Group group) {
        return groupService.save(group);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> update(@PathVariable Long id, @RequestBody Group group) {
        if (groupService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        group.setGroupId(id);
        return ResponseEntity.ok(groupService.save(group));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (groupService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        groupService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/audit")
    public List<?> getAuditHistory(@PathVariable Long id) {
        return groupService.getAuditHistory(id);
    }
} 