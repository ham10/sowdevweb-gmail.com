package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Region.
 */
@Entity
@Table(name = "region")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_region")
    private String codeRegion;

    @Column(name = "libelle_region")
    private String libelleRegion;

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

    @OneToMany(mappedBy = "region")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Departement> departements = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("regions")
    private Pays pays;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeRegion() {
        return codeRegion;
    }

    public Region codeRegion(String codeRegion) {
        this.codeRegion = codeRegion;
        return this;
    }

    public void setCodeRegion(String codeRegion) {
        this.codeRegion = codeRegion;
    }

    public String getLibelleRegion() {
        return libelleRegion;
    }

    public Region libelleRegion(String libelleRegion) {
        this.libelleRegion = libelleRegion;
        return this;
    }

    public void setLibelleRegion(String libelleRegion) {
        this.libelleRegion = libelleRegion;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Region dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Region dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Region userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Region userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Region userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Departement> getDepartements() {
        return departements;
    }

    public Region departements(Set<Departement> departements) {
        this.departements = departements;
        return this;
    }

    public Region addDepartement(Departement departement) {
        this.departements.add(departement);
        departement.setRegion(this);
        return this;
    }

    public Region removeDepartement(Departement departement) {
        this.departements.remove(departement);
        departement.setRegion(null);
        return this;
    }

    public void setDepartements(Set<Departement> departements) {
        this.departements = departements;
    }

    public Pays getPays() {
        return pays;
    }

    public Region pays(Pays pays) {
        this.pays = pays;
        return this;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Region)) {
            return false;
        }
        return id != null && id.equals(((Region) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Region{" +
            "id=" + getId() +
            ", codeRegion='" + getCodeRegion() + "'" +
            ", libelleRegion='" + getLibelleRegion() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
