package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ParamCode.
 */
@Entity
@Table(name = "param_code")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ParamCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_param_code")
    private String codeParamCode;

    @Column(name = "libelle_param_code")
    private String libelleParamCode;

    @Column(name = "description_param_code")
    private String descriptionParamCode;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeParamCode() {
        return codeParamCode;
    }

    public ParamCode codeParamCode(String codeParamCode) {
        this.codeParamCode = codeParamCode;
        return this;
    }

    public void setCodeParamCode(String codeParamCode) {
        this.codeParamCode = codeParamCode;
    }

    public String getLibelleParamCode() {
        return libelleParamCode;
    }

    public ParamCode libelleParamCode(String libelleParamCode) {
        this.libelleParamCode = libelleParamCode;
        return this;
    }

    public void setLibelleParamCode(String libelleParamCode) {
        this.libelleParamCode = libelleParamCode;
    }

    public String getDescriptionParamCode() {
        return descriptionParamCode;
    }

    public ParamCode descriptionParamCode(String descriptionParamCode) {
        this.descriptionParamCode = descriptionParamCode;
        return this;
    }

    public void setDescriptionParamCode(String descriptionParamCode) {
        this.descriptionParamCode = descriptionParamCode;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public ParamCode dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public ParamCode dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public ParamCode userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public ParamCode userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public ParamCode userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParamCode)) {
            return false;
        }
        return id != null && id.equals(((ParamCode) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ParamCode{" +
            "id=" + getId() +
            ", codeParamCode='" + getCodeParamCode() + "'" +
            ", libelleParamCode='" + getLibelleParamCode() + "'" +
            ", descriptionParamCode='" + getDescriptionParamCode() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
