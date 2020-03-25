package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Inventaire.
 */
@Entity
@Table(name = "inventaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inventaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "date")
    private String date;

    @Column(name = "quantite_entrant")
    private Double quantiteEntrant;

    @Column(name = "quantite_initiale")
    private Double quantiteInitiale;

    @Column(name = "quantite_sortant")
    private Double quantiteSortant;

    @Column(name = "nombre_sortant")
    private Integer nombreSortant;

    @Column(name = "nombre_livraison")
    private Integer nombreLivraison;

    @Column(name = "nombre_retour")
    private Integer nombreRetour;

    @ManyToOne
    @JsonIgnoreProperties("inventaires")
    private Produit produit;

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

    public Inventaire code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public Inventaire date(String date) {
        this.date = date;
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getQuantiteEntrant() {
        return quantiteEntrant;
    }

    public Inventaire quantiteEntrant(Double quantiteEntrant) {
        this.quantiteEntrant = quantiteEntrant;
        return this;
    }

    public void setQuantiteEntrant(Double quantiteEntrant) {
        this.quantiteEntrant = quantiteEntrant;
    }

    public Double getQuantiteInitiale() {
        return quantiteInitiale;
    }

    public Inventaire quantiteInitiale(Double quantiteInitiale) {
        this.quantiteInitiale = quantiteInitiale;
        return this;
    }

    public void setQuantiteInitiale(Double quantiteInitiale) {
        this.quantiteInitiale = quantiteInitiale;
    }

    public Double getQuantiteSortant() {
        return quantiteSortant;
    }

    public Inventaire quantiteSortant(Double quantiteSortant) {
        this.quantiteSortant = quantiteSortant;
        return this;
    }

    public void setQuantiteSortant(Double quantiteSortant) {
        this.quantiteSortant = quantiteSortant;
    }

    public Integer getNombreSortant() {
        return nombreSortant;
    }

    public Inventaire nombreSortant(Integer nombreSortant) {
        this.nombreSortant = nombreSortant;
        return this;
    }

    public void setNombreSortant(Integer nombreSortant) {
        this.nombreSortant = nombreSortant;
    }

    public Integer getNombreLivraison() {
        return nombreLivraison;
    }

    public Inventaire nombreLivraison(Integer nombreLivraison) {
        this.nombreLivraison = nombreLivraison;
        return this;
    }

    public void setNombreLivraison(Integer nombreLivraison) {
        this.nombreLivraison = nombreLivraison;
    }

    public Integer getNombreRetour() {
        return nombreRetour;
    }

    public Inventaire nombreRetour(Integer nombreRetour) {
        this.nombreRetour = nombreRetour;
        return this;
    }

    public void setNombreRetour(Integer nombreRetour) {
        this.nombreRetour = nombreRetour;
    }

    public Produit getProduit() {
        return produit;
    }

    public Inventaire produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inventaire)) {
            return false;
        }
        return id != null && id.equals(((Inventaire) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Inventaire{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", date='" + getDate() + "'" +
            ", quantiteEntrant=" + getQuantiteEntrant() +
            ", quantiteInitiale=" + getQuantiteInitiale() +
            ", quantiteSortant=" + getQuantiteSortant() +
            ", nombreSortant=" + getNombreSortant() +
            ", nombreLivraison=" + getNombreLivraison() +
            ", nombreRetour=" + getNombreRetour() +
            "}";
    }
}
