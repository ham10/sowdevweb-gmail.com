package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Fournisseur.
 */
@Entity
@Table(name = "fournisseur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "statut")
    private String statut;

    @Column(name = "raison_sociale")
    private String raisonSociale;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "ninea")
    private String ninea;

    @Column(name = "rc")
    private String rc;

    @Column(name = "ville")
    private String ville;

    @Column(name = "email")
    private String email;

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

    @OneToMany(mappedBy = "fournisseur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Offre> offres = new HashSet<>();

    @OneToMany(mappedBy = "fournisseur")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProdFournis> prodFournis = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Fournisseur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getStatut() {
        return statut;
    }

    public Fournisseur statut(String statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public Fournisseur raisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
        return this;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getAdresse() {
        return adresse;
    }

    public Fournisseur adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public Fournisseur telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNinea() {
        return ninea;
    }

    public Fournisseur ninea(String ninea) {
        this.ninea = ninea;
        return this;
    }

    public void setNinea(String ninea) {
        this.ninea = ninea;
    }

    public String getRc() {
        return rc;
    }

    public Fournisseur rc(String rc) {
        this.rc = rc;
        return this;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getVille() {
        return ville;
    }

    public Fournisseur ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEmail() {
        return email;
    }

    public Fournisseur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Fournisseur dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Fournisseur dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Fournisseur userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Fournisseur userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Fournisseur userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Offre> getOffres() {
        return offres;
    }

    public Fournisseur offres(Set<Offre> offres) {
        this.offres = offres;
        return this;
    }

    public Fournisseur addOffre(Offre offre) {
        this.offres.add(offre);
        offre.setFournisseur(this);
        return this;
    }

    public Fournisseur removeOffre(Offre offre) {
        this.offres.remove(offre);
        offre.setFournisseur(null);
        return this;
    }

    public void setOffres(Set<Offre> offres) {
        this.offres = offres;
    }

    public Set<ProdFournis> getProdFournis() {
        return prodFournis;
    }

    public Fournisseur prodFournis(Set<ProdFournis> prodFournis) {
        this.prodFournis = prodFournis;
        return this;
    }

    public Fournisseur addProdFournis(ProdFournis prodFournis) {
        this.prodFournis.add(prodFournis);
        prodFournis.setFournisseur(this);
        return this;
    }

    public Fournisseur removeProdFournis(ProdFournis prodFournis) {
        this.prodFournis.remove(prodFournis);
        prodFournis.setFournisseur(null);
        return this;
    }

    public void setProdFournis(Set<ProdFournis> prodFournis) {
        this.prodFournis = prodFournis;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fournisseur)) {
            return false;
        }
        return id != null && id.equals(((Fournisseur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Fournisseur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", statut='" + getStatut() + "'" +
            ", raisonSociale='" + getRaisonSociale() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", ninea='" + getNinea() + "'" +
            ", rc='" + getRc() + "'" +
            ", ville='" + getVille() + "'" +
            ", email='" + getEmail() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
