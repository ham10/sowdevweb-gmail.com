package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Plateau.
 */
@Entity
@Table(name = "plateau")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Plateau implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle_plateau")
    private String libellePlateau;

    @Column(name = "description_plateau")
    private String descriptionPlateau;

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
    @JsonIgnoreProperties("plateaus")
    private TypePlateau typePlateau;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibellePlateau() {
        return libellePlateau;
    }

    public Plateau libellePlateau(String libellePlateau) {
        this.libellePlateau = libellePlateau;
        return this;
    }

    public void setLibellePlateau(String libellePlateau) {
        this.libellePlateau = libellePlateau;
    }

    public String getDescriptionPlateau() {
        return descriptionPlateau;
    }

    public Plateau descriptionPlateau(String descriptionPlateau) {
        this.descriptionPlateau = descriptionPlateau;
        return this;
    }

    public void setDescriptionPlateau(String descriptionPlateau) {
        this.descriptionPlateau = descriptionPlateau;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Plateau dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Plateau dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Plateau userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Plateau userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Plateau userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public TypePlateau getTypePlateau() {
        return typePlateau;
    }

    public Plateau typePlateau(TypePlateau typePlateau) {
        this.typePlateau = typePlateau;
        return this;
    }

    public void setTypePlateau(TypePlateau typePlateau) {
        this.typePlateau = typePlateau;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plateau)) {
            return false;
        }
        return id != null && id.equals(((Plateau) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Plateau{" +
            "id=" + getId() +
            ", libellePlateau='" + getLibellePlateau() + "'" +
            ", descriptionPlateau='" + getDescriptionPlateau() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
