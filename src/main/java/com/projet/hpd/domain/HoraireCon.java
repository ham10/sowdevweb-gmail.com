package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A HoraireCon.
 */
@Entity
@Table(name = "horaire_con")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HoraireCon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "heure_debut_hc")
    private LocalDate heureDebutHC;

    @Column(name = "heure_fin_hc")
    private LocalDate heureFinHC;

    @Column(name = "date_debut_hc")
    private LocalDate dateDebutHC;

    @Column(name = "date_fin_hc")
    private LocalDate dateFinHC;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

    @Column(name = "date_deleted")
    private LocalDate dateDeleted;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_updated")
    private Long userUpdated;

    @Column(name = "user_deleted")
    private Long userDeleted;

    @ManyToOne
    @JsonIgnoreProperties("horaireCons")
    private Jour jour;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getHeureDebutHC() {
        return heureDebutHC;
    }

    public HoraireCon heureDebutHC(LocalDate heureDebutHC) {
        this.heureDebutHC = heureDebutHC;
        return this;
    }

    public void setHeureDebutHC(LocalDate heureDebutHC) {
        this.heureDebutHC = heureDebutHC;
    }

    public LocalDate getHeureFinHC() {
        return heureFinHC;
    }

    public HoraireCon heureFinHC(LocalDate heureFinHC) {
        this.heureFinHC = heureFinHC;
        return this;
    }

    public void setHeureFinHC(LocalDate heureFinHC) {
        this.heureFinHC = heureFinHC;
    }

    public LocalDate getDateDebutHC() {
        return dateDebutHC;
    }

    public HoraireCon dateDebutHC(LocalDate dateDebutHC) {
        this.dateDebutHC = dateDebutHC;
        return this;
    }

    public void setDateDebutHC(LocalDate dateDebutHC) {
        this.dateDebutHC = dateDebutHC;
    }

    public LocalDate getDateFinHC() {
        return dateFinHC;
    }

    public HoraireCon dateFinHC(LocalDate dateFinHC) {
        this.dateFinHC = dateFinHC;
        return this;
    }

    public void setDateFinHC(LocalDate dateFinHC) {
        this.dateFinHC = dateFinHC;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public HoraireCon dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public HoraireCon dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public HoraireCon dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public HoraireCon userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public HoraireCon userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public HoraireCon userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Jour getJour() {
        return jour;
    }

    public HoraireCon jour(Jour jour) {
        this.jour = jour;
        return this;
    }

    public void setJour(Jour jour) {
        this.jour = jour;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HoraireCon)) {
            return false;
        }
        return id != null && id.equals(((HoraireCon) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "HoraireCon{" +
            "id=" + getId() +
            ", heureDebutHC='" + getHeureDebutHC() + "'" +
            ", heureFinHC='" + getHeureFinHC() + "'" +
            ", dateDebutHC='" + getDateDebutHC() + "'" +
            ", dateFinHC='" + getDateFinHC() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
