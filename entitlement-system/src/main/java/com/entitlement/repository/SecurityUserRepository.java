package com.entitlement.repository;

import com.entitlement.entity.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityUserRepository extends JpaRepository<SecurityUser, Long> {
} 