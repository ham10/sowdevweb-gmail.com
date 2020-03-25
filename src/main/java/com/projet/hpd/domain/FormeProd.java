package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A FormeProd.
 */
@Entity
@Table(name = "forme_prod")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FormeProd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_forme_prod")
    private String codeFormeProd;

    @Column(name = "libelle_forme_prod")
    private String libelleFormeProd;

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

    public String getCodeFormeProd() {
        return codeFormeProd;
    }

    public FormeProd codeFormeProd(String codeFormeProd) {
        this.codeFormeProd = codeFormeProd;
        return this;
    }

    public void setCodeFormeProd(String codeFormeProd) {
        this.codeFormeProd = codeFormeProd;
    }

    public String getLibelleFormeProd() {
        return libelleFormeProd;
    }

    public FormeProd libelleFormeProd(String libelleFormeProd) {
        this.libelleFormeProd = libelleFormeProd;
        return this;
    }

    public void setLibelleFormeProd(String libelleFormeProd) {
        this.libelleFormeProd = libelleFormeProd;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public FormeProd dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public FormeProd dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public FormeProd userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public FormeProd userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public FormeProd userDeleted(Long userDeleted) {
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
        if (!(o instanceof FormeProd)) {
            return false;
        }
        return id != null && id.equals(((FormeProd) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FormeProd{" +
            "id=" + getId() +
            ", codeFormeProd='" + getCodeFormeProd() + "'" +
            ", libelleFormeProd='" + getLibelleFormeProd() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
