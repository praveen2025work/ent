package com.entitlement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "SECURITY_USERENTXREFHIST")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntXrefHist {
    @Id
    @Column(name = "USERENTITLEMENTXREFHISTID")
    private Long userEntitlementXrefHistId;

    @Column(name = "USERENTITLEMENTXREFID")
    private Long userEntitlementXrefId;

    @Column(name = "USERID")
    private String userId;

    @Column(name = "ENTITLEMENTID")
    private Long entitlementId;

    @Column(name = "ISACTIVE")
    private Integer isActive;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "CREATEDON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

    @Column(name = "LASTUPDATEDBY")
    private String lastUpdatedBy;

    @Column(name = "UPDATEDON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Column(name = "EFFECTIVEDATE")
    @Temporal(TemporalType.DATE)
    private Date effectiveDate;

    @Column(name = "EXPIRYDATE")
    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @Column(name = "HIST_CREATEDON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date histCreatedOn;
} 