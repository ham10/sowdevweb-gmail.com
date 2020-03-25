package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeProd.
 */
@Entity
@Table(name = "type_prod")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeProd implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_prod")
    private String codeTypeProd;

    @Column(name = "libelle_type_prod")
    private String libelleTypeProd;

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

    @OneToMany(mappedBy = "typeproduit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Produit> produits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypeProd() {
        return codeTypeProd;
    }

    public TypeProd codeTypeProd(String codeTypeProd) {
        this.codeTypeProd = codeTypeProd;
        return this;
    }

    public void setCodeTypeProd(String codeTypeProd) {
        this.codeTypeProd = codeTypeProd;
    }

    public String getLibelleTypeProd() {
        return libelleTypeProd;
    }

    public TypeProd libelleTypeProd(String libelleTypeProd) {
        this.libelleTypeProd = libelleTypeProd;
        return this;
    }

    public void setLibelleTypeProd(String libelleTypeProd) {
        this.libelleTypeProd = libelleTypeProd;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypeProd dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypeProd dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypeProd userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypeProd userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypeProd userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public TypeProd produits(Set<Produit> produits) {
        this.produits = produits;
        return this;
    }

    public TypeProd addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setTypeproduit(this);
        return this;
    }

    public TypeProd removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setTypeproduit(null);
        return this;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeProd)) {
            return false;
        }
        return id != null && id.equals(((TypeProd) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeProd{" +
            "id=" + getId() +
            ", codeTypeProd='" + getCodeTypeProd() + "'" +
            ", libelleTypeProd='" + getLibelleTypeProd() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
