package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Offre.
 */
@Entity
@Table(name = "offre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Offre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    private LocalDate libelle;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "taxe")
    private Double taxe;

    @Column(name = "num_marche")
    private String numMarche;

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
    private BonDeCommande bonDeCommande;

    @ManyToOne
    @JsonIgnoreProperties("offres")
    private Fournisseur fournisseur;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLibelle() {
        return libelle;
    }

    public Offre libelle(LocalDate libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(LocalDate libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDate() {
        return date;
    }

    public Offre date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getMontant() {
        return montant;
    }

    public Offre montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getTaxe() {
        return taxe;
    }

    public Offre taxe(Double taxe) {
        this.taxe = taxe;
        return this;
    }

    public void setTaxe(Double taxe) {
        this.taxe = taxe;
    }

    public String getNumMarche() {
        return numMarche;
    }

    public Offre numMarche(String numMarche) {
        this.numMarche = numMarche;
        return this;
    }

    public void setNumMarche(String numMarche) {
        this.numMarche = numMarche;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Offre dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Offre dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public Offre dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Offre userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Offre userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Offre userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public BonDeCommande getBonDeCommande() {
        return bonDeCommande;
    }

    public Offre bonDeCommande(BonDeCommande bonDeCommande) {
        this.bonDeCommande = bonDeCommande;
        return this;
    }

    public void setBonDeCommande(BonDeCommande bonDeCommande) {
        this.bonDeCommande = bonDeCommande;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public Offre fournisseur(Fournisseur fournisseur) {
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
        if (!(o instanceof Offre)) {
            return false;
        }
        return id != null && id.equals(((Offre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Offre{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", date='" + getDate() + "'" +
            ", montant=" + getMontant() +
            ", taxe=" + getTaxe() +
            ", numMarche='" + getNumMarche() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
