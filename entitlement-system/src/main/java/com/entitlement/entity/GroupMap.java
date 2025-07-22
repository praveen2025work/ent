package com.entitlement.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import java.util.Date;

@Entity
@Table(name = "SECURITY_GROUPMAP")
@Audited
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMap {
    @Id
    @Column(name = "GROUPMAPID")
    private Long groupMapId;

    @Column(name = "GROUPID")
    private Long groupId;

    @Column(name = "GROUPMAPVALUE")
    private String groupMapValue;

    @Column(name = "GROUPMAPDESC")
    private String groupMapDesc;

    @Column(name = "EXCLUDE")
    private Integer exclude;

    @Column(name = "SORT")
    private Integer sort;

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