package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DetailPlanning.
 */
@Entity
@Table(name = "detail_planning")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DetailPlanning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titre")
    private String titre;

    @Column(name = "date_debut")
    private Instant dateDebut;

    @Column(name = "date_fin")
    private Instant dateFin;

    @ManyToOne
    @JsonIgnoreProperties("detailPlannings")
    private Planning planning;

    @ManyToOne
    @JsonIgnoreProperties("detailPlannings")
    private EtatPlanning etatPlanning;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public DetailPlanning titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public DetailPlanning dateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public DetailPlanning dateFin(Instant dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public Planning getPlanning() {
        return planning;
    }

    public DetailPlanning planning(Planning planning) {
        this.planning = planning;
        return this;
    }

    public void setPlanning(Planning planning) {
        this.planning = planning;
    }

    public EtatPlanning getEtatPlanning() {
        return etatPlanning;
    }

    public DetailPlanning etatPlanning(EtatPlanning etatPlanning) {
        this.etatPlanning = etatPlanning;
        return this;
    }

    public void setEtatPlanning(EtatPlanning etatPlanning) {
        this.etatPlanning = etatPlanning;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailPlanning)) {
            return false;
        }
        return id != null && id.equals(((DetailPlanning) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DetailPlanning{" +
            "id=" + getId() +
            ", titre='" + getTitre() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            "}";
    }
}
