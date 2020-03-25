package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeServices.
 */
@Entity
@Table(name = "type_services")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeServices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_srv")
    private String codeTypeSrv;

    @Column(name = "libelle_type_srv")
    private String libelleTypeSrv;

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

    @OneToMany(mappedBy = "typeServices")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Services> services = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypeSrv() {
        return codeTypeSrv;
    }

    public TypeServices codeTypeSrv(String codeTypeSrv) {
        this.codeTypeSrv = codeTypeSrv;
        return this;
    }

    public void setCodeTypeSrv(String codeTypeSrv) {
        this.codeTypeSrv = codeTypeSrv;
    }

    public String getLibelleTypeSrv() {
        return libelleTypeSrv;
    }

    public TypeServices libelleTypeSrv(String libelleTypeSrv) {
        this.libelleTypeSrv = libelleTypeSrv;
        return this;
    }

    public void setLibelleTypeSrv(String libelleTypeSrv) {
        this.libelleTypeSrv = libelleTypeSrv;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypeServices dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypeServices dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypeServices userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypeServices userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypeServices userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Services> getServices() {
        return services;
    }

    public TypeServices services(Set<Services> services) {
        this.services = services;
        return this;
    }

    public TypeServices addServices(Services services) {
        this.services.add(services);
        services.setTypeServices(this);
        return this;
    }

    public TypeServices removeServices(Services services) {
        this.services.remove(services);
        services.setTypeServices(null);
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
        if (!(o instanceof TypeServices)) {
            return false;
        }
        return id != null && id.equals(((TypeServices) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeServices{" +
            "id=" + getId() +
            ", codeTypeSrv='" + getCodeTypeSrv() + "'" +
            ", libelleTypeSrv='" + getLibelleTypeSrv() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
