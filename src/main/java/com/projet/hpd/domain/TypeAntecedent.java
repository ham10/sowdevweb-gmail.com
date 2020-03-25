package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A TypeAntecedent.
 */
@Entity
@Table(name = "type_antecedent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeAntecedent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_antecedent")
    private String codeTypeAntecedent;

    @Column(name = "libelle_type_antecedent")
    private String libelleTypeAntecedent;

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

    public String getCodeTypeAntecedent() {
        return codeTypeAntecedent;
    }

    public TypeAntecedent codeTypeAntecedent(String codeTypeAntecedent) {
        this.codeTypeAntecedent = codeTypeAntecedent;
        return this;
    }

    public void setCodeTypeAntecedent(String codeTypeAntecedent) {
        this.codeTypeAntecedent = codeTypeAntecedent;
    }

    public String getLibelleTypeAntecedent() {
        return libelleTypeAntecedent;
    }

    public TypeAntecedent libelleTypeAntecedent(String libelleTypeAntecedent) {
        this.libelleTypeAntecedent = libelleTypeAntecedent;
        return this;
    }

    public void setLibelleTypeAntecedent(String libelleTypeAntecedent) {
        this.libelleTypeAntecedent = libelleTypeAntecedent;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypeAntecedent dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypeAntecedent dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypeAntecedent userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypeAntecedent userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypeAntecedent userDeleted(Long userDeleted) {
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
        if (!(o instanceof TypeAntecedent)) {
            return false;
        }
        return id != null && id.equals(((TypeAntecedent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeAntecedent{" +
            "id=" + getId() +
            ", codeTypeAntecedent='" + getCodeTypeAntecedent() + "'" +
            ", libelleTypeAntecedent='" + getLibelleTypeAntecedent() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
