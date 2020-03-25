package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Specialite.
 */
@Entity
@Table(name = "specialite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Specialite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_specialite")
    private String codeSpecialite;

    @Column(name = "libelle_specialite")
    private String libelleSpecialite;

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

    @OneToMany(mappedBy = "specialite")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Services> services = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeSpecialite() {
        return codeSpecialite;
    }

    public Specialite codeSpecialite(String codeSpecialite) {
        this.codeSpecialite = codeSpecialite;
        return this;
    }

    public void setCodeSpecialite(String codeSpecialite) {
        this.codeSpecialite = codeSpecialite;
    }

    public String getLibelleSpecialite() {
        return libelleSpecialite;
    }

    public Specialite libelleSpecialite(String libelleSpecialite) {
        this.libelleSpecialite = libelleSpecialite;
        return this;
    }

    public void setLibelleSpecialite(String libelleSpecialite) {
        this.libelleSpecialite = libelleSpecialite;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Specialite dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Specialite dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Specialite userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Specialite userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Specialite userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Services> getServices() {
        return services;
    }

    public Specialite services(Set<Services> services) {
        this.services = services;
        return this;
    }

    public Specialite addServices(Services services) {
        this.services.add(services);
        services.setSpecialite(this);
        return this;
    }

    public Specialite removeServices(Services services) {
        this.services.remove(services);
        services.setSpecialite(null);
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
        if (!(o instanceof Specialite)) {
            return false;
        }
        return id != null && id.equals(((Specialite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Specialite{" +
            "id=" + getId() +
            ", codeSpecialite='" + getCodeSpecialite() + "'" +
            ", libelleSpecialite='" + getLibelleSpecialite() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
