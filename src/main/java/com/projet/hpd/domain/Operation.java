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
 * A Operation.
 */
@Entity
@Table(name = "operation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "taxe")
    private Double taxe;

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
    private Set<Immo> immos = new HashSet<>();

    @OneToMany(mappedBy = "operation")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ecriture> ecritures = new HashSet<>();

    @OneToOne(mappedBy = "operation")
    @JsonIgnore
    private SchemaCompta schemaComptable;

    @ManyToOne
    @JsonIgnoreProperties("operations")
    private EtatOperation etatOperation;

    @ManyToOne
    @JsonIgnoreProperties("operations")
    private Caisse caisse;

    @ManyToOne
    @JsonIgnoreProperties("operations")
    private Facture operation;

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

    public Operation libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDate() {
        return date;
    }

    public Operation date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getMontant() {
        return montant;
    }

    public Operation montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getTaxe() {
        return taxe;
    }

    public Operation taxe(Double taxe) {
        this.taxe = taxe;
        return this;
    }

    public void setTaxe(Double taxe) {
        this.taxe = taxe;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Operation dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Operation dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public Operation dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Operation userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Operation userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Operation userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Immo> getImmos() {
        return immos;
    }

    public Operation immos(Set<Immo> immos) {
        this.immos = immos;
        return this;
    }

    public Operation addImmo(Immo immo) {
        this.immos.add(immo);
        immo.setOperation(this);
        return this;
    }

    public Operation removeImmo(Immo immo) {
        this.immos.remove(immo);
        immo.setOperation(null);
        return this;
    }

    public void setImmos(Set<Immo> immos) {
        this.immos = immos;
    }

    public Set<Ecriture> getEcritures() {
        return ecritures;
    }

    public Operation ecritures(Set<Ecriture> ecritures) {
        this.ecritures = ecritures;
        return this;
    }

    public Operation addEcriture(Ecriture ecriture) {
        this.ecritures.add(ecriture);
        ecriture.setOperation(this);
        return this;
    }

    public Operation removeEcriture(Ecriture ecriture) {
        this.ecritures.remove(ecriture);
        ecriture.setOperation(null);
        return this;
    }

    public void setEcritures(Set<Ecriture> ecritures) {
        this.ecritures = ecritures;
    }

    public SchemaCompta getSchemaComptable() {
        return schemaComptable;
    }

    public Operation schemaComptable(SchemaCompta schemaCompta) {
        this.schemaComptable = schemaCompta;
        return this;
    }

    public void setSchemaComptable(SchemaCompta schemaCompta) {
        this.schemaComptable = schemaCompta;
    }

    public EtatOperation getEtatOperation() {
        return etatOperation;
    }

    public Operation etatOperation(EtatOperation etatOperation) {
        this.etatOperation = etatOperation;
        return this;
    }

    public void setEtatOperation(EtatOperation etatOperation) {
        this.etatOperation = etatOperation;
    }

    public Caisse getCaisse() {
        return caisse;
    }

    public Operation caisse(Caisse caisse) {
        this.caisse = caisse;
        return this;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }

    public Facture getOperation() {
        return operation;
    }

    public Operation operation(Facture facture) {
        this.operation = facture;
        return this;
    }

    public void setOperation(Facture facture) {
        this.operation = facture;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Operation)) {
            return false;
        }
        return id != null && id.equals(((Operation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Operation{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", date='" + getDate() + "'" +
            ", montant=" + getMontant() +
            ", taxe=" + getTaxe() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
