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
 * A Departement.
 */
@Entity
@Table(name = "departement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Departement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_departement")
    private String codeDepartement;

    @Column(name = "libelle_departement")
    private String libelleDepartement;

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

    @OneToMany(mappedBy = "departement")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Etablis> etablis = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("departements")
    private Region region;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeDepartement() {
        return codeDepartement;
    }

    public Departement codeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
        return this;
    }

    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    public String getLibelleDepartement() {
        return libelleDepartement;
    }

    public Departement libelleDepartement(String libelleDepartement) {
        this.libelleDepartement = libelleDepartement;
        return this;
    }

    public void setLibelleDepartement(String libelleDepartement) {
        this.libelleDepartement = libelleDepartement;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Departement dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Departement dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Departement userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Departement userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Departement userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Etablis> getEtablis() {
        return etablis;
    }

    public Departement etablis(Set<Etablis> etablis) {
        this.etablis = etablis;
        return this;
    }

    public Departement addEtablis(Etablis etablis) {
        this.etablis.add(etablis);
        etablis.setDepartement(this);
        return this;
    }

    public Departement removeEtablis(Etablis etablis) {
        this.etablis.remove(etablis);
        etablis.setDepartement(null);
        return this;
    }

    public void setEtablis(Set<Etablis> etablis) {
        this.etablis = etablis;
    }

    public Region getRegion() {
        return region;
    }

    public Departement region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departement)) {
            return false;
        }
        return id != null && id.equals(((Departement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Departement{" +
            "id=" + getId() +
            ", codeDepartement='" + getCodeDepartement() + "'" +
            ", libelleDepartement='" + getLibelleDepartement() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
