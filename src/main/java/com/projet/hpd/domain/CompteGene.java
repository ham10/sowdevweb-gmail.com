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
 * A CompteGene.
 */
@Entity
@Table(name = "compte_gene")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompteGene implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_compte_gene")
    private Integer numeroCompteGene;

    @Column(name = "libelle_compte_gene")
    private String libelleCompteGene;

    @Column(name = "sens_compte_gene")
    private String sensCompteGene;

    @Column(name = "solde_compte_gene")
    private Double soldeCompteGene;

    @Column(name = "cumul_mouv_debit_compte_gene")
    private Double cumulMouvDebitCompteGene;

    @Column(name = "cumul_mouv_credit_compte_gene")
    private Double cumulMouvCreditCompteGene;

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

    @OneToMany(mappedBy = "compteGeneral")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CodeBudget> codeBudgets = new HashSet<>();

    @OneToMany(mappedBy = "compteGeneral")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Banque> banques = new HashSet<>();

    @OneToMany(mappedBy = "compteGeneral")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Services> services = new HashSet<>();

    @OneToMany(mappedBy = "compteGeneral")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Compte> comptes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("compteGenes")
    private ChapCompta chapitreComptable;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroCompteGene() {
        return numeroCompteGene;
    }

    public CompteGene numeroCompteGene(Integer numeroCompteGene) {
        this.numeroCompteGene = numeroCompteGene;
        return this;
    }

    public void setNumeroCompteGene(Integer numeroCompteGene) {
        this.numeroCompteGene = numeroCompteGene;
    }

    public String getLibelleCompteGene() {
        return libelleCompteGene;
    }

    public CompteGene libelleCompteGene(String libelleCompteGene) {
        this.libelleCompteGene = libelleCompteGene;
        return this;
    }

    public void setLibelleCompteGene(String libelleCompteGene) {
        this.libelleCompteGene = libelleCompteGene;
    }

    public String getSensCompteGene() {
        return sensCompteGene;
    }

    public CompteGene sensCompteGene(String sensCompteGene) {
        this.sensCompteGene = sensCompteGene;
        return this;
    }

    public void setSensCompteGene(String sensCompteGene) {
        this.sensCompteGene = sensCompteGene;
    }

    public Double getSoldeCompteGene() {
        return soldeCompteGene;
    }

    public CompteGene soldeCompteGene(Double soldeCompteGene) {
        this.soldeCompteGene = soldeCompteGene;
        return this;
    }

    public void setSoldeCompteGene(Double soldeCompteGene) {
        this.soldeCompteGene = soldeCompteGene;
    }

    public Double getCumulMouvDebitCompteGene() {
        return cumulMouvDebitCompteGene;
    }

    public CompteGene cumulMouvDebitCompteGene(Double cumulMouvDebitCompteGene) {
        this.cumulMouvDebitCompteGene = cumulMouvDebitCompteGene;
        return this;
    }

    public void setCumulMouvDebitCompteGene(Double cumulMouvDebitCompteGene) {
        this.cumulMouvDebitCompteGene = cumulMouvDebitCompteGene;
    }

    public Double getCumulMouvCreditCompteGene() {
        return cumulMouvCreditCompteGene;
    }

    public CompteGene cumulMouvCreditCompteGene(Double cumulMouvCreditCompteGene) {
        this.cumulMouvCreditCompteGene = cumulMouvCreditCompteGene;
        return this;
    }

    public void setCumulMouvCreditCompteGene(Double cumulMouvCreditCompteGene) {
        this.cumulMouvCreditCompteGene = cumulMouvCreditCompteGene;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public CompteGene dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public CompteGene dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public CompteGene dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public CompteGene userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public CompteGene userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public CompteGene userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<CodeBudget> getCodeBudgets() {
        return codeBudgets;
    }

    public CompteGene codeBudgets(Set<CodeBudget> codeBudgets) {
        this.codeBudgets = codeBudgets;
        return this;
    }

    public CompteGene addCodeBudget(CodeBudget codeBudget) {
        this.codeBudgets.add(codeBudget);
        codeBudget.setCompteGeneral(this);
        return this;
    }

    public CompteGene removeCodeBudget(CodeBudget codeBudget) {
        this.codeBudgets.remove(codeBudget);
        codeBudget.setCompteGeneral(null);
        return this;
    }

    public void setCodeBudgets(Set<CodeBudget> codeBudgets) {
        this.codeBudgets = codeBudgets;
    }

    public Set<Banque> getBanques() {
        return banques;
    }

    public CompteGene banques(Set<Banque> banques) {
        this.banques = banques;
        return this;
    }

    public CompteGene addBanque(Banque banque) {
        this.banques.add(banque);
        banque.setCompteGeneral(this);
        return this;
    }

    public CompteGene removeBanque(Banque banque) {
        this.banques.remove(banque);
        banque.setCompteGeneral(null);
        return this;
    }

    public void setBanques(Set<Banque> banques) {
        this.banques = banques;
    }

    public Set<Services> getServices() {
        return services;
    }

    public CompteGene services(Set<Services> services) {
        this.services = services;
        return this;
    }

    public CompteGene addServices(Services services) {
        this.services.add(services);
        services.setCompteGeneral(this);
        return this;
    }

    public CompteGene removeServices(Services services) {
        this.services.remove(services);
        services.setCompteGeneral(null);
        return this;
    }

    public void setServices(Set<Services> services) {
        this.services = services;
    }

    public Set<Compte> getComptes() {
        return comptes;
    }

    public CompteGene comptes(Set<Compte> comptes) {
        this.comptes = comptes;
        return this;
    }

    public CompteGene addCompte(Compte compte) {
        this.comptes.add(compte);
        compte.setCompteGeneral(this);
        return this;
    }

    public CompteGene removeCompte(Compte compte) {
        this.comptes.remove(compte);
        compte.setCompteGeneral(null);
        return this;
    }

    public void setComptes(Set<Compte> comptes) {
        this.comptes = comptes;
    }

    public ChapCompta getChapitreComptable() {
        return chapitreComptable;
    }

    public CompteGene chapitreComptable(ChapCompta chapCompta) {
        this.chapitreComptable = chapCompta;
        return this;
    }

    public void setChapitreComptable(ChapCompta chapCompta) {
        this.chapitreComptable = chapCompta;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompteGene)) {
            return false;
        }
        return id != null && id.equals(((CompteGene) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CompteGene{" +
            "id=" + getId() +
            ", numeroCompteGene=" + getNumeroCompteGene() +
            ", libelleCompteGene='" + getLibelleCompteGene() + "'" +
            ", sensCompteGene='" + getSensCompteGene() + "'" +
            ", soldeCompteGene=" + getSoldeCompteGene() +
            ", cumulMouvDebitCompteGene=" + getCumulMouvDebitCompteGene() +
            ", cumulMouvCreditCompteGene=" + getCumulMouvCreditCompteGene() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
