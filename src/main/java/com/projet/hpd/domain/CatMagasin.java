package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CatMagasin.
 */
@Entity
@Table(name = "cat_magasin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CatMagasin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_cat_magasin")
    private String codeCatMagasin;

    @Column(name = "libelle_cat_magasin")
    private String libelleCatMagasin;

    @Column(name = "description_cat_magasin")
    private String descriptionCatMagasin;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeCatMagasin() {
        return codeCatMagasin;
    }

    public CatMagasin codeCatMagasin(String codeCatMagasin) {
        this.codeCatMagasin = codeCatMagasin;
        return this;
    }

    public void setCodeCatMagasin(String codeCatMagasin) {
        this.codeCatMagasin = codeCatMagasin;
    }

    public String getLibelleCatMagasin() {
        return libelleCatMagasin;
    }

    public CatMagasin libelleCatMagasin(String libelleCatMagasin) {
        this.libelleCatMagasin = libelleCatMagasin;
        return this;
    }

    public void setLibelleCatMagasin(String libelleCatMagasin) {
        this.libelleCatMagasin = libelleCatMagasin;
    }

    public String getDescriptionCatMagasin() {
        return descriptionCatMagasin;
    }

    public CatMagasin descriptionCatMagasin(String descriptionCatMagasin) {
        this.descriptionCatMagasin = descriptionCatMagasin;
        return this;
    }

    public void setDescriptionCatMagasin(String descriptionCatMagasin) {
        this.descriptionCatMagasin = descriptionCatMagasin;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public CatMagasin dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public CatMagasin dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public CatMagasin userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public CatMagasin userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public CatMagasin userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatMagasin)) {
            return false;
        }
        return id != null && id.equals(((CatMagasin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CatMagasin{" +
            "id=" + getId() +
            ", codeCatMagasin='" + getCodeCatMagasin() + "'" +
            ", libelleCatMagasin='" + getLibelleCatMagasin() + "'" +
            ", descriptionCatMagasin='" + getDescriptionCatMagasin() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
