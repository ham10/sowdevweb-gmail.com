package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ParamDivers.
 */
@Entity
@Table(name = "param_divers")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ParamDivers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_param_divers")
    private String codeParamDivers;

    @Column(name = "libelle_param_divers")
    private String libelleParamDivers;

    @Column(name = "description_param_divers")
    private String descriptionParamDivers;

    @Column(name = "valeur_num_1")
    private Integer valeurNum1;

    @Column(name = "valeur_num_2")
    private Integer valeurNum2;

    @Column(name = "valeur_num_3")
    private Integer valeurNum3;

    @Column(name = "valeur_text_1")
    private String valeurText1;

    @Column(name = "valeur_text_2")
    private String valeurText2;

    @Column(name = "valeur_text_3")
    private String valeurText3;

    @Column(name = "valeur_boolean_1")
    private Boolean valeurBoolean1;

    @Column(name = "valeur_boolean_2")
    private Boolean valeurBoolean2;

    @Column(name = "valeur_boolean_3")
    private Boolean valeurBoolean3;

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

    public String getCodeParamDivers() {
        return codeParamDivers;
    }

    public ParamDivers codeParamDivers(String codeParamDivers) {
        this.codeParamDivers = codeParamDivers;
        return this;
    }

    public void setCodeParamDivers(String codeParamDivers) {
        this.codeParamDivers = codeParamDivers;
    }

    public String getLibelleParamDivers() {
        return libelleParamDivers;
    }

    public ParamDivers libelleParamDivers(String libelleParamDivers) {
        this.libelleParamDivers = libelleParamDivers;
        return this;
    }

    public void setLibelleParamDivers(String libelleParamDivers) {
        this.libelleParamDivers = libelleParamDivers;
    }

    public String getDescriptionParamDivers() {
        return descriptionParamDivers;
    }

    public ParamDivers descriptionParamDivers(String descriptionParamDivers) {
        this.descriptionParamDivers = descriptionParamDivers;
        return this;
    }

    public void setDescriptionParamDivers(String descriptionParamDivers) {
        this.descriptionParamDivers = descriptionParamDivers;
    }

    public Integer getValeurNum1() {
        return valeurNum1;
    }

    public ParamDivers valeurNum1(Integer valeurNum1) {
        this.valeurNum1 = valeurNum1;
        return this;
    }

    public void setValeurNum1(Integer valeurNum1) {
        this.valeurNum1 = valeurNum1;
    }

    public Integer getValeurNum2() {
        return valeurNum2;
    }

    public ParamDivers valeurNum2(Integer valeurNum2) {
        this.valeurNum2 = valeurNum2;
        return this;
    }

    public void setValeurNum2(Integer valeurNum2) {
        this.valeurNum2 = valeurNum2;
    }

    public Integer getValeurNum3() {
        return valeurNum3;
    }

    public ParamDivers valeurNum3(Integer valeurNum3) {
        this.valeurNum3 = valeurNum3;
        return this;
    }

    public void setValeurNum3(Integer valeurNum3) {
        this.valeurNum3 = valeurNum3;
    }

    public String getValeurText1() {
        return valeurText1;
    }

    public ParamDivers valeurText1(String valeurText1) {
        this.valeurText1 = valeurText1;
        return this;
    }

    public void setValeurText1(String valeurText1) {
        this.valeurText1 = valeurText1;
    }

    public String getValeurText2() {
        return valeurText2;
    }

    public ParamDivers valeurText2(String valeurText2) {
        this.valeurText2 = valeurText2;
        return this;
    }

    public void setValeurText2(String valeurText2) {
        this.valeurText2 = valeurText2;
    }

    public String getValeurText3() {
        return valeurText3;
    }

    public ParamDivers valeurText3(String valeurText3) {
        this.valeurText3 = valeurText3;
        return this;
    }

    public void setValeurText3(String valeurText3) {
        this.valeurText3 = valeurText3;
    }

    public Boolean isValeurBoolean1() {
        return valeurBoolean1;
    }

    public ParamDivers valeurBoolean1(Boolean valeurBoolean1) {
        this.valeurBoolean1 = valeurBoolean1;
        return this;
    }

    public void setValeurBoolean1(Boolean valeurBoolean1) {
        this.valeurBoolean1 = valeurBoolean1;
    }

    public Boolean isValeurBoolean2() {
        return valeurBoolean2;
    }

    public ParamDivers valeurBoolean2(Boolean valeurBoolean2) {
        this.valeurBoolean2 = valeurBoolean2;
        return this;
    }

    public void setValeurBoolean2(Boolean valeurBoolean2) {
        this.valeurBoolean2 = valeurBoolean2;
    }

    public Boolean isValeurBoolean3() {
        return valeurBoolean3;
    }

    public ParamDivers valeurBoolean3(Boolean valeurBoolean3) {
        this.valeurBoolean3 = valeurBoolean3;
        return this;
    }

    public void setValeurBoolean3(Boolean valeurBoolean3) {
        this.valeurBoolean3 = valeurBoolean3;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public ParamDivers dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public ParamDivers dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public ParamDivers userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public ParamDivers userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public ParamDivers userDeleted(Long userDeleted) {
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
        if (!(o instanceof ParamDivers)) {
            return false;
        }
        return id != null && id.equals(((ParamDivers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ParamDivers{" +
            "id=" + getId() +
            ", codeParamDivers='" + getCodeParamDivers() + "'" +
            ", libelleParamDivers='" + getLibelleParamDivers() + "'" +
            ", descriptionParamDivers='" + getDescriptionParamDivers() + "'" +
            ", valeurNum1=" + getValeurNum1() +
            ", valeurNum2=" + getValeurNum2() +
            ", valeurNum3=" + getValeurNum3() +
            ", valeurText1='" + getValeurText1() + "'" +
            ", valeurText2='" + getValeurText2() + "'" +
            ", valeurText3='" + getValeurText3() + "'" +
            ", valeurBoolean1='" + isValeurBoolean1() + "'" +
            ", valeurBoolean2='" + isValeurBoolean2() + "'" +
            ", valeurBoolean3='" + isValeurBoolean3() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
