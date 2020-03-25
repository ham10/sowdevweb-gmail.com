package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TypeQuestion.
 */
@Entity
@Table(name = "type_question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "belle_type")
    private String belleType;

    @Column(name = "multiplicite_choix")
    private Integer multipliciteChoix;

    @Column(name = "date_deleted")
    private Instant dateDeleted;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @Column(name = "user_update")
    private Long userUpdate;

    @Column(name = "user_delete")
    private Long userDelete;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public TypeQuestion code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBelleType() {
        return belleType;
    }

    public TypeQuestion belleType(String belleType) {
        this.belleType = belleType;
        return this;
    }

    public void setBelleType(String belleType) {
        this.belleType = belleType;
    }

    public Integer getMultipliciteChoix() {
        return multipliciteChoix;
    }

    public TypeQuestion multipliciteChoix(Integer multipliciteChoix) {
        this.multipliciteChoix = multipliciteChoix;
        return this;
    }

    public void setMultipliciteChoix(Integer multipliciteChoix) {
        this.multipliciteChoix = multipliciteChoix;
    }

    public Instant getDateDeleted() {
        return dateDeleted;
    }

    public TypeQuestion dateDeleted(Instant dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(Instant dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public TypeQuestion dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public TypeQuestion dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserUpdate() {
        return userUpdate;
    }

    public TypeQuestion userUpdate(Long userUpdate) {
        this.userUpdate = userUpdate;
        return this;
    }

    public void setUserUpdate(Long userUpdate) {
        this.userUpdate = userUpdate;
    }

    public Long getUserDelete() {
        return userDelete;
    }

    public TypeQuestion userDelete(Long userDelete) {
        this.userDelete = userDelete;
        return this;
    }

    public void setUserDelete(Long userDelete) {
        this.userDelete = userDelete;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeQuestion)) {
            return false;
        }
        return id != null && id.equals(((TypeQuestion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeQuestion{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", belleType='" + getBelleType() + "'" +
            ", multipliciteChoix=" + getMultipliciteChoix() +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userUpdate=" + getUserUpdate() +
            ", userDelete=" + getUserDelete() +
            "}";
    }
}
