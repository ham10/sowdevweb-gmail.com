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
 * A DosMedical.
 */
@Entity
@Table(name = "dos_medical")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DosMedical implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_creation")
    private LocalDate dateCreation;

    @Column(name = "numero_dossier_dos_medical")
    private Integer numeroDossierDosMedical;

    @Column(name = "niveau_dependance")
    private Integer niveauDependance;

    @Column(name = "etat_conscience")
    private Integer etatConscience;

    @Column(name = "etat_cutane")
    private Integer etatCutane;

    @Column(name = "intolerance_medic")
    private String intoleranceMedic;

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
    @JoinTable(name = "dos_medical_antecedent",
               joinColumns = @JoinColumn(name = "dos_medical_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "antecedent_id", referencedColumnName = "id"))
    private Set<Antecedent> antecedents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("dossierMedicals")
    private FicheMedical ficheMedical;

    @ManyToOne
    @JsonIgnoreProperties("dosMedicals")
    private Services serv;

    @ManyToOne
    @JsonIgnoreProperties("dosMedicals")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public DosMedical dateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Integer getNumeroDossierDosMedical() {
        return numeroDossierDosMedical;
    }

    public DosMedical numeroDossierDosMedical(Integer numeroDossierDosMedical) {
        this.numeroDossierDosMedical = numeroDossierDosMedical;
        return this;
    }

    public void setNumeroDossierDosMedical(Integer numeroDossierDosMedical) {
        this.numeroDossierDosMedical = numeroDossierDosMedical;
    }

    public Integer getNiveauDependance() {
        return niveauDependance;
    }

    public DosMedical niveauDependance(Integer niveauDependance) {
        this.niveauDependance = niveauDependance;
        return this;
    }

    public void setNiveauDependance(Integer niveauDependance) {
        this.niveauDependance = niveauDependance;
    }

    public Integer getEtatConscience() {
        return etatConscience;
    }

    public DosMedical etatConscience(Integer etatConscience) {
        this.etatConscience = etatConscience;
        return this;
    }

    public void setEtatConscience(Integer etatConscience) {
        this.etatConscience = etatConscience;
    }

    public Integer getEtatCutane() {
        return etatCutane;
    }

    public DosMedical etatCutane(Integer etatCutane) {
        this.etatCutane = etatCutane;
        return this;
    }

    public void setEtatCutane(Integer etatCutane) {
        this.etatCutane = etatCutane;
    }

    public String getIntoleranceMedic() {
        return intoleranceMedic;
    }

    public DosMedical intoleranceMedic(String intoleranceMedic) {
        this.intoleranceMedic = intoleranceMedic;
        return this;
    }

    public void setIntoleranceMedic(String intoleranceMedic) {
        this.intoleranceMedic = intoleranceMedic;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public DosMedical dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public DosMedical dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public DosMedical userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public DosMedical userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public DosMedical userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Antecedent> getAntecedents() {
        return antecedents;
    }

    public DosMedical antecedents(Set<Antecedent> antecedents) {
        this.antecedents = antecedents;
        return this;
    }

    public DosMedical addAntecedent(Antecedent antecedent) {
        this.antecedents.add(antecedent);
        antecedent.getDosMedicals().add(this);
        return this;
    }

    public DosMedical removeAntecedent(Antecedent antecedent) {
        this.antecedents.remove(antecedent);
        antecedent.getDosMedicals().remove(this);
        return this;
    }

    public void setAntecedents(Set<Antecedent> antecedents) {
        this.antecedents = antecedents;
    }

    public FicheMedical getFicheMedical() {
        return ficheMedical;
    }

    public DosMedical ficheMedical(FicheMedical ficheMedical) {
        this.ficheMedical = ficheMedical;
        return this;
    }

    public void setFicheMedical(FicheMedical ficheMedical) {
        this.ficheMedical = ficheMedical;
    }

    public Services getServ() {
        return serv;
    }

    public DosMedical serv(Services services) {
        this.serv = services;
        return this;
    }

    public void setServ(Services services) {
        this.serv = services;
    }

    public Patient getPatient() {
        return patient;
    }

    public DosMedical patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DosMedical)) {
            return false;
        }
        return id != null && id.equals(((DosMedical) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DosMedical{" +
            "id=" + getId() +
            ", dateCreation='" + getDateCreation() + "'" +
            ", numeroDossierDosMedical=" + getNumeroDossierDosMedical() +
            ", niveauDependance=" + getNiveauDependance() +
            ", etatConscience=" + getEtatConscience() +
            ", etatCutane=" + getEtatCutane() +
            ", intoleranceMedic='" + getIntoleranceMedic() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
