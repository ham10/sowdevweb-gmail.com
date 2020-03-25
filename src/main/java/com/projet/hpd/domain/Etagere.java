package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Etagere.
 */
@Entity
@Table(name = "etagere")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Etagere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "etagere")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Produit> produits = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("etageres")
    private Rayon rayon;

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

    public Etagere code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Etagere libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public Etagere produits(Set<Produit> produits) {
        this.produits = produits;
        return this;
    }

    public Etagere addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setEtagere(this);
        return this;
    }

    public Etagere removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setEtagere(null);
        return this;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }

    public Rayon getRayon() {
        return rayon;
    }

    public Etagere rayon(Rayon rayon) {
        this.rayon = rayon;
        return this;
    }

    public void setRayon(Rayon rayon) {
        this.rayon = rayon;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Etagere)) {
            return false;
        }
        return id != null && id.equals(((Etagere) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Etagere{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
