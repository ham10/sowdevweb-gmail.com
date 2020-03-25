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
 * A ResultatActe.
 */
@Entity
@Table(name = "resultat_acte")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResultatActe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "resultat")
    private String resultat;

    @Column(name = "date")
    private LocalDate date;

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

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "resultat_acte_acte_medical",
               joinColumns = @JoinColumn(name = "resultat_acte_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "acte_medical_id", referencedColumnName = "id"))
    private Set<ActeMedical> acteMedicals = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("resultatActes")
    private FicheMedical ficheMedical;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public ResultatActe numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getResultat() {
        return resultat;
    }

    public ResultatActe resultat(String resultat) {
        this.resultat = resultat;
        return this;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public LocalDate getDate() {
        return date;
    }

    public ResultatActe date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public ResultatActe description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public ResultatActe dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public ResultatActe dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public ResultatActe userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public ResultatActe userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public ResultatActe userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<ActeMedical> getActeMedicals() {
        return acteMedicals;
    }

    public ResultatActe acteMedicals(Set<ActeMedical> acteMedicals) {
        this.acteMedicals = acteMedicals;
        return this;
    }

    public ResultatActe addActeMedical(ActeMedical acteMedical) {
        this.acteMedicals.add(acteMedical);
        acteMedical.getResultatActes().add(this);
        return this;
    }

    public ResultatActe removeActeMedical(ActeMedical acteMedical) {
        this.acteMedicals.remove(acteMedical);
        acteMedical.getResultatActes().remove(this);
        return this;
    }

    public void setActeMedicals(Set<ActeMedical> acteMedicals) {
        this.acteMedicals = acteMedicals;
    }

    public FicheMedical getFicheMedical() {
        return ficheMedical;
    }

    public ResultatActe ficheMedical(FicheMedical ficheMedical) {
        this.ficheMedical = ficheMedical;
        return this;
    }

    public void setFicheMedical(FicheMedical ficheMedical) {
        this.ficheMedical = ficheMedical;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultatActe)) {
            return false;
        }
        return id != null && id.equals(((ResultatActe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ResultatActe{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", resultat='" + getResultat() + "'" +
            ", date='" + getDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
