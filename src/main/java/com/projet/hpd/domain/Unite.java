package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Unite.
 */
@Entity
@Table(name = "unite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Unite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_unite")
    private String codeUnite;

    @Column(name = "libelle_unite")
    private String libelleUnite;

    @Column(name = "description_unite")
    private String descriptionUnite;

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

    @ManyToOne
    @JsonIgnoreProperties("unites")
    private TypeUnite typeUnite;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeUnite() {
        return codeUnite;
    }

    public Unite codeUnite(String codeUnite) {
        this.codeUnite = codeUnite;
        return this;
    }

    public void setCodeUnite(String codeUnite) {
        this.codeUnite = codeUnite;
    }

    public String getLibelleUnite() {
        return libelleUnite;
    }

    public Unite libelleUnite(String libelleUnite) {
        this.libelleUnite = libelleUnite;
        return this;
    }

    public void setLibelleUnite(String libelleUnite) {
        this.libelleUnite = libelleUnite;
    }

    public String getDescriptionUnite() {
        return descriptionUnite;
    }

    public Unite descriptionUnite(String descriptionUnite) {
        this.descriptionUnite = descriptionUnite;
        return this;
    }

    public void setDescriptionUnite(String descriptionUnite) {
        this.descriptionUnite = descriptionUnite;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Unite dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Unite dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Unite userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Unite userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Unite userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public TypeUnite getTypeUnite() {
        return typeUnite;
    }

    public Unite typeUnite(TypeUnite typeUnite) {
        this.typeUnite = typeUnite;
        return this;
    }

    public void setTypeUnite(TypeUnite typeUnite) {
        this.typeUnite = typeUnite;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Unite)) {
            return false;
        }
        return id != null && id.equals(((Unite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Unite{" +
            "id=" + getId() +
            ", codeUnite='" + getCodeUnite() + "'" +
            ", libelleUnite='" + getLibelleUnite() + "'" +
            ", descriptionUnite='" + getDescriptionUnite() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
