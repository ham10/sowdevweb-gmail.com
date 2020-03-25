package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A TypeMagasin.
 */
@Entity
@Table(name = "type_magasin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeMagasin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_magasin")
    private String codeTypeMagasin;

    @Column(name = "libelle_type_magasin")
    private String libelleTypeMagasin;

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

    public String getCodeTypeMagasin() {
        return codeTypeMagasin;
    }

    public TypeMagasin codeTypeMagasin(String codeTypeMagasin) {
        this.codeTypeMagasin = codeTypeMagasin;
        return this;
    }

    public void setCodeTypeMagasin(String codeTypeMagasin) {
        this.codeTypeMagasin = codeTypeMagasin;
    }

    public String getLibelleTypeMagasin() {
        return libelleTypeMagasin;
    }

    public TypeMagasin libelleTypeMagasin(String libelleTypeMagasin) {
        this.libelleTypeMagasin = libelleTypeMagasin;
        return this;
    }

    public void setLibelleTypeMagasin(String libelleTypeMagasin) {
        this.libelleTypeMagasin = libelleTypeMagasin;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypeMagasin dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypeMagasin dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypeMagasin userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypeMagasin userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypeMagasin userDeleted(Long userDeleted) {
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
        if (!(o instanceof TypeMagasin)) {
            return false;
        }
        return id != null && id.equals(((TypeMagasin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeMagasin{" +
            "id=" + getId() +
            ", codeTypeMagasin='" + getCodeTypeMagasin() + "'" +
            ", libelleTypeMagasin='" + getLibelleTypeMagasin() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
