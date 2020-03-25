package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Echeancier.
 */
@Entity
@Table(name = "echeancier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Echeancier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "numero")
    private String numero;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "date_paiement")
    private LocalDate datePaiement;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "montant_paye")
    private Double montantPaye;

    @Column(name = "capital")
    private Double capital;

    @Column(name = "frais")
    private Double frais;

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
    @JsonIgnoreProperties("echeanciers")
    private Facture facture;

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

    public Echeancier code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNumero() {
        return numero;
    }

    public Echeancier numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDate() {
        return date;
    }

    public Echeancier date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public Echeancier datePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
        return this;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Double getMontant() {
        return montant;
    }

    public Echeancier montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getMontantPaye() {
        return montantPaye;
    }

    public Echeancier montantPaye(Double montantPaye) {
        this.montantPaye = montantPaye;
        return this;
    }

    public void setMontantPaye(Double montantPaye) {
        this.montantPaye = montantPaye;
    }

    public Double getCapital() {
        return capital;
    }

    public Echeancier capital(Double capital) {
        this.capital = capital;
        return this;
    }

    public void setCapital(Double capital) {
        this.capital = capital;
    }

    public Double getFrais() {
        return frais;
    }

    public Echeancier frais(Double frais) {
        this.frais = frais;
        return this;
    }

    public void setFrais(Double frais) {
        this.frais = frais;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Echeancier dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Echeancier dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public Echeancier dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Echeancier userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Echeancier userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Echeancier userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Facture getFacture() {
        return facture;
    }

    public Echeancier facture(Facture facture) {
        this.facture = facture;
        return this;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Echeancier)) {
            return false;
        }
        return id != null && id.equals(((Echeancier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Echeancier{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", numero='" + getNumero() + "'" +
            ", date='" + getDate() + "'" +
            ", datePaiement='" + getDatePaiement() + "'" +
            ", montant=" + getMontant() +
            ", montantPaye=" + getMontantPaye() +
            ", capital=" + getCapital() +
            ", frais=" + getFrais() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
