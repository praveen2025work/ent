package com.entitlement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "SECURITY_USER_ROLE", uniqueConstraints = @UniqueConstraint(columnNames = {"USER_ID", "ROLE_ID", "APPLICATION_ID"}))
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_seq")
    @SequenceGenerator(name = "user_role_seq", sequenceName = "SECURITY_USER_ROLE_SEQ", allocationSize = 1)
    @Column(name = "USER_ROLE_ID")
    private Long userRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private SecurityUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private SecurityRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_ID", nullable = false)
    private SecurityApplication application;

    @Column(name = "EFFECTIVE_DATE")
    @Temporal(TemporalType.DATE)
    private Date effectiveDate;

    @Column(name = "EXPIRY_DATE")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
} 