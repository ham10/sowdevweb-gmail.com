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
 * A FicheMedical.
 */
@Entity
@Table(name = "fiche_medical")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FicheMedical implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_fiche_medical")
    private String numeroFicheMedical;

    @Column(name = "date_consultation")
    private LocalDate dateConsultation;

    @Column(name = "facteur_risque")
    private String facteurRisque;

    @Column(name = "regime_alimentaire")
    private String regimeAlimentaire;

    @Column(name = "diagnostic")
    private String diagnostic;

    @Column(name = "recommandations")
    private String recommandations;

    @Column(name = "commentaires")
    private String commentaires;

    @Column(name = "date_prochain_rv")
    private LocalDate dateProchainRV;

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

    @OneToMany(mappedBy = "ficheMedical")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DosMedical> dossierMedicals = new HashSet<>();

    @OneToMany(mappedBy = "ficheMedical")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ResultatActe> resultatActes = new HashSet<>();

    @OneToMany(mappedBy = "ficheMedical")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ordonnance> ordonnances = new HashSet<>();

    @OneToMany(mappedBy = "ficheMedical")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vaccin> vaccins = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("ficheMedicals")
    private Medecin medecin;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "fiche_medical_type_constante",
               joinColumns = @JoinColumn(name = "fiche_medical_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "type_constante_id", referencedColumnName = "id"))
    private Set<TypeConstante> typeConstantes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroFicheMedical() {
        return numeroFicheMedical;
    }

    public FicheMedical numeroFicheMedical(String numeroFicheMedical) {
        this.numeroFicheMedical = numeroFicheMedical;
        return this;
    }

    public void setNumeroFicheMedical(String numeroFicheMedical) {
        this.numeroFicheMedical = numeroFicheMedical;
    }

    public LocalDate getDateConsultation() {
        return dateConsultation;
    }

    public FicheMedical dateConsultation(LocalDate dateConsultation) {
        this.dateConsultation = dateConsultation;
        return this;
    }

    public void setDateConsultation(LocalDate dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    public String getFacteurRisque() {
        return facteurRisque;
    }

    public FicheMedical facteurRisque(String facteurRisque) {
        this.facteurRisque = facteurRisque;
        return this;
    }

    public void setFacteurRisque(String facteurRisque) {
        this.facteurRisque = facteurRisque;
    }

    public String getRegimeAlimentaire() {
        return regimeAlimentaire;
    }

    public FicheMedical regimeAlimentaire(String regimeAlimentaire) {
        this.regimeAlimentaire = regimeAlimentaire;
        return this;
    }

    public void setRegimeAlimentaire(String regimeAlimentaire) {
        this.regimeAlimentaire = regimeAlimentaire;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public FicheMedical diagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
        return this;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getRecommandations() {
        return recommandations;
    }

    public FicheMedical recommandations(String recommandations) {
        this.recommandations = recommandations;
        return this;
    }

    public void setRecommandations(String recommandations) {
        this.recommandations = recommandations;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public FicheMedical commentaires(String commentaires) {
        this.commentaires = commentaires;
        return this;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public LocalDate getDateProchainRV() {
        return dateProchainRV;
    }

    public FicheMedical dateProchainRV(LocalDate dateProchainRV) {
        this.dateProchainRV = dateProchainRV;
        return this;
    }

    public void setDateProchainRV(LocalDate dateProchainRV) {
        this.dateProchainRV = dateProchainRV;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public FicheMedical dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public FicheMedical dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public FicheMedical userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public FicheMedical userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public FicheMedical userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<DosMedical> getDossierMedicals() {
        return dossierMedicals;
    }

    public FicheMedical dossierMedicals(Set<DosMedical> dosMedicals) {
        this.dossierMedicals = dosMedicals;
        return this;
    }

    public FicheMedical addDossierMedical(DosMedical dosMedical) {
        this.dossierMedicals.add(dosMedical);
        dosMedical.setFicheMedical(this);
        return this;
    }

    public FicheMedical removeDossierMedical(DosMedical dosMedical) {
        this.dossierMedicals.remove(dosMedical);
        dosMedical.setFicheMedical(null);
        return this;
    }

    public void setDossierMedicals(Set<DosMedical> dosMedicals) {
        this.dossierMedicals = dosMedicals;
    }

    public Set<ResultatActe> getResultatActes() {
        return resultatActes;
    }

    public FicheMedical resultatActes(Set<ResultatActe> resultatActes) {
        this.resultatActes = resultatActes;
        return this;
    }

    public FicheMedical addResultatActe(ResultatActe resultatActe) {
        this.resultatActes.add(resultatActe);
        resultatActe.setFicheMedical(this);
        return this;
    }

    public FicheMedical removeResultatActe(ResultatActe resultatActe) {
        this.resultatActes.remove(resultatActe);
        resultatActe.setFicheMedical(null);
        return this;
    }

    public void setResultatActes(Set<ResultatActe> resultatActes) {
        this.resultatActes = resultatActes;
    }

    public Set<Ordonnance> getOrdonnances() {
        return ordonnances;
    }

    public FicheMedical ordonnances(Set<Ordonnance> ordonnances) {
        this.ordonnances = ordonnances;
        return this;
    }

    public FicheMedical addOrdonnance(Ordonnance ordonnance) {
        this.ordonnances.add(ordonnance);
        ordonnance.setFicheMedical(this);
        return this;
    }

    public FicheMedical removeOrdonnance(Ordonnance ordonnance) {
        this.ordonnances.remove(ordonnance);
        ordonnance.setFicheMedical(null);
        return this;
    }

    public void setOrdonnances(Set<Ordonnance> ordonnances) {
        this.ordonnances = ordonnances;
    }

    public Set<Vaccin> getVaccins() {
        return vaccins;
    }

    public FicheMedical vaccins(Set<Vaccin> vaccins) {
        this.vaccins = vaccins;
        return this;
    }

    public FicheMedical addVaccin(Vaccin vaccin) {
        this.vaccins.add(vaccin);
        vaccin.setFicheMedical(this);
        return this;
    }

    public FicheMedical removeVaccin(Vaccin vaccin) {
        this.vaccins.remove(vaccin);
        vaccin.setFicheMedical(null);
        return this;
    }

    public void setVaccins(Set<Vaccin> vaccins) {
        this.vaccins = vaccins;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public FicheMedical medecin(Medecin medecin) {
        this.medecin = medecin;
        return this;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Set<TypeConstante> getTypeConstantes() {
        return typeConstantes;
    }

    public FicheMedical typeConstantes(Set<TypeConstante> typeConstantes) {
        this.typeConstantes = typeConstantes;
        return this;
    }

    public FicheMedical addTypeConstante(TypeConstante typeConstante) {
        this.typeConstantes.add(typeConstante);
        typeConstante.getFicheMedicals().add(this);
        return this;
    }

    public FicheMedical removeTypeConstante(TypeConstante typeConstante) {
        this.typeConstantes.remove(typeConstante);
        typeConstante.getFicheMedicals().remove(this);
        return this;
    }

    public void setTypeConstantes(Set<TypeConstante> typeConstantes) {
        this.typeConstantes = typeConstantes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FicheMedical)) {
            return false;
        }
        return id != null && id.equals(((FicheMedical) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FicheMedical{" +
            "id=" + getId() +
            ", numeroFicheMedical='" + getNumeroFicheMedical() + "'" +
            ", dateConsultation='" + getDateConsultation() + "'" +
            ", facteurRisque='" + getFacteurRisque() + "'" +
            ", regimeAlimentaire='" + getRegimeAlimentaire() + "'" +
            ", diagnostic='" + getDiagnostic() + "'" +
            ", recommandations='" + getRecommandations() + "'" +
            ", commentaires='" + getCommentaires() + "'" +
            ", dateProchainRV='" + getDateProchainRV() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
