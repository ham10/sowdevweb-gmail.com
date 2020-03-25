package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ProdFournis.
 */
@Entity
@Table(name = "prod_fournis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProdFournis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "stock")
    private String stock;

    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "produitFournisseur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LigneLivraison> ligneLivraisons = new HashSet<>();

    @OneToMany(mappedBy = "produitFournisseur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LigneCommande> ligneCommandes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("prodFournis")
    private Produit produit;

    @ManyToOne
    @JsonIgnoreProperties("prodFournis")
    private Fournisseur fournisseur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStock() {
        return stock;
    }

    public ProdFournis stock(String stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getNom() {
        return nom;
    }

    public ProdFournis nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<LigneLivraison> getLigneLivraisons() {
        return ligneLivraisons;
    }

    public ProdFournis ligneLivraisons(Set<LigneLivraison> ligneLivraisons) {
        this.ligneLivraisons = ligneLivraisons;
        return this;
    }

    public ProdFournis addLigneLivraison(LigneLivraison ligneLivraison) {
        this.ligneLivraisons.add(ligneLivraison);
        ligneLivraison.setProduitFournisseur(this);
        return this;
    }

    public ProdFournis removeLigneLivraison(LigneLivraison ligneLivraison) {
        this.ligneLivraisons.remove(ligneLivraison);
        ligneLivraison.setProduitFournisseur(null);
        return this;
    }

    public void setLigneLivraisons(Set<LigneLivraison> ligneLivraisons) {
        this.ligneLivraisons = ligneLivraisons;
    }

    public Set<LigneCommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public ProdFournis ligneCommandes(Set<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
        return this;
    }

    public ProdFournis addLigneCommande(LigneCommande ligneCommande) {
        this.ligneCommandes.add(ligneCommande);
        ligneCommande.setProduitFournisseur(this);
        return this;
    }

    public ProdFournis removeLigneCommande(LigneCommande ligneCommande) {
        this.ligneCommandes.remove(ligneCommande);
        ligneCommande.setProduitFournisseur(null);
        return this;
    }

    public void setLigneCommandes(Set<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }

    public Produit getProduit() {
        return produit;
    }

    public ProdFournis produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public ProdFournis fournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
        return this;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdFournis)) {
            return false;
        }
        return id != null && id.equals(((ProdFournis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProdFournis{" +
            "id=" + getId() +
            ", stock='" + getStock() + "'" +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
