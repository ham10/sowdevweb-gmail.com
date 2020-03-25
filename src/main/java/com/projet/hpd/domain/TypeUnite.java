package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeUnite.
 */
@Entity
@Table(name = "type_unite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeUnite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_unite")
    private String codeTypeUnite;

    @Column(name = "libelle_type_unite")
    private String libelleTypeUnite;

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

    @OneToMany(mappedBy = "typeUnite")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Unite> unites = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypeUnite() {
        return codeTypeUnite;
    }

    public TypeUnite codeTypeUnite(String codeTypeUnite) {
        this.codeTypeUnite = codeTypeUnite;
        return this;
    }

    public void setCodeTypeUnite(String codeTypeUnite) {
        this.codeTypeUnite = codeTypeUnite;
    }

    public String getLibelleTypeUnite() {
        return libelleTypeUnite;
    }

    public TypeUnite libelleTypeUnite(String libelleTypeUnite) {
        this.libelleTypeUnite = libelleTypeUnite;
        return this;
    }

    public void setLibelleTypeUnite(String libelleTypeUnite) {
        this.libelleTypeUnite = libelleTypeUnite;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypeUnite dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypeUnite dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypeUnite userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypeUnite userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypeUnite userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Unite> getUnites() {
        return unites;
    }

    public TypeUnite unites(Set<Unite> unites) {
        this.unites = unites;
        return this;
    }

    public TypeUnite addUnite(Unite unite) {
        this.unites.add(unite);
        unite.setTypeUnite(this);
        return this;
    }

    public TypeUnite removeUnite(Unite unite) {
        this.unites.remove(unite);
        unite.setTypeUnite(null);
        return this;
    }

    public void setUnites(Set<Unite> unites) {
        this.unites = unites;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeUnite)) {
            return false;
        }
        return id != null && id.equals(((TypeUnite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeUnite{" +
            "id=" + getId() +
            ", codeTypeUnite='" + getCodeTypeUnite() + "'" +
            ", libelleTypeUnite='" + getLibelleTypeUnite() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
