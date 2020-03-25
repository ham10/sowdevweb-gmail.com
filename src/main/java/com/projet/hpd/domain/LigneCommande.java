package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A LigneCommande.
 */
@Entity
@Table(name = "ligne_commande")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LigneCommande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "produit")
    private String produit;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "prix_unitaire")
    private Double prixUnitaire;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_updated")
    private Long userUpdated;

    @Column(name = "user_deleted")
    private Long userDeleted;

    @ManyToOne
    @JsonIgnoreProperties("ligneCommandes")
    private ProdFournis produitFournisseur;

    @ManyToOne
    @JsonIgnoreProperties("ligneCommandes")
    private BonDeCommande bonDeCommande;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduit() {
        return produit;
    }

    public LigneCommande produit(String produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public LigneCommande quantite(Integer quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public LigneCommande prixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        return this;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public LigneCommande dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public LigneCommande dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public LigneCommande userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public LigneCommande userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public LigneCommande userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public ProdFournis getProduitFournisseur() {
        return produitFournisseur;
    }

    public LigneCommande produitFournisseur(ProdFournis prodFournis) {
        this.produitFournisseur = prodFournis;
        return this;
    }

    public void setProduitFournisseur(ProdFournis prodFournis) {
        this.produitFournisseur = prodFournis;
    }

    public BonDeCommande getBonDeCommande() {
        return bonDeCommande;
    }

    public LigneCommande bonDeCommande(BonDeCommande bonDeCommande) {
        this.bonDeCommande = bonDeCommande;
        return this;
    }

    public void setBonDeCommande(BonDeCommande bonDeCommande) {
        this.bonDeCommande = bonDeCommande;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LigneCommande)) {
            return false;
        }
        return id != null && id.equals(((LigneCommande) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LigneCommande{" +
            "id=" + getId() +
            ", produit='" + getProduit() + "'" +
            ", quantite=" + getQuantite() +
            ", prixUnitaire=" + getPrixUnitaire() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
