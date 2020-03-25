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
 * A Compte.
 */
@Entity
@Table(name = "compte")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Compte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_compte")
    private Integer numeroCompte;

    @Column(name = "libelle_compte")
    private String libelleCompte;

    @Column(name = "solde_compte")
    private Double soldeCompte;

    @Column(name = "sens_compte")
    private String sensCompte;

    @Column(name = "cumul_mouv_debit")
    private Double cumulMouvDebit;

    @Column(name = "cumul_mouv_credit")
    private Double cumulMouvCredit;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

    @Column(name = "date_deleted")
    private Integer dateDeleted;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_updated")
    private Long userUpdated;

    @Column(name = "user_deleted")
    private Long userDeleted;

    @OneToMany(mappedBy = "compte")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ecriture> ecritures = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("comptes")
    private CompteGene compteGeneral;

    @ManyToOne
    @JsonIgnoreProperties("comptes")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroCompte() {
        return numeroCompte;
    }

    public Compte numeroCompte(Integer numeroCompte) {
        this.numeroCompte = numeroCompte;
        return this;
    }

    public void setNumeroCompte(Integer numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getLibelleCompte() {
        return libelleCompte;
    }

    public Compte libelleCompte(String libelleCompte) {
        this.libelleCompte = libelleCompte;
        return this;
    }

    public void setLibelleCompte(String libelleCompte) {
        this.libelleCompte = libelleCompte;
    }

    public Double getSoldeCompte() {
        return soldeCompte;
    }

    public Compte soldeCompte(Double soldeCompte) {
        this.soldeCompte = soldeCompte;
        return this;
    }

    public void setSoldeCompte(Double soldeCompte) {
        this.soldeCompte = soldeCompte;
    }

    public String getSensCompte() {
        return sensCompte;
    }

    public Compte sensCompte(String sensCompte) {
        this.sensCompte = sensCompte;
        return this;
    }

    public void setSensCompte(String sensCompte) {
        this.sensCompte = sensCompte;
    }

    public Double getCumulMouvDebit() {
        return cumulMouvDebit;
    }

    public Compte cumulMouvDebit(Double cumulMouvDebit) {
        this.cumulMouvDebit = cumulMouvDebit;
        return this;
    }

    public void setCumulMouvDebit(Double cumulMouvDebit) {
        this.cumulMouvDebit = cumulMouvDebit;
    }

    public Double getCumulMouvCredit() {
        return cumulMouvCredit;
    }

    public Compte cumulMouvCredit(Double cumulMouvCredit) {
        this.cumulMouvCredit = cumulMouvCredit;
        return this;
    }

    public void setCumulMouvCredit(Double cumulMouvCredit) {
        this.cumulMouvCredit = cumulMouvCredit;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Compte dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Compte dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Integer getDateDeleted() {
        return dateDeleted;
    }

    public Compte dateDeleted(Integer dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(Integer dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Compte userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Compte userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Compte userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Ecriture> getEcritures() {
        return ecritures;
    }

    public Compte ecritures(Set<Ecriture> ecritures) {
        this.ecritures = ecritures;
        return this;
    }

    public Compte addEcriture(Ecriture ecriture) {
        this.ecritures.add(ecriture);
        ecriture.setCompte(this);
        return this;
    }

    public Compte removeEcriture(Ecriture ecriture) {
        this.ecritures.remove(ecriture);
        ecriture.setCompte(null);
        return this;
    }

    public void setEcritures(Set<Ecriture> ecritures) {
        this.ecritures = ecritures;
    }

    public CompteGene getCompteGeneral() {
        return compteGeneral;
    }

    public Compte compteGeneral(CompteGene compteGene) {
        this.compteGeneral = compteGene;
        return this;
    }

    public void setCompteGeneral(CompteGene compteGene) {
        this.compteGeneral = compteGene;
    }

    public Patient getPatient() {
        return patient;
    }

    public Compte patient(Patient patient) {
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
        if (!(o instanceof Compte)) {
            return false;
        }
        return id != null && id.equals(((Compte) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Compte{" +
            "id=" + getId() +
            ", numeroCompte=" + getNumeroCompte() +
            ", libelleCompte='" + getLibelleCompte() + "'" +
            ", soldeCompte=" + getSoldeCompte() +
            ", sensCompte='" + getSensCompte() + "'" +
            ", cumulMouvDebit=" + getCumulMouvDebit() +
            ", cumulMouvCredit=" + getCumulMouvCredit() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted=" + getDateDeleted() +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
