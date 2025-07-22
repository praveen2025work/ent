package com.entitlement.repository;

import com.entitlement.entity.SecurityRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityRoleRepository extends JpaRepository<SecurityRole, Long> {
} 