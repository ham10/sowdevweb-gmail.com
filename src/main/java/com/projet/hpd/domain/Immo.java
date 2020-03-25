package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Immo.
 */
@Entity
@Table(name = "immo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Immo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "valeur_aquisition")
    private Double valeurAquisition;

    @Column(name = "date_aquisition")
    private LocalDate dateAquisition;

    @Column(name = "valeur_net_comptable")
    private Double valeurNetComptable;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "duree")
    private Integer duree;

    @Column(name = "nbre_echeance")
    private Integer nbreEcheance;

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
    @JsonIgnoreProperties("immos")
    private Operation operation;

    @ManyToOne
    @JsonIgnoreProperties("immos")
    private TypeImmo typeImmo;

    @ManyToOne
    @JsonIgnoreProperties("immos")
    private TabAmortis tableauAmortissement;

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

    public Immo libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getValeurAquisition() {
        return valeurAquisition;
    }

    public Immo valeurAquisition(Double valeurAquisition) {
        this.valeurAquisition = valeurAquisition;
        return this;
    }

    public void setValeurAquisition(Double valeurAquisition) {
        this.valeurAquisition = valeurAquisition;
    }

    public LocalDate getDateAquisition() {
        return dateAquisition;
    }

    public Immo dateAquisition(LocalDate dateAquisition) {
        this.dateAquisition = dateAquisition;
        return this;
    }

    public void setDateAquisition(LocalDate dateAquisition) {
        this.dateAquisition = dateAquisition;
    }

    public Double getValeurNetComptable() {
        return valeurNetComptable;
    }

    public Immo valeurNetComptable(Double valeurNetComptable) {
        this.valeurNetComptable = valeurNetComptable;
        return this;
    }

    public void setValeurNetComptable(Double valeurNetComptable) {
        this.valeurNetComptable = valeurNetComptable;
    }

    public Double getMontant() {
        return montant;
    }

    public Immo montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Integer getDuree() {
        return duree;
    }

    public Immo duree(Integer duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Integer getNbreEcheance() {
        return nbreEcheance;
    }

    public Immo nbreEcheance(Integer nbreEcheance) {
        this.nbreEcheance = nbreEcheance;
        return this;
    }

    public void setNbreEcheance(Integer nbreEcheance) {
        this.nbreEcheance = nbreEcheance;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Immo dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Immo dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public Immo dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Immo userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Immo userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Immo userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Operation getOperation() {
        return operation;
    }

    public Immo operation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public TypeImmo getTypeImmo() {
        return typeImmo;
    }

    public Immo typeImmo(TypeImmo typeImmo) {
        this.typeImmo = typeImmo;
        return this;
    }

    public void setTypeImmo(TypeImmo typeImmo) {
        this.typeImmo = typeImmo;
    }

    public TabAmortis getTableauAmortissement() {
        return tableauAmortissement;
    }

    public Immo tableauAmortissement(TabAmortis tabAmortis) {
        this.tableauAmortissement = tabAmortis;
        return this;
    }

    public void setTableauAmortissement(TabAmortis tabAmortis) {
        this.tableauAmortissement = tabAmortis;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Immo)) {
            return false;
        }
        return id != null && id.equals(((Immo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Immo{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", valeurAquisition=" + getValeurAquisition() +
            ", dateAquisition='" + getDateAquisition() + "'" +
            ", valeurNetComptable=" + getValeurNetComptable() +
            ", montant=" + getMontant() +
            ", duree=" + getDuree() +
            ", nbreEcheance=" + getNbreEcheance() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
