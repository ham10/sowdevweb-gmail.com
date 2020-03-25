package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Pole.
 */
@Entity
@Table(name = "pole")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_pole")
    private String codePole;

    @Column(name = "libelle_pole")
    private String libellePole;

    @Column(name = "description_pole")
    private String descriptionPole;

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
    @JsonIgnoreProperties("poles")
    private TypePole typePole;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodePole() {
        return codePole;
    }

    public Pole codePole(String codePole) {
        this.codePole = codePole;
        return this;
    }

    public void setCodePole(String codePole) {
        this.codePole = codePole;
    }

    public String getLibellePole() {
        return libellePole;
    }

    public Pole libellePole(String libellePole) {
        this.libellePole = libellePole;
        return this;
    }

    public void setLibellePole(String libellePole) {
        this.libellePole = libellePole;
    }

    public String getDescriptionPole() {
        return descriptionPole;
    }

    public Pole descriptionPole(String descriptionPole) {
        this.descriptionPole = descriptionPole;
        return this;
    }

    public void setDescriptionPole(String descriptionPole) {
        this.descriptionPole = descriptionPole;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Pole dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Pole dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Pole userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Pole userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Pole userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public TypePole getTypePole() {
        return typePole;
    }

    public Pole typePole(TypePole typePole) {
        this.typePole = typePole;
        return this;
    }

    public void setTypePole(TypePole typePole) {
        this.typePole = typePole;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pole)) {
            return false;
        }
        return id != null && id.equals(((Pole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pole{" +
            "id=" + getId() +
            ", codePole='" + getCodePole() + "'" +
            ", libellePole='" + getLibellePole() + "'" +
            ", descriptionPole='" + getDescriptionPole() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
