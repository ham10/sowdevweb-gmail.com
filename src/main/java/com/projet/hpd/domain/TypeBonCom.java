package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeBonCom.
 */
@Entity
@Table(name = "type_bon_com")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeBonCom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle_type_bon_com")
    private String libelleTypeBonCom;

    @Column(name = "code_type_bon_com")
    private String codeTypeBonCom;

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

    @OneToMany(mappedBy = "typeBonDeCommande")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BonDeCommande> bonDeCommandes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleTypeBonCom() {
        return libelleTypeBonCom;
    }

    public TypeBonCom libelleTypeBonCom(String libelleTypeBonCom) {
        this.libelleTypeBonCom = libelleTypeBonCom;
        return this;
    }

    public void setLibelleTypeBonCom(String libelleTypeBonCom) {
        this.libelleTypeBonCom = libelleTypeBonCom;
    }

    public String getCodeTypeBonCom() {
        return codeTypeBonCom;
    }

    public TypeBonCom codeTypeBonCom(String codeTypeBonCom) {
        this.codeTypeBonCom = codeTypeBonCom;
        return this;
    }

    public void setCodeTypeBonCom(String codeTypeBonCom) {
        this.codeTypeBonCom = codeTypeBonCom;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypeBonCom dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypeBonCom dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypeBonCom userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypeBonCom userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypeBonCom userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<BonDeCommande> getBonDeCommandes() {
        return bonDeCommandes;
    }

    public TypeBonCom bonDeCommandes(Set<BonDeCommande> bonDeCommandes) {
        this.bonDeCommandes = bonDeCommandes;
        return this;
    }

    public TypeBonCom addBonDeCommande(BonDeCommande bonDeCommande) {
        this.bonDeCommandes.add(bonDeCommande);
        bonDeCommande.setTypeBonDeCommande(this);
        return this;
    }

    public TypeBonCom removeBonDeCommande(BonDeCommande bonDeCommande) {
        this.bonDeCommandes.remove(bonDeCommande);
        bonDeCommande.setTypeBonDeCommande(null);
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
        if (!(o instanceof TypeBonCom)) {
            return false;
        }
        return id != null && id.equals(((TypeBonCom) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeBonCom{" +
            "id=" + getId() +
            ", libelleTypeBonCom='" + getLibelleTypeBonCom() + "'" +
            ", codeTypeBonCom='" + getCodeTypeBonCom() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
