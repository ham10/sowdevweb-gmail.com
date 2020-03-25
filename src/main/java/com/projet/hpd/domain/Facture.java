package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Facture.
 */
@Entity
@Table(name = "facture")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Facture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "designation")
    private String designation;

    @Column(name = "montant_fact")
    private Double montantFact;

    @Column(name = "montant_paye")
    private Double montantPaye;

    @Column(name = "montant_global")
    private Double montantGlobal;

    @Column(name = "moratoire")
    private Boolean moratoire;

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

    @OneToMany(mappedBy = "operation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Operation> operations = new HashSet<>();

    @OneToMany(mappedBy = "facture")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Echeancier> echeanciers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("factures")
    private BonLivraison bonLivraison;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public Facture designation(String designation) {
        this.designation = designation;
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getMontantFact() {
        return montantFact;
    }

    public Facture montantFact(Double montantFact) {
        this.montantFact = montantFact;
        return this;
    }

    public void setMontantFact(Double montantFact) {
        this.montantFact = montantFact;
    }

    public Double getMontantPaye() {
        return montantPaye;
    }

    public Facture montantPaye(Double montantPaye) {
        this.montantPaye = montantPaye;
        return this;
    }

    public void setMontantPaye(Double montantPaye) {
        this.montantPaye = montantPaye;
    }

    public Double getMontantGlobal() {
        return montantGlobal;
    }

    public Facture montantGlobal(Double montantGlobal) {
        this.montantGlobal = montantGlobal;
        return this;
    }

    public void setMontantGlobal(Double montantGlobal) {
        this.montantGlobal = montantGlobal;
    }

    public Boolean isMoratoire() {
        return moratoire;
    }

    public Facture moratoire(Boolean moratoire) {
        this.moratoire = moratoire;
        return this;
    }

    public void setMoratoire(Boolean moratoire) {
        this.moratoire = moratoire;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Facture dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Facture dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public Facture dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Facture userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Facture userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Facture userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public Facture operations(Set<Operation> operations) {
        this.operations = operations;
        return this;
    }

    public Facture addOperation(Operation operation) {
        this.operations.add(operation);
        operation.setOperation(this);
        return this;
    }

    public Facture removeOperation(Operation operation) {
        this.operations.remove(operation);
        operation.setOperation(null);
        return this;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }

    public Set<Echeancier> getEcheanciers() {
        return echeanciers;
    }

    public Facture echeanciers(Set<Echeancier> echeanciers) {
        this.echeanciers = echeanciers;
        return this;
    }

    public Facture addEcheancier(Echeancier echeancier) {
        this.echeanciers.add(echeancier);
        echeancier.setFacture(this);
        return this;
    }

    public Facture removeEcheancier(Echeancier echeancier) {
        this.echeanciers.remove(echeancier);
        echeancier.setFacture(null);
        return this;
    }

    public void setEcheanciers(Set<Echeancier> echeanciers) {
        this.echeanciers = echeanciers;
    }

    public BonLivraison getBonLivraison() {
        return bonLivraison;
    }

    public Facture bonLivraison(BonLivraison bonLivraison) {
        this.bonLivraison = bonLivraison;
        return this;
    }

    public void setBonLivraison(BonLivraison bonLivraison) {
        this.bonLivraison = bonLivraison;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facture)) {
            return false;
        }
        return id != null && id.equals(((Facture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Facture{" +
            "id=" + getId() +
            ", designation='" + getDesignation() + "'" +
            ", montantFact=" + getMontantFact() +
            ", montantPaye=" + getMontantPaye() +
            ", montantGlobal=" + getMontantGlobal() +
            ", moratoire='" + isMoratoire() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
