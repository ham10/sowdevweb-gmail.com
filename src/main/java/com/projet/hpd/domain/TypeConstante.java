package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeConstante.
 */
@Entity
@Table(name = "type_constante")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeConstante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_constante")
    private String codeTypeConstante;

    @Column(name = "libelle_type_constante")
    private String libelleTypeConstante;

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

    @ManyToMany(mappedBy = "typeConstantes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<FicheMedical> ficheMedicals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypeConstante() {
        return codeTypeConstante;
    }

    public TypeConstante codeTypeConstante(String codeTypeConstante) {
        this.codeTypeConstante = codeTypeConstante;
        return this;
    }

    public void setCodeTypeConstante(String codeTypeConstante) {
        this.codeTypeConstante = codeTypeConstante;
    }

    public String getLibelleTypeConstante() {
        return libelleTypeConstante;
    }

    public TypeConstante libelleTypeConstante(String libelleTypeConstante) {
        this.libelleTypeConstante = libelleTypeConstante;
        return this;
    }

    public void setLibelleTypeConstante(String libelleTypeConstante) {
        this.libelleTypeConstante = libelleTypeConstante;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypeConstante dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypeConstante dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypeConstante userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypeConstante userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypeConstante userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<FicheMedical> getFicheMedicals() {
        return ficheMedicals;
    }

    public TypeConstante ficheMedicals(Set<FicheMedical> ficheMedicals) {
        this.ficheMedicals = ficheMedicals;
        return this;
    }

    public TypeConstante addFicheMedical(FicheMedical ficheMedical) {
        this.ficheMedicals.add(ficheMedical);
        ficheMedical.getTypeConstantes().add(this);
        return this;
    }

    public TypeConstante removeFicheMedical(FicheMedical ficheMedical) {
        this.ficheMedicals.remove(ficheMedical);
        ficheMedical.getTypeConstantes().remove(this);
        return this;
    }

    public void setFicheMedicals(Set<FicheMedical> ficheMedicals) {
        this.ficheMedicals = ficheMedicals;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeConstante)) {
            return false;
        }
        return id != null && id.equals(((TypeConstante) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeConstante{" +
            "id=" + getId() +
            ", codeTypeConstante='" + getCodeTypeConstante() + "'" +
            ", libelleTypeConstante='" + getLibelleTypeConstante() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
