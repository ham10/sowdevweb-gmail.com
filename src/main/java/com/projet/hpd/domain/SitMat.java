package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SitMat.
 */
@Entity
@Table(name = "sit_mat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SitMat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_sit_mat")
    private String codeSitMat;

    @Column(name = "libelle_sit_mat")
    private String libelleSitMat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeSitMat() {
        return codeSitMat;
    }

    public SitMat codeSitMat(String codeSitMat) {
        this.codeSitMat = codeSitMat;
        return this;
    }

    public void setCodeSitMat(String codeSitMat) {
        this.codeSitMat = codeSitMat;
    }

    public String getLibelleSitMat() {
        return libelleSitMat;
    }

    public SitMat libelleSitMat(String libelleSitMat) {
        this.libelleSitMat = libelleSitMat;
        return this;
    }

    public void setLibelleSitMat(String libelleSitMat) {
        this.libelleSitMat = libelleSitMat;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SitMat)) {
            return false;
        }
        return id != null && id.equals(((SitMat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SitMat{" +
            "id=" + getId() +
            ", codeSitMat='" + getCodeSitMat() + "'" +
            ", libelleSitMat='" + getLibelleSitMat() + "'" +
            "}";
    }
}
