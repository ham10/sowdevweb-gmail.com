package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Planning.
 */
@Entity
@Table(name = "planning")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Planning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "num")
    private String num;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @ManyToOne
    @JsonIgnoreProperties("plannings")
    private Medecin medecin;

    @ManyToOne
    @JsonIgnoreProperties("plannings")
    private TypePlanning typePlanning;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public Planning num(String num) {
        this.num = num;
        return this;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getLibelle() {
        return libelle;
    }

    public Planning libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Planning dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public Planning medecin(Medecin medecin) {
        this.medecin = medecin;
        return this;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public TypePlanning getTypePlanning() {
        return typePlanning;
    }

    public Planning typePlanning(TypePlanning typePlanning) {
        this.typePlanning = typePlanning;
        return this;
    }

    public void setTypePlanning(TypePlanning typePlanning) {
        this.typePlanning = typePlanning;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Planning)) {
            return false;
        }
        return id != null && id.equals(((Planning) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Planning{" +
            "id=" + getId() +
            ", num='" + getNum() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }
}
