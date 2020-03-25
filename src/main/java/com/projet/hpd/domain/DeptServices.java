package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A DeptServices.
 */
@Entity
@Table(name = "dept_services")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DeptServices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_dept_srv")
    private String codeDeptSrv;

    @Column(name = "libelle_dept_srv")
    private String libelleDeptSrv;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_updated")
    private Long userUpdated;

    @Column(name = "user_deleted")
    private Long userDeleted;

    @OneToMany(mappedBy = "deptServices")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Services> services = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeDeptSrv() {
        return codeDeptSrv;
    }

    public DeptServices codeDeptSrv(String codeDeptSrv) {
        this.codeDeptSrv = codeDeptSrv;
        return this;
    }

    public void setCodeDeptSrv(String codeDeptSrv) {
        this.codeDeptSrv = codeDeptSrv;
    }

    public String getLibelleDeptSrv() {
        return libelleDeptSrv;
    }

    public DeptServices libelleDeptSrv(String libelleDeptSrv) {
        this.libelleDeptSrv = libelleDeptSrv;
        return this;
    }

    public void setLibelleDeptSrv(String libelleDeptSrv) {
        this.libelleDeptSrv = libelleDeptSrv;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public DeptServices dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public DeptServices dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public DeptServices userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public DeptServices userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public DeptServices userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Services> getServices() {
        return services;
    }

    public DeptServices services(Set<Services> services) {
        this.services = services;
        return this;
    }

    public DeptServices addServices(Services services) {
        this.services.add(services);
        services.setDeptServices(this);
        return this;
    }

    public DeptServices removeServices(Services services) {
        this.services.remove(services);
        services.setDeptServices(null);
        return this;
    }

    public void setServices(Set<Services> services) {
        this.services = services;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeptServices)) {
            return false;
        }
        return id != null && id.equals(((DeptServices) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DeptServices{" +
            "id=" + getId() +
            ", codeDeptSrv='" + getCodeDeptSrv() + "'" +
            ", libelleDeptSrv='" + getLibelleDeptSrv() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
