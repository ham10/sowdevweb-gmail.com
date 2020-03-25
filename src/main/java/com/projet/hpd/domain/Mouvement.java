package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Mouvement.
 */
@Entity
@Table(name = "mouvement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Mouvement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_mvt")
    private String codeMvt;

    @Column(name = "libelle_mvt")
    private String libelleMvt;

    @Column(name = "stock_entrant")
    private Integer stockEntrant;

    @Column(name = "stock_sortant")
    private Integer stockSortant;

    @ManyToOne
    @JsonIgnoreProperties("mouvements")
    private Produit produit;

    @ManyToOne
    @JsonIgnoreProperties("mouvements")
    private TypeMvtStock typeMvtStock;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeMvt() {
        return codeMvt;
    }

    public Mouvement codeMvt(String codeMvt) {
        this.codeMvt = codeMvt;
        return this;
    }

    public void setCodeMvt(String codeMvt) {
        this.codeMvt = codeMvt;
    }

    public String getLibelleMvt() {
        return libelleMvt;
    }

    public Mouvement libelleMvt(String libelleMvt) {
        this.libelleMvt = libelleMvt;
        return this;
    }

    public void setLibelleMvt(String libelleMvt) {
        this.libelleMvt = libelleMvt;
    }

    public Integer getStockEntrant() {
        return stockEntrant;
    }

    public Mouvement stockEntrant(Integer stockEntrant) {
        this.stockEntrant = stockEntrant;
        return this;
    }

    public void setStockEntrant(Integer stockEntrant) {
        this.stockEntrant = stockEntrant;
    }

    public Integer getStockSortant() {
        return stockSortant;
    }

    public Mouvement stockSortant(Integer stockSortant) {
        this.stockSortant = stockSortant;
        return this;
    }

    public void setStockSortant(Integer stockSortant) {
        this.stockSortant = stockSortant;
    }

    public Produit getProduit() {
        return produit;
    }

    public Mouvement produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public TypeMvtStock getTypeMvtStock() {
        return typeMvtStock;
    }

    public Mouvement typeMvtStock(TypeMvtStock typeMvtStock) {
        this.typeMvtStock = typeMvtStock;
        return this;
    }

    public void setTypeMvtStock(TypeMvtStock typeMvtStock) {
        this.typeMvtStock = typeMvtStock;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mouvement)) {
            return false;
        }
        return id != null && id.equals(((Mouvement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Mouvement{" +
            "id=" + getId() +
            ", codeMvt='" + getCodeMvt() + "'" +
            ", libelleMvt='" + getLibelleMvt() + "'" +
            ", stockEntrant=" + getStockEntrant() +
            ", stockSortant=" + getStockSortant() +
            "}";
    }
}
