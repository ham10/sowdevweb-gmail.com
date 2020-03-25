package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Caisse.
 */
@Entity
@Table(name = "caisse")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Caisse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "solde_min")
    private LocalDate soldeMin;

    @Column(name = "solde_max")
    private String soldeMax;

    @Column(name = "montant")
    private Integer montant;

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

    @OneToMany(mappedBy = "caisse")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Operation> operations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Caisse numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public LocalDate getSoldeMin() {
        return soldeMin;
    }

    public Caisse soldeMin(LocalDate soldeMin) {
        this.soldeMin = soldeMin;
        return this;
    }

    public void setSoldeMin(LocalDate soldeMin) {
        this.soldeMin = soldeMin;
    }

    public String getSoldeMax() {
        return soldeMax;
    }

    public Caisse soldeMax(String soldeMax) {
        this.soldeMax = soldeMax;
        return this;
    }

    public void setSoldeMax(String soldeMax) {
        this.soldeMax = soldeMax;
    }

    public Integer getMontant() {
        return montant;
    }

    public Caisse montant(Integer montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Caisse dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Caisse dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public Caisse dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Caisse userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Caisse userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Caisse userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Operation> getOperations() {
        return operations;
    }

    public Caisse operations(Set<Operation> operations) {
        this.operations = operations;
        return this;
    }

    public Caisse addOperation(Operation operation) {
        this.operations.add(operation);
        operation.setCaisse(this);
        return this;
    }

    public Caisse removeOperation(Operation operation) {
        this.operations.remove(operation);
        operation.setCaisse(null);
        return this;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Caisse)) {
            return false;
        }
        return id != null && id.equals(((Caisse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Caisse{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", soldeMin='" + getSoldeMin() + "'" +
            ", soldeMax='" + getSoldeMax() + "'" +
            ", montant=" + getMontant() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
