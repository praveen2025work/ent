package com.entitlement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "SECURITY_APPLICATION")
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_seq")
    @SequenceGenerator(name = "app_seq", sequenceName = "SECURITY_APPLICATION_SEQ", allocationSize = 1)
    @Column(name = "APPLICATION_ID")
    private Long applicationId;

    @Column(name = "CODE", unique = true, nullable = false)
    private String code;

    @Column(name = "NAME", nullable = false)
    private String name;

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