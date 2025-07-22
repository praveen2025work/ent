package com.entitlement.controller;

import com.entitlement.entity.GroupMap;
import com.entitlement.service.GroupMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group-maps")
@RequiredArgsConstructor
public class GroupMapController {
    private final GroupMapService groupMapService;

    @GetMapping
    public List<GroupMap> getAll() {
        return groupMapService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupMap> getById(@PathVariable Long id) {
        GroupMap groupMap = groupMapService.findById(id);
        return groupMap != null ? ResponseEntity.ok(groupMap) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public GroupMap create(@RequestBody GroupMap groupMap) {
        return groupMapService.save(groupMap);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupMap> update(@PathVariable Long id, @RequestBody GroupMap groupMap) {
        if (groupMapService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        groupMap.setGroupMapId(id);
        return ResponseEntity.ok(groupMapService.save(groupMap));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (groupMapService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        groupMapService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/audit")
    public List<?> getAuditHistory(@PathVariable Long id) {
        return groupMapService.getAuditHistory(id);
    }
} 