package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A ActeMedical.
 */
@Entity
@Table(name = "acte_medical")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActeMedical implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle_a")
    private String libelleA;

    @Column(name = "description")
    private String description;

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

    @OneToMany(mappedBy = "actemedical")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tarif> tarifs = new HashSet<>();

    @ManyToMany(mappedBy = "acteMedicals")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<ResultatActe> resultatActes = new HashSet<>();

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

    public ActeMedical code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelleA() {
        return libelleA;
    }

    public ActeMedical libelleA(String libelleA) {
        this.libelleA = libelleA;
        return this;
    }

    public void setLibelleA(String libelleA) {
        this.libelleA = libelleA;
    }

    public String getDescription() {
        return description;
    }

    public ActeMedical description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public ActeMedical dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public ActeMedical dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public ActeMedical userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public ActeMedical userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public ActeMedical userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Tarif> getTarifs() {
        return tarifs;
    }

    public ActeMedical tarifs(Set<Tarif> tarifs) {
        this.tarifs = tarifs;
        return this;
    }

    public ActeMedical addTarif(Tarif tarif) {
        this.tarifs.add(tarif);
        tarif.setActemedical(this);
        return this;
    }

    public ActeMedical removeTarif(Tarif tarif) {
        this.tarifs.remove(tarif);
        tarif.setActemedical(null);
        return this;
    }

    public void setTarifs(Set<Tarif> tarifs) {
        this.tarifs = tarifs;
    }

    public Set<ResultatActe> getResultatActes() {
        return resultatActes;
    }

    public ActeMedical resultatActes(Set<ResultatActe> resultatActes) {
        this.resultatActes = resultatActes;
        return this;
    }

    public ActeMedical addResultatActe(ResultatActe resultatActe) {
        this.resultatActes.add(resultatActe);
        resultatActe.getActeMedicals().add(this);
        return this;
    }

    public ActeMedical removeResultatActe(ResultatActe resultatActe) {
        this.resultatActes.remove(resultatActe);
        resultatActe.getActeMedicals().remove(this);
        return this;
    }

    public void setResultatActes(Set<ResultatActe> resultatActes) {
        this.resultatActes = resultatActes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActeMedical)) {
            return false;
        }
        return id != null && id.equals(((ActeMedical) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ActeMedical{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelleA='" + getLibelleA() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
