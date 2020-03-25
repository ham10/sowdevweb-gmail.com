package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "quantite_stock")
    private Integer quantiteStock;

    @Column(name = "stock_provisoire")
    private Integer stockProvisoire;

    @Column(name = "tva")
    private Boolean tva;

    @Column(name = "prix_vente_unitaire")
    private Integer prixVenteUnitaire;

    @Column(name = "code_barre")
    private String codeBarre;

    @Column(name = "seuil")
    private Integer seuil;

    @OneToMany(mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TypeCond> typeConds = new HashSet<>();

    @OneToMany(mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Mouvement> mouvements = new HashSet<>();

    @OneToMany(mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inventaire> inventaires = new HashSet<>();

    @OneToMany(mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProdFournis> prodFournis = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("produits")
    private Etagere etagere;

    @ManyToOne
    @JsonIgnoreProperties("produits")
    private TypeProd typeproduit;

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

    public Produit code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Produit libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getQuantiteStock() {
        return quantiteStock;
    }

    public Produit quantiteStock(Integer quantiteStock) {
        this.quantiteStock = quantiteStock;
        return this;
    }

    public void setQuantiteStock(Integer quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public Integer getStockProvisoire() {
        return stockProvisoire;
    }

    public Produit stockProvisoire(Integer stockProvisoire) {
        this.stockProvisoire = stockProvisoire;
        return this;
    }

    public void setStockProvisoire(Integer stockProvisoire) {
        this.stockProvisoire = stockProvisoire;
    }

    public Boolean isTva() {
        return tva;
    }

    public Produit tva(Boolean tva) {
        this.tva = tva;
        return this;
    }

    public void setTva(Boolean tva) {
        this.tva = tva;
    }

    public Integer getPrixVenteUnitaire() {
        return prixVenteUnitaire;
    }

    public Produit prixVenteUnitaire(Integer prixVenteUnitaire) {
        this.prixVenteUnitaire = prixVenteUnitaire;
        return this;
    }

    public void setPrixVenteUnitaire(Integer prixVenteUnitaire) {
        this.prixVenteUnitaire = prixVenteUnitaire;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public Produit codeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
        return this;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public Integer getSeuil() {
        return seuil;
    }

    public Produit seuil(Integer seuil) {
        this.seuil = seuil;
        return this;
    }

    public void setSeuil(Integer seuil) {
        this.seuil = seuil;
    }

    public Set<TypeCond> getTypeConds() {
        return typeConds;
    }

    public Produit typeConds(Set<TypeCond> typeConds) {
        this.typeConds = typeConds;
        return this;
    }

    public Produit addTypeCond(TypeCond typeCond) {
        this.typeConds.add(typeCond);
        typeCond.setProduit(this);
        return this;
    }

    public Produit removeTypeCond(TypeCond typeCond) {
        this.typeConds.remove(typeCond);
        typeCond.setProduit(null);
        return this;
    }

    public void setTypeConds(Set<TypeCond> typeConds) {
        this.typeConds = typeConds;
    }

    public Set<Mouvement> getMouvements() {
        return mouvements;
    }

    public Produit mouvements(Set<Mouvement> mouvements) {
        this.mouvements = mouvements;
        return this;
    }

    public Produit addMouvement(Mouvement mouvement) {
        this.mouvements.add(mouvement);
        mouvement.setProduit(this);
        return this;
    }

    public Produit removeMouvement(Mouvement mouvement) {
        this.mouvements.remove(mouvement);
        mouvement.setProduit(null);
        return this;
    }

    public void setMouvements(Set<Mouvement> mouvements) {
        this.mouvements = mouvements;
    }

    public Set<Inventaire> getInventaires() {
        return inventaires;
    }

    public Produit inventaires(Set<Inventaire> inventaires) {
        this.inventaires = inventaires;
        return this;
    }

    public Produit addInventaire(Inventaire inventaire) {
        this.inventaires.add(inventaire);
        inventaire.setProduit(this);
        return this;
    }

    public Produit removeInventaire(Inventaire inventaire) {
        this.inventaires.remove(inventaire);
        inventaire.setProduit(null);
        return this;
    }

    public void setInventaires(Set<Inventaire> inventaires) {
        this.inventaires = inventaires;
    }

    public Set<ProdFournis> getProdFournis() {
        return prodFournis;
    }

    public Produit prodFournis(Set<ProdFournis> prodFournis) {
        this.prodFournis = prodFournis;
        return this;
    }

    public Produit addProdFournis(ProdFournis prodFournis) {
        this.prodFournis.add(prodFournis);
        prodFournis.setProduit(this);
        return this;
    }

    public Produit removeProdFournis(ProdFournis prodFournis) {
        this.prodFournis.remove(prodFournis);
        prodFournis.setProduit(null);
        return this;
    }

    public void setProdFournis(Set<ProdFournis> prodFournis) {
        this.prodFournis = prodFournis;
    }

    public Etagere getEtagere() {
        return etagere;
    }

    public Produit etagere(Etagere etagere) {
        this.etagere = etagere;
        return this;
    }

    public void setEtagere(Etagere etagere) {
        this.etagere = etagere;
    }

    public TypeProd getTypeproduit() {
        return typeproduit;
    }

    public Produit typeproduit(TypeProd typeProd) {
        this.typeproduit = typeProd;
        return this;
    }

    public void setTypeproduit(TypeProd typeProd) {
        this.typeproduit = typeProd;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return id != null && id.equals(((Produit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Produit{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", quantiteStock=" + getQuantiteStock() +
            ", stockProvisoire=" + getStockProvisoire() +
            ", tva='" + isTva() + "'" +
            ", prixVenteUnitaire=" + getPrixVenteUnitaire() +
            ", codeBarre='" + getCodeBarre() + "'" +
            ", seuil=" + getSeuil() +
            "}";
    }
}
