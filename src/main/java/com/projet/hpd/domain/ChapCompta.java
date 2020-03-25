package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A ChapCompta.
 */
@Entity
@Table(name = "chap_compta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ChapCompta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_chap_compta")
    private Integer numeroChapCompta;

    @Column(name = "libelle_chap_compta")
    private String libelleChapCompta;

    @Column(name = "sens_chap_compta")
    private String sensChapCompta;

    @Column(name = "solde_chap_compta")
    private Double soldeChapCompta;

    @Column(name = "cumul_mouv_debit_chap_compta")
    private Double cumulMouvDebitChapCompta;

    @Column(name = "cumul_mouv_credit_chap_compta")
    private Double cumulMouvCreditChapCompta;

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

    @OneToMany(mappedBy = "chapitreComptable")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompteGene> compteGenes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroChapCompta() {
        return numeroChapCompta;
    }

    public ChapCompta numeroChapCompta(Integer numeroChapCompta) {
        this.numeroChapCompta = numeroChapCompta;
        return this;
    }

    public void setNumeroChapCompta(Integer numeroChapCompta) {
        this.numeroChapCompta = numeroChapCompta;
    }

    public String getLibelleChapCompta() {
        return libelleChapCompta;
    }

    public ChapCompta libelleChapCompta(String libelleChapCompta) {
        this.libelleChapCompta = libelleChapCompta;
        return this;
    }

    public void setLibelleChapCompta(String libelleChapCompta) {
        this.libelleChapCompta = libelleChapCompta;
    }

    public String getSensChapCompta() {
        return sensChapCompta;
    }

    public ChapCompta sensChapCompta(String sensChapCompta) {
        this.sensChapCompta = sensChapCompta;
        return this;
    }

    public void setSensChapCompta(String sensChapCompta) {
        this.sensChapCompta = sensChapCompta;
    }

    public Double getSoldeChapCompta() {
        return soldeChapCompta;
    }

    public ChapCompta soldeChapCompta(Double soldeChapCompta) {
        this.soldeChapCompta = soldeChapCompta;
        return this;
    }

    public void setSoldeChapCompta(Double soldeChapCompta) {
        this.soldeChapCompta = soldeChapCompta;
    }

    public Double getCumulMouvDebitChapCompta() {
        return cumulMouvDebitChapCompta;
    }

    public ChapCompta cumulMouvDebitChapCompta(Double cumulMouvDebitChapCompta) {
        this.cumulMouvDebitChapCompta = cumulMouvDebitChapCompta;
        return this;
    }

    public void setCumulMouvDebitChapCompta(Double cumulMouvDebitChapCompta) {
        this.cumulMouvDebitChapCompta = cumulMouvDebitChapCompta;
    }

    public Double getCumulMouvCreditChapCompta() {
        return cumulMouvCreditChapCompta;
    }

    public ChapCompta cumulMouvCreditChapCompta(Double cumulMouvCreditChapCompta) {
        this.cumulMouvCreditChapCompta = cumulMouvCreditChapCompta;
        return this;
    }

    public void setCumulMouvCreditChapCompta(Double cumulMouvCreditChapCompta) {
        this.cumulMouvCreditChapCompta = cumulMouvCreditChapCompta;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public ChapCompta dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public ChapCompta dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public ChapCompta dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public ChapCompta userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public ChapCompta userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public ChapCompta userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<CompteGene> getCompteGenes() {
        return compteGenes;
    }

    public ChapCompta compteGenes(Set<CompteGene> compteGenes) {
        this.compteGenes = compteGenes;
        return this;
    }

    public ChapCompta addCompteGene(CompteGene compteGene) {
        this.compteGenes.add(compteGene);
        compteGene.setChapitreComptable(this);
        return this;
    }

    public ChapCompta removeCompteGene(CompteGene compteGene) {
        this.compteGenes.remove(compteGene);
        compteGene.setChapitreComptable(null);
        return this;
    }

    public void setCompteGenes(Set<CompteGene> compteGenes) {
        this.compteGenes = compteGenes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChapCompta)) {
            return false;
        }
        return id != null && id.equals(((ChapCompta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ChapCompta{" +
            "id=" + getId() +
            ", numeroChapCompta=" + getNumeroChapCompta() +
            ", libelleChapCompta='" + getLibelleChapCompta() + "'" +
            ", sensChapCompta='" + getSensChapCompta() + "'" +
            ", soldeChapCompta=" + getSoldeChapCompta() +
            ", cumulMouvDebitChapCompta=" + getCumulMouvDebitChapCompta() +
            ", cumulMouvCreditChapCompta=" + getCumulMouvCreditChapCompta() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
