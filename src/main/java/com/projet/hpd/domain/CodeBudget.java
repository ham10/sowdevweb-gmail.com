package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CodeBudget.
 */
@Entity
@Table(name = "code_budget")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CodeBudget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_code_budget")
    private Integer codeCodeBudget;

    @Column(name = "libelle_code_budget")
    private String libelleCodeBudget;

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

    @ManyToOne
    @JsonIgnoreProperties("codeBudgets")
    private CompteGene compteGeneral;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodeCodeBudget() {
        return codeCodeBudget;
    }

    public CodeBudget codeCodeBudget(Integer codeCodeBudget) {
        this.codeCodeBudget = codeCodeBudget;
        return this;
    }

    public void setCodeCodeBudget(Integer codeCodeBudget) {
        this.codeCodeBudget = codeCodeBudget;
    }

    public String getLibelleCodeBudget() {
        return libelleCodeBudget;
    }

    public CodeBudget libelleCodeBudget(String libelleCodeBudget) {
        this.libelleCodeBudget = libelleCodeBudget;
        return this;
    }

    public void setLibelleCodeBudget(String libelleCodeBudget) {
        this.libelleCodeBudget = libelleCodeBudget;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public CodeBudget dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public CodeBudget dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public CodeBudget dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public CodeBudget userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public CodeBudget userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public CodeBudget userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public CompteGene getCompteGeneral() {
        return compteGeneral;
    }

    public CodeBudget compteGeneral(CompteGene compteGene) {
        this.compteGeneral = compteGene;
        return this;
    }

    public void setCompteGeneral(CompteGene compteGene) {
        this.compteGeneral = compteGene;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CodeBudget)) {
            return false;
        }
        return id != null && id.equals(((CodeBudget) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CodeBudget{" +
            "id=" + getId() +
            ", codeCodeBudget=" + getCodeCodeBudget() +
            ", libelleCodeBudget='" + getLibelleCodeBudget() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
