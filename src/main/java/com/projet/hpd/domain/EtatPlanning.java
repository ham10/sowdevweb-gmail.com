package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EtatPlanning.
 */
@Entity
@Table(name = "etat_planning")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EtatPlanning implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "etatPlanning")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DetailPlanning> detailPlannings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public EtatPlanning code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public EtatPlanning libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<DetailPlanning> getDetailPlannings() {
        return detailPlannings;
    }

    public EtatPlanning detailPlannings(Set<DetailPlanning> detailPlannings) {
        this.detailPlannings = detailPlannings;
        return this;
    }

    public EtatPlanning addDetailPlanning(DetailPlanning detailPlanning) {
        this.detailPlannings.add(detailPlanning);
        detailPlanning.setEtatPlanning(this);
        return this;
    }

    public EtatPlanning removeDetailPlanning(DetailPlanning detailPlanning) {
        this.detailPlannings.remove(detailPlanning);
        detailPlanning.setEtatPlanning(null);
        return this;
    }

    public void setDetailPlannings(Set<DetailPlanning> detailPlannings) {
        this.detailPlannings = detailPlannings;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtatPlanning)) {
            return false;
        }
        return id != null && id.equals(((EtatPlanning) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EtatPlanning{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
