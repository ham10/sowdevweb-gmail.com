package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A LigneLivraison.
 */
@Entity
@Table(name = "ligne_livraison")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LigneLivraison implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "designation")
    private String designation;

    @Column(name = "quantite")
    private Integer quantite;

    @Column(name = "prix_unitaire")
    private Double prixUnitaire;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

    @Column(name = "date_deleted")
    private LocalDate dateDeleted;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_updated")
    private Long userUpdated;

    @Column(name = "user_deleted")
    private Long userDeleted;

    @ManyToOne
    @JsonIgnoreProperties("ligneLivraisons")
    private ProdFournis produitFournisseur;

    @ManyToOne
    @JsonIgnoreProperties("ligneLivraisons")
    private BonLivraison bonDeLivraison;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public LigneLivraison designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public LigneLivraison quantite(Integer quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public LigneLivraison prixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
        return this;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public LigneLivraison dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public LigneLivraison dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public LigneLivraison dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public LigneLivraison userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public LigneLivraison userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public LigneLivraison userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public ProdFournis getProduitFournisseur() {
        return produitFournisseur;
    }

    public LigneLivraison produitFournisseur(ProdFournis prodFournis) {
        this.produitFournisseur = prodFournis;
        return this;
    }

    public void setProduitFournisseur(ProdFournis prodFournis) {
        this.produitFournisseur = prodFournis;
    }

    public BonLivraison getBonDeLivraison() {
        return bonDeLivraison;
    }

    public LigneLivraison bonDeLivraison(BonLivraison bonLivraison) {
        this.bonDeLivraison = bonLivraison;
        return this;
    }

    public void setBonDeLivraison(BonLivraison bonLivraison) {
        this.bonDeLivraison = bonLivraison;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LigneLivraison)) {
            return false;
        }
        return id != null && id.equals(((LigneLivraison) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LigneLivraison{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", quantite=" + getQuantite() +
            ", prixUnitaire=" + getPrixUnitaire() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
