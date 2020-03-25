package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Civilite.
 */
@Entity
@Table(name = "civilite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Civilite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle_civilite")
    private String libelleCivilite;

    @Column(name = "intitule_civilite")
    private String intituleCivilite;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleCivilite() {
        return libelleCivilite;
    }

    public Civilite libelleCivilite(String libelleCivilite) {
        this.libelleCivilite = libelleCivilite;
        return this;
    }

    public void setLibelleCivilite(String libelleCivilite) {
        this.libelleCivilite = libelleCivilite;
    }

    public String getIntituleCivilite() {
        return intituleCivilite;
    }

    public Civilite intituleCivilite(String intituleCivilite) {
        this.intituleCivilite = intituleCivilite;
        return this;
    }

    public void setIntituleCivilite(String intituleCivilite) {
        this.intituleCivilite = intituleCivilite;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Civilite)) {
            return false;
        }
        return id != null && id.equals(((Civilite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Civilite{" +
            "id=" + getId() +
            ", libelleCivilite='" + getLibelleCivilite() + "'" +
            ", intituleCivilite='" + getIntituleCivilite() + "'" +
            "}";
    }
}
