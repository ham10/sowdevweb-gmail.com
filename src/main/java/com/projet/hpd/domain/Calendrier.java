package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Calendrier.
 */
@Entity
@Table(name = "calendrier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Calendrier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle_calendrier")
    private String libelleCalendrier;

    @Column(name = "heure_debut")
    private LocalDate heureDebut;

    @Column(name = "heure_fin")
    private LocalDate heureFin;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

    @Column(name = "date_deleted")
    private LocalDate dateDeleted;

    @Column(name = "user_created")
    private LocalDate userCreated;

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

    public String getLibelleCalendrier() {
        return libelleCalendrier;
    }

    public Calendrier libelleCalendrier(String libelleCalendrier) {
        this.libelleCalendrier = libelleCalendrier;
        return this;
    }

    public void setLibelleCalendrier(String libelleCalendrier) {
        this.libelleCalendrier = libelleCalendrier;
    }

    public LocalDate getHeureDebut() {
        return heureDebut;
    }

    public Calendrier heureDebut(LocalDate heureDebut) {
        this.heureDebut = heureDebut;
        return this;
    }

    public void setHeureDebut(LocalDate heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalDate getHeureFin() {
        return heureFin;
    }

    public Calendrier heureFin(LocalDate heureFin) {
        this.heureFin = heureFin;
        return this;
    }

    public void setHeureFin(LocalDate heureFin) {
        this.heureFin = heureFin;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public Calendrier dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public Calendrier dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Calendrier dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Calendrier dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public Calendrier dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public LocalDate getUserCreated() {
        return userCreated;
    }

    public Calendrier userCreated(LocalDate userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(LocalDate userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Calendrier userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Calendrier userDeleted(Long userDeleted) {
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
        if (!(o instanceof Calendrier)) {
            return false;
        }
        return id != null && id.equals(((Calendrier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Calendrier{" +
            "id=" + getId() +
            ", libelleCalendrier='" + getLibelleCalendrier() + "'" +
            ", heureDebut='" + getHeureDebut() + "'" +
            ", heureFin='" + getHeureFin() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated='" + getUserCreated() + "'" +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
