package com.entitlement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "SECURITY_USER")
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "SECURITY_USER_SEQ", allocationSize = 1)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "IS_ACTIVE")
    private Integer isActive;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
} 