package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Banque.
 */
@Entity
@Table(name = "banque")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Banque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_banque")
    private String codeBanque;

    @Column(name = "rib_banque")
    private String ribBanque;

    @Column(name = "libelle_banque")
    private String libelleBanque;

    @Column(name = "description_banque")
    private String descriptionBanque;

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
    @JsonIgnoreProperties("banques")
    private CompteGene compteGeneral;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeBanque() {
        return codeBanque;
    }

    public Banque codeBanque(String codeBanque) {
        this.codeBanque = codeBanque;
        return this;
    }

    public void setCodeBanque(String codeBanque) {
        this.codeBanque = codeBanque;
    }

    public String getRibBanque() {
        return ribBanque;
    }

    public Banque ribBanque(String ribBanque) {
        this.ribBanque = ribBanque;
        return this;
    }

    public void setRibBanque(String ribBanque) {
        this.ribBanque = ribBanque;
    }

    public String getLibelleBanque() {
        return libelleBanque;
    }

    public Banque libelleBanque(String libelleBanque) {
        this.libelleBanque = libelleBanque;
        return this;
    }

    public void setLibelleBanque(String libelleBanque) {
        this.libelleBanque = libelleBanque;
    }

    public String getDescriptionBanque() {
        return descriptionBanque;
    }

    public Banque descriptionBanque(String descriptionBanque) {
        this.descriptionBanque = descriptionBanque;
        return this;
    }

    public void setDescriptionBanque(String descriptionBanque) {
        this.descriptionBanque = descriptionBanque;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Banque dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Banque dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Banque userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Banque userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Banque userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public CompteGene getCompteGeneral() {
        return compteGeneral;
    }

    public Banque compteGeneral(CompteGene compteGene) {
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
        if (!(o instanceof Banque)) {
            return false;
        }
        return id != null && id.equals(((Banque) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Banque{" +
            "id=" + getId() +
            ", codeBanque='" + getCodeBanque() + "'" +
            ", ribBanque='" + getRibBanque() + "'" +
            ", libelleBanque='" + getLibelleBanque() + "'" +
            ", descriptionBanque='" + getDescriptionBanque() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
