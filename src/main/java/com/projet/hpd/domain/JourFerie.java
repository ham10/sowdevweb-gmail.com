package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A JourFerie.
 */
@Entity
@Table(name = "jour_ferie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JourFerie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle_jour_ferie")
    private String libelleJourFerie;

    @Column(name = "date_jour_ferie")
    private LocalDate dateJourFerie;

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

    public String getLibelleJourFerie() {
        return libelleJourFerie;
    }

    public JourFerie libelleJourFerie(String libelleJourFerie) {
        this.libelleJourFerie = libelleJourFerie;
        return this;
    }

    public void setLibelleJourFerie(String libelleJourFerie) {
        this.libelleJourFerie = libelleJourFerie;
    }

    public LocalDate getDateJourFerie() {
        return dateJourFerie;
    }

    public JourFerie dateJourFerie(LocalDate dateJourFerie) {
        this.dateJourFerie = dateJourFerie;
        return this;
    }

    public void setDateJourFerie(LocalDate dateJourFerie) {
        this.dateJourFerie = dateJourFerie;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public JourFerie dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public JourFerie dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public JourFerie userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public JourFerie userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public JourFerie userDeleted(Long userDeleted) {
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
        if (!(o instanceof JourFerie)) {
            return false;
        }
        return id != null && id.equals(((JourFerie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "JourFerie{" +
            "id=" + getId() +
            ", libelleJourFerie='" + getLibelleJourFerie() + "'" +
            ", dateJourFerie='" + getDateJourFerie() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
