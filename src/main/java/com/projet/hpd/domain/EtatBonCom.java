package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EtatBonCom.
 */
@Entity
@Table(name = "etat_bon_com")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EtatBonCom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "etatBonCommande")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BonDeCommande> bonDeCommandes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public EtatBonCom libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public EtatBonCom code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<BonDeCommande> getBonDeCommandes() {
        return bonDeCommandes;
    }

    public EtatBonCom bonDeCommandes(Set<BonDeCommande> bonDeCommandes) {
        this.bonDeCommandes = bonDeCommandes;
        return this;
    }

    public EtatBonCom addBonDeCommande(BonDeCommande bonDeCommande) {
        this.bonDeCommandes.add(bonDeCommande);
        bonDeCommande.setEtatBonCommande(this);
        return this;
    }

    public EtatBonCom removeBonDeCommande(BonDeCommande bonDeCommande) {
        this.bonDeCommandes.remove(bonDeCommande);
        bonDeCommande.setEtatBonCommande(null);
        return this;
    }

    public void setBonDeCommandes(Set<BonDeCommande> bonDeCommandes) {
        this.bonDeCommandes = bonDeCommandes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtatBonCom)) {
            return false;
        }
        return id != null && id.equals(((EtatBonCom) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EtatBonCom{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
