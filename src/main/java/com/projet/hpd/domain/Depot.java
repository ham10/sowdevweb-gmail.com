package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Depot.
 */
@Entity
@Table(name = "depot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Depot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_depot")
    private Integer codeDepot;

    @Column(name = "libelle_depot")
    private String libelleDepot;

    @OneToMany(mappedBy = "rayon")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Rayon> rayons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodeDepot() {
        return codeDepot;
    }

    public Depot codeDepot(Integer codeDepot) {
        this.codeDepot = codeDepot;
        return this;
    }

    public void setCodeDepot(Integer codeDepot) {
        this.codeDepot = codeDepot;
    }

    public String getLibelleDepot() {
        return libelleDepot;
    }

    public Depot libelleDepot(String libelleDepot) {
        this.libelleDepot = libelleDepot;
        return this;
    }

    public void setLibelleDepot(String libelleDepot) {
        this.libelleDepot = libelleDepot;
    }

    public Set<Rayon> getRayons() {
        return rayons;
    }

    public Depot rayons(Set<Rayon> rayons) {
        this.rayons = rayons;
        return this;
    }

    public Depot addRayon(Rayon rayon) {
        this.rayons.add(rayon);
        rayon.setRayon(this);
        return this;
    }

    public Depot removeRayon(Rayon rayon) {
        this.rayons.remove(rayon);
        rayon.setRayon(null);
        return this;
    }

    public void setRayons(Set<Rayon> rayons) {
        this.rayons = rayons;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Depot)) {
            return false;
        }
        return id != null && id.equals(((Depot) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Depot{" +
            "id=" + getId() +
            ", codeDepot=" + getCodeDepot() +
            ", libelleDepot='" + getLibelleDepot() + "'" +
            "}";
    }
}
