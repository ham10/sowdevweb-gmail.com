package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Vaccin.
 */
@Entity
@Table(name = "vaccin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vaccin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "date_renouv")
    private LocalDate dateRenouv;

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
    @JsonIgnoreProperties("vaccins")
    private FicheMedical ficheMedical;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Vaccin description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public Vaccin date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDateRenouv() {
        return dateRenouv;
    }

    public Vaccin dateRenouv(LocalDate dateRenouv) {
        this.dateRenouv = dateRenouv;
        return this;
    }

    public void setDateRenouv(LocalDate dateRenouv) {
        this.dateRenouv = dateRenouv;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Vaccin dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Vaccin dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Vaccin userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Vaccin userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Vaccin userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public FicheMedical getFicheMedical() {
        return ficheMedical;
    }

    public Vaccin ficheMedical(FicheMedical ficheMedical) {
        this.ficheMedical = ficheMedical;
        return this;
    }

    public void setFicheMedical(FicheMedical ficheMedical) {
        this.ficheMedical = ficheMedical;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vaccin)) {
            return false;
        }
        return id != null && id.equals(((Vaccin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vaccin{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", date='" + getDate() + "'" +
            ", dateRenouv='" + getDateRenouv() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
