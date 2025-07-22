package com.entitlement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "SECURITY_FUNCTIONAL_ROLE")
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityFunctionalRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "func_role_seq")
    @SequenceGenerator(name = "func_role_seq", sequenceName = "SECURITY_FUNCTIONAL_ROLE_SEQ", allocationSize = 1)
    @Column(name = "FUNCTIONAL_ROLE_ID")
    private Long functionalRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_ID", nullable = false)
    private SecurityApplication application;

    @Column(name = "ENTITY_TYPE")
    private String entityType;

    @Column(name = "ENTITY_SUBTYPE")
    private String entitySubtype;

    @Column(name = "ENTITY_GROUP")
    private String entityGroup;

    @Column(name = "ENTITY_ID")
    private Long entityId;

    @Column(name = "IS_ACTIVE")
    private Integer isActive;

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