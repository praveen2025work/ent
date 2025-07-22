package com.entitlement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import java.util.Date;

@Entity
@Table(name = "SECURITY_GROUP")
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {
    @Id
    @Column(name = "GROUPID")
    private Long groupId;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ISACTIVE")
    private Integer isActive;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CREATEDON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "UPDATEDBY")
    private String updatedBy;

    @Column(name = "UPDATEDON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Column(name = "EFFECTIVEDATE")
    @Temporal(TemporalType.DATE)
    private Date effectiveDate;

    @Column(name = "EXPIRYDATE")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;
} 