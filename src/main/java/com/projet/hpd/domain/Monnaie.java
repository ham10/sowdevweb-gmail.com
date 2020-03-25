package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Monnaie.
 */
@Entity
@Table(name = "monnaie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Monnaie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle_monnaie")
    private String libelleMonnaie;

    @Column(name = "montant_monnaie")
    private Double montantMonnaie;

    @Column(name = "nature_monnaie")
    private String natureMonnaie;

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

    @ManyToOne
    @JsonIgnoreProperties("monnaies")
    private Devise devise;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleMonnaie() {
        return libelleMonnaie;
    }

    public Monnaie libelleMonnaie(String libelleMonnaie) {
        this.libelleMonnaie = libelleMonnaie;
        return this;
    }

    public void setLibelleMonnaie(String libelleMonnaie) {
        this.libelleMonnaie = libelleMonnaie;
    }

    public Double getMontantMonnaie() {
        return montantMonnaie;
    }

    public Monnaie montantMonnaie(Double montantMonnaie) {
        this.montantMonnaie = montantMonnaie;
        return this;
    }

    public void setMontantMonnaie(Double montantMonnaie) {
        this.montantMonnaie = montantMonnaie;
    }

    public String getNatureMonnaie() {
        return natureMonnaie;
    }

    public Monnaie natureMonnaie(String natureMonnaie) {
        this.natureMonnaie = natureMonnaie;
        return this;
    }

    public void setNatureMonnaie(String natureMonnaie) {
        this.natureMonnaie = natureMonnaie;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Monnaie dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Monnaie dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Monnaie userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Monnaie userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Monnaie userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Devise getDevise() {
        return devise;
    }

    public Monnaie devise(Devise devise) {
        this.devise = devise;
        return this;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Monnaie)) {
            return false;
        }
        return id != null && id.equals(((Monnaie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Monnaie{" +
            "id=" + getId() +
            ", libelleMonnaie='" + getLibelleMonnaie() + "'" +
            ", montantMonnaie=" + getMontantMonnaie() +
            ", natureMonnaie='" + getNatureMonnaie() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
