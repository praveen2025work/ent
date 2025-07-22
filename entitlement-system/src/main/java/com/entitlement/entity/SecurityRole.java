package com.entitlement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "SECURITY_ROLE")
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "SECURITY_ROLE_SEQ", allocationSize = 1)
    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "ROLE_NAME", nullable = false)
    private String roleName;

    @Column(name = "IS_READ_WRITE")
    private Integer isReadWrite;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "USER_TYPE")
    private String userType;

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