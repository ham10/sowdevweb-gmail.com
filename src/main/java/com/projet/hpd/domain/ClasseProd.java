package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ClasseProd.
 */
@Entity
@Table(name = "classe_prod")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClasseProd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_classe_prod")
    private String codeClasseProd;

    @Column(name = "libelle_classe_prod")
    private String libelleClasseProd;

    @Column(name = "description_classe_prod")
    private String descriptionClasseProd;

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

    public String getCodeClasseProd() {
        return codeClasseProd;
    }

    public ClasseProd codeClasseProd(String codeClasseProd) {
        this.codeClasseProd = codeClasseProd;
        return this;
    }

    public void setCodeClasseProd(String codeClasseProd) {
        this.codeClasseProd = codeClasseProd;
    }

    public String getLibelleClasseProd() {
        return libelleClasseProd;
    }

    public ClasseProd libelleClasseProd(String libelleClasseProd) {
        this.libelleClasseProd = libelleClasseProd;
        return this;
    }

    public void setLibelleClasseProd(String libelleClasseProd) {
        this.libelleClasseProd = libelleClasseProd;
    }

    public String getDescriptionClasseProd() {
        return descriptionClasseProd;
    }

    public ClasseProd descriptionClasseProd(String descriptionClasseProd) {
        this.descriptionClasseProd = descriptionClasseProd;
        return this;
    }

    public void setDescriptionClasseProd(String descriptionClasseProd) {
        this.descriptionClasseProd = descriptionClasseProd;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public ClasseProd dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public ClasseProd dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public ClasseProd userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public ClasseProd userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public ClasseProd userDeleted(Long userDeleted) {
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
        if (!(o instanceof ClasseProd)) {
            return false;
        }
        return id != null && id.equals(((ClasseProd) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ClasseProd{" +
            "id=" + getId() +
            ", codeClasseProd='" + getCodeClasseProd() + "'" +
            ", libelleClasseProd='" + getLibelleClasseProd() + "'" +
            ", descriptionClasseProd='" + getDescriptionClasseProd() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
