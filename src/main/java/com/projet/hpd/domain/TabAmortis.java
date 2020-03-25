package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TabAmortis.
 */
@Entity
@Table(name = "tab_amortis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TabAmortis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "etat")
    private String etat;

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

    @OneToMany(mappedBy = "tableauAmortissement")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Immo> immos = new HashSet<>();

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

    public TabAmortis libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getMontant() {
        return montant;
    }

    public TabAmortis montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public TabAmortis date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public TabAmortis etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TabAmortis dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TabAmortis dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public TabAmortis dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TabAmortis userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TabAmortis userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TabAmortis userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Immo> getImmos() {
        return immos;
    }

    public TabAmortis immos(Set<Immo> immos) {
        this.immos = immos;
        return this;
    }

    public TabAmortis addImmo(Immo immo) {
        this.immos.add(immo);
        immo.setTableauAmortissement(this);
        return this;
    }

    public TabAmortis removeImmo(Immo immo) {
        this.immos.remove(immo);
        immo.setTableauAmortissement(null);
        return this;
    }

    public void setImmos(Set<Immo> immos) {
        this.immos = immos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TabAmortis)) {
            return false;
        }
        return id != null && id.equals(((TabAmortis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TabAmortis{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", montant=" + getMontant() +
            ", date='" + getDate() + "'" +
            ", etat='" + getEtat() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
