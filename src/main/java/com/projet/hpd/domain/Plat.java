package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Plat.
 */
@Entity
@Table(name = "plat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Plat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "type_repas")
    private String typeRepas;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties("plats")
    private TypePlat typePlat;

    @ManyToOne
    @JsonIgnoreProperties("plats")
    private Services serv;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public Plat quantite(Integer quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public String getTypeRepas() {
        return typeRepas;
    }

    public Plat typeRepas(String typeRepas) {
        this.typeRepas = typeRepas;
        return this;
    }

    public void setTypeRepas(String typeRepas) {
        this.typeRepas = typeRepas;
    }

    public LocalDate getDate() {
        return date;
    }

    public Plat date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public TypePlat getTypePlat() {
        return typePlat;
    }

    public Plat typePlat(TypePlat typePlat) {
        this.typePlat = typePlat;
        return this;
    }

    public void setTypePlat(TypePlat typePlat) {
        this.typePlat = typePlat;
    }

    public Services getServ() {
        return serv;
    }

    public Plat serv(Services services) {
        this.serv = services;
        return this;
    }

    public void setServ(Services services) {
        this.serv = services;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plat)) {
            return false;
        }
        return id != null && id.equals(((Plat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Plat{" +
            "id=" + getId() +
            ", quantite=" + getQuantite() +
            ", typeRepas='" + getTypeRepas() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
