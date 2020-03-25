package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A RDV.
 */
@Entity
@Table(name = "rdv")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RDV implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "num_rdv")
    private String numRdv;

    @Column(name = "date_rdv")
    private LocalDate dateRdv;

    @Column(name = "heure_rdv")
    private Instant heureRdv;

    @ManyToOne
    @JsonIgnoreProperties("rDVS")
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties("rDVS")
    private Planning planning;

    @ManyToOne
    @JsonIgnoreProperties("rDVS")
    private EtatRdv etatRDV;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumRdv() {
        return numRdv;
    }

    public RDV numRdv(String numRdv) {
        this.numRdv = numRdv;
        return this;
    }

    public void setNumRdv(String numRdv) {
        this.numRdv = numRdv;
    }

    public LocalDate getDateRdv() {
        return dateRdv;
    }

    public RDV dateRdv(LocalDate dateRdv) {
        this.dateRdv = dateRdv;
        return this;
    }

    public void setDateRdv(LocalDate dateRdv) {
        this.dateRdv = dateRdv;
    }

    public Instant getHeureRdv() {
        return heureRdv;
    }

    public RDV heureRdv(Instant heureRdv) {
        this.heureRdv = heureRdv;
        return this;
    }

    public void setHeureRdv(Instant heureRdv) {
        this.heureRdv = heureRdv;
    }

    public Patient getPatient() {
        return patient;
    }

    public RDV patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Planning getPlanning() {
        return planning;
    }

    public RDV planning(Planning planning) {
        this.planning = planning;
        return this;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    public EtatRdv getEtatRDV() {
        return etatRDV;
    }

    public RDV etatRDV(EtatRdv etatRdv) {
        this.etatRDV = etatRdv;
        return this;
    }

    public void setEtatRDV(EtatRdv etatRdv) {
        this.etatRDV = etatRdv;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RDV)) {
            return false;
        }
        return id != null && id.equals(((RDV) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RDV{" +
            "id=" + getId() +
            ", numRdv='" + getNumRdv() + "'" +
            ", dateRdv='" + getDateRdv() + "'" +
            ", heureRdv='" + getHeureRdv() + "'" +
            "}";
    }
}
