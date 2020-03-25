package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Rayon.
 */
@Entity
@Table(name = "rayon")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rayon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_rayon")
    private String codeRayon;

    @Column(name = "libelle_rayon")
    private String libelleRayon;

    @OneToMany(mappedBy = "rayon")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Etagere> etageres = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("rayons")
    private Depot rayon;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeRayon() {
        return codeRayon;
    }

    public Rayon codeRayon(String codeRayon) {
        this.codeRayon = codeRayon;
        return this;
    }

    public void setCodeRayon(String codeRayon) {
        this.codeRayon = codeRayon;
    }

    public String getLibelleRayon() {
        return libelleRayon;
    }

    public Rayon libelleRayon(String libelleRayon) {
        this.libelleRayon = libelleRayon;
        return this;
    }

    public void setLibelleRayon(String libelleRayon) {
        this.libelleRayon = libelleRayon;
    }

    public Set<Etagere> getEtageres() {
        return etageres;
    }

    public Rayon etageres(Set<Etagere> etageres) {
        this.etageres = etageres;
        return this;
    }

    public Rayon addEtagere(Etagere etagere) {
        this.etageres.add(etagere);
        etagere.setRayon(this);
        return this;
    }

    public Rayon removeEtagere(Etagere etagere) {
        this.etageres.remove(etagere);
        etagere.setRayon(null);
        return this;
    }

    public void setEtageres(Set<Etagere> etageres) {
        this.etageres = etageres;
    }

    public Depot getRayon() {
        return rayon;
    }

    public Rayon rayon(Depot depot) {
        this.rayon = depot;
        return this;
    }

    public void setRayon(Depot depot) {
        this.rayon = depot;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rayon)) {
            return false;
        }
        return id != null && id.equals(((Rayon) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Rayon{" +
            "id=" + getId() +
            ", codeRayon='" + getCodeRayon() + "'" +
            ", libelleRayon='" + getLibelleRayon() + "'" +
            "}";
    }
}
