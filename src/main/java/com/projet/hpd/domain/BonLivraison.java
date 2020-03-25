package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A BonLivraison.
 */
@Entity
@Table(name = "bon_livraison")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BonLivraison implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "designation_bon_livraison")
    private String designationBonLivraison;

    @Column(name = "prix_total_bon_livraison")
    private Double prixTotalBonLivraison;

    @Column(name = "date_bon_livraison")
    private LocalDate dateBonLivraison;

    @Column(name = "user_certified")
    private Integer userCertified;

    @Column(name = "date_certif")
    private LocalDate dateCertif;

    @Column(name = "num_certif")
    private String numCertif;

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

    @OneToMany(mappedBy = "bonLivraison")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Facture> factures = new HashSet<>();

    @OneToMany(mappedBy = "bonDeLivraison")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LigneLivraison> ligneLivraisons = new HashSet<>();

    @OneToOne(mappedBy = "bonLivraison")
    @JsonIgnore
    private BonDeCommande bonDeCommande;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignationBonLivraison() {
        return designationBonLivraison;
    }

    public BonLivraison designationBonLivraison(String designationBonLivraison) {
        this.designationBonLivraison = designationBonLivraison;
        return this;
    }

    public void setDesignationBonLivraison(String designationBonLivraison) {
        this.designationBonLivraison = designationBonLivraison;
    }

    public Double getPrixTotalBonLivraison() {
        return prixTotalBonLivraison;
    }

    public BonLivraison prixTotalBonLivraison(Double prixTotalBonLivraison) {
        this.prixTotalBonLivraison = prixTotalBonLivraison;
        return this;
    }

    public void setPrixTotalBonLivraison(Double prixTotalBonLivraison) {
        this.prixTotalBonLivraison = prixTotalBonLivraison;
    }

    public LocalDate getDateBonLivraison() {
        return dateBonLivraison;
    }

    public BonLivraison dateBonLivraison(LocalDate dateBonLivraison) {
        this.dateBonLivraison = dateBonLivraison;
        return this;
    }

    public void setDateBonLivraison(LocalDate dateBonLivraison) {
        this.dateBonLivraison = dateBonLivraison;
    }

    public Integer getUserCertified() {
        return userCertified;
    }

    public BonLivraison userCertified(Integer userCertified) {
        this.userCertified = userCertified;
        return this;
    }

    public void setUserCertified(Integer userCertified) {
        this.userCertified = userCertified;
    }

    public LocalDate getDateCertif() {
        return dateCertif;
    }

    public BonLivraison dateCertif(LocalDate dateCertif) {
        this.dateCertif = dateCertif;
        return this;
    }

    public void setDateCertif(LocalDate dateCertif) {
        this.dateCertif = dateCertif;
    }

    public String getNumCertif() {
        return numCertif;
    }

    public BonLivraison numCertif(String numCertif) {
        this.numCertif = numCertif;
        return this;
    }

    public void setNumCertif(String numCertif) {
        this.numCertif = numCertif;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public BonLivraison dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public BonLivraison dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public BonLivraison dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public BonLivraison userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public BonLivraison userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public BonLivraison userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Facture> getFactures() {
        return factures;
    }

    public BonLivraison factures(Set<Facture> factures) {
        this.factures = factures;
        return this;
    }

    public BonLivraison addFacture(Facture facture) {
        this.factures.add(facture);
        facture.setBonLivraison(this);
        return this;
    }

    public BonLivraison removeFacture(Facture facture) {
        this.factures.remove(facture);
        facture.setBonLivraison(null);
        return this;
    }

    public void setFactures(Set<Facture> factures) {
        this.factures = factures;
    }

    public Set<LigneLivraison> getLigneLivraisons() {
        return ligneLivraisons;
    }

    public BonLivraison ligneLivraisons(Set<LigneLivraison> ligneLivraisons) {
        this.ligneLivraisons = ligneLivraisons;
        return this;
    }

    public BonLivraison addLigneLivraison(LigneLivraison ligneLivraison) {
        this.ligneLivraisons.add(ligneLivraison);
        ligneLivraison.setBonDeLivraison(this);
        return this;
    }

    public BonLivraison removeLigneLivraison(LigneLivraison ligneLivraison) {
        this.ligneLivraisons.remove(ligneLivraison);
        ligneLivraison.setBonDeLivraison(null);
        return this;
    }

    public void setLigneLivraisons(Set<LigneLivraison> ligneLivraisons) {
        this.ligneLivraisons = ligneLivraisons;
    }

    public BonDeCommande getBonDeCommande() {
        return bonDeCommande;
    }

    public BonLivraison bonDeCommande(BonDeCommande bonDeCommande) {
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
        if (!(o instanceof BonLivraison)) {
            return false;
        }
        return id != null && id.equals(((BonLivraison) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BonLivraison{" +
            "id=" + getId() +
            ", designationBonLivraison='" + getDesignationBonLivraison() + "'" +
            ", prixTotalBonLivraison=" + getPrixTotalBonLivraison() +
            ", dateBonLivraison='" + getDateBonLivraison() + "'" +
            ", userCertified=" + getUserCertified() +
            ", dateCertif='" + getDateCertif() + "'" +
            ", numCertif='" + getNumCertif() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
