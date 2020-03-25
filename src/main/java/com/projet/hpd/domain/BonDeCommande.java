package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A BonDeCommande.
 */
@Entity
@Table(name = "bon_de_commande")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BonDeCommande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "prix_total")
    private Double prixTotal;

    @Column(name = "date_comm")
    private LocalDate dateComm;

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

    @OneToOne
    @JoinColumn(unique = true)
    private BonLivraison bonLivraison;

    @OneToMany(mappedBy = "bonDeCommande")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LigneCommande> ligneCommandes = new HashSet<>();

    @OneToOne(mappedBy = "bonDeCommande")
    @JsonIgnore
    private Offre offre;

    @ManyToOne
    @JsonIgnoreProperties("bonDeCommandes")
    private Services serv;

    @ManyToOne
    @JsonIgnoreProperties("bonDeCommandes")
    private EtatBonCom etatBonCommande;

    @ManyToOne
    @JsonIgnoreProperties("bonDeCommandes")
    private TypeBonCom typeBonDeCommande;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public BonDeCommande numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLibelle() {
        return libelle;
    }

    public BonDeCommande libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrixTotal() {
        return prixTotal;
    }

    public BonDeCommande prixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
        return this;
    }

    public void setPrixTotal(Double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public LocalDate getDateComm() {
        return dateComm;
    }

    public BonDeCommande dateComm(LocalDate dateComm) {
        this.dateComm = dateComm;
        return this;
    }

    public void setDateComm(LocalDate dateComm) {
        this.dateComm = dateComm;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public BonDeCommande dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public BonDeCommande dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public BonDeCommande dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public BonDeCommande userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public BonDeCommande userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public BonDeCommande userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public BonLivraison getBonLivraison() {
        return bonLivraison;
    }

    public BonDeCommande bonLivraison(BonLivraison bonLivraison) {
        this.bonLivraison = bonLivraison;
        return this;
    }

    public void setBonLivraison(BonLivraison bonLivraison) {
        this.bonLivraison = bonLivraison;
    }

    public Set<LigneCommande> getLigneCommandes() {
        return ligneCommandes;
    }

    public BonDeCommande ligneCommandes(Set<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
        return this;
    }

    public BonDeCommande addLigneCommande(LigneCommande ligneCommande) {
        this.ligneCommandes.add(ligneCommande);
        ligneCommande.setBonDeCommande(this);
        return this;
    }

    public BonDeCommande removeLigneCommande(LigneCommande ligneCommande) {
        this.ligneCommandes.remove(ligneCommande);
        ligneCommande.setBonDeCommande(null);
        return this;
    }

    public void setLigneCommandes(Set<LigneCommande> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }

    public Offre getOffre() {
        return offre;
    }

    public BonDeCommande offre(Offre offre) {
        this.offre = offre;
        return this;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public Services getServ() {
        return serv;
    }

    public BonDeCommande serv(Services services) {
        this.serv = services;
        return this;
    }

    public void setServ(Services services) {
        this.serv = services;
    }

    public EtatBonCom getEtatBonCommande() {
        return etatBonCommande;
    }

    public BonDeCommande etatBonCommande(EtatBonCom etatBonCom) {
        this.etatBonCommande = etatBonCom;
        return this;
    }

    public void setEtatBonCommande(EtatBonCom etatBonCom) {
        this.etatBonCommande = etatBonCom;
    }

    public TypeBonCom getTypeBonDeCommande() {
        return typeBonDeCommande;
    }

    public BonDeCommande typeBonDeCommande(TypeBonCom typeBonCom) {
        this.typeBonDeCommande = typeBonCom;
        return this;
    }

    public void setTypeBonDeCommande(TypeBonCom typeBonCom) {
        this.typeBonDeCommande = typeBonCom;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonDeCommande)) {
            return false;
        }
        return id != null && id.equals(((BonDeCommande) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BonDeCommande{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", prixTotal=" + getPrixTotal() +
            ", dateComm='" + getDateComm() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
