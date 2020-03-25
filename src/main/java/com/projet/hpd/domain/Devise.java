package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Devise.
 */
@Entity
@Table(name = "devise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Devise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "codeiso_dvise")
    private String codeisoDvise;

    @Column(name = "libelle_devise")
    private String libelleDevise;

    @Column(name = "description_devise")
    private String descriptionDevise;

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

    @OneToMany(mappedBy = "devise")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TauxDevise> tauxDevises = new HashSet<>();

    @OneToMany(mappedBy = "devise")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Monnaie> monnaies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeisoDvise() {
        return codeisoDvise;
    }

    public Devise codeisoDvise(String codeisoDvise) {
        this.codeisoDvise = codeisoDvise;
        return this;
    }

    public void setCodeisoDvise(String codeisoDvise) {
        this.codeisoDvise = codeisoDvise;
    }

    public String getLibelleDevise() {
        return libelleDevise;
    }

    public Devise libelleDevise(String libelleDevise) {
        this.libelleDevise = libelleDevise;
        return this;
    }

    public void setLibelleDevise(String libelleDevise) {
        this.libelleDevise = libelleDevise;
    }

    public String getDescriptionDevise() {
        return descriptionDevise;
    }

    public Devise descriptionDevise(String descriptionDevise) {
        this.descriptionDevise = descriptionDevise;
        return this;
    }

    public void setDescriptionDevise(String descriptionDevise) {
        this.descriptionDevise = descriptionDevise;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Devise dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Devise dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Devise userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Devise userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Devise userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<TauxDevise> getTauxDevises() {
        return tauxDevises;
    }

    public Devise tauxDevises(Set<TauxDevise> tauxDevises) {
        this.tauxDevises = tauxDevises;
        return this;
    }

    public Devise addTauxDevise(TauxDevise tauxDevise) {
        this.tauxDevises.add(tauxDevise);
        tauxDevise.setDevise(this);
        return this;
    }

    public Devise removeTauxDevise(TauxDevise tauxDevise) {
        this.tauxDevises.remove(tauxDevise);
        tauxDevise.setDevise(null);
        return this;
    }

    public void setTauxDevises(Set<TauxDevise> tauxDevises) {
        this.tauxDevises = tauxDevises;
    }

    public Set<Monnaie> getMonnaies() {
        return monnaies;
    }

    public Devise monnaies(Set<Monnaie> monnaies) {
        this.monnaies = monnaies;
        return this;
    }

    public Devise addMonnaie(Monnaie monnaie) {
        this.monnaies.add(monnaie);
        monnaie.setDevise(this);
        return this;
    }

    public Devise removeMonnaie(Monnaie monnaie) {
        this.monnaies.remove(monnaie);
        monnaie.setDevise(null);
        return this;
    }

    public void setMonnaies(Set<Monnaie> monnaies) {
        this.monnaies = monnaies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Devise)) {
            return false;
        }
        return id != null && id.equals(((Devise) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Devise{" +
            "id=" + getId() +
            ", codeisoDvise='" + getCodeisoDvise() + "'" +
            ", libelleDevise='" + getLibelleDevise() + "'" +
            ", descriptionDevise='" + getDescriptionDevise() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
