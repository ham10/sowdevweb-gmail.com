package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A GroupeSan.
 */
@Entity
@Table(name = "groupe_san")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GroupeSan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_groupe_san")
    private Integer codeGroupeSan;

    @Column(name = "libelle_groupe_san")
    private String libelleGroupeSan;

    @Column(name = "description_groupe_san")
    private String descriptionGroupeSan;

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

    public Integer getCodeGroupeSan() {
        return codeGroupeSan;
    }

    public GroupeSan codeGroupeSan(Integer codeGroupeSan) {
        this.codeGroupeSan = codeGroupeSan;
        return this;
    }

    public void setCodeGroupeSan(Integer codeGroupeSan) {
        this.codeGroupeSan = codeGroupeSan;
    }

    public String getLibelleGroupeSan() {
        return libelleGroupeSan;
    }

    public GroupeSan libelleGroupeSan(String libelleGroupeSan) {
        this.libelleGroupeSan = libelleGroupeSan;
        return this;
    }

    public void setLibelleGroupeSan(String libelleGroupeSan) {
        this.libelleGroupeSan = libelleGroupeSan;
    }

    public String getDescriptionGroupeSan() {
        return descriptionGroupeSan;
    }

    public GroupeSan descriptionGroupeSan(String descriptionGroupeSan) {
        this.descriptionGroupeSan = descriptionGroupeSan;
        return this;
    }

    public void setDescriptionGroupeSan(String descriptionGroupeSan) {
        this.descriptionGroupeSan = descriptionGroupeSan;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public GroupeSan dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public GroupeSan dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public GroupeSan userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public GroupeSan userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public GroupeSan userDeleted(Long userDeleted) {
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
        if (!(o instanceof GroupeSan)) {
            return false;
        }
        return id != null && id.equals(((GroupeSan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GroupeSan{" +
            "id=" + getId() +
            ", codeGroupeSan=" + getCodeGroupeSan() +
            ", libelleGroupeSan='" + getLibelleGroupeSan() + "'" +
            ", descriptionGroupeSan='" + getDescriptionGroupeSan() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
