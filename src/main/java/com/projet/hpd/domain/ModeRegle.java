package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ModeRegle.
 */
@Entity
@Table(name = "mode_regle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ModeRegle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_mode_regle")
    private String codeModeRegle;

    @Column(name = "libelle_mode_regle")
    private String libelleModeRegle;

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

    public String getCodeModeRegle() {
        return codeModeRegle;
    }

    public ModeRegle codeModeRegle(String codeModeRegle) {
        this.codeModeRegle = codeModeRegle;
        return this;
    }

    public void setCodeModeRegle(String codeModeRegle) {
        this.codeModeRegle = codeModeRegle;
    }

    public String getLibelleModeRegle() {
        return libelleModeRegle;
    }

    public ModeRegle libelleModeRegle(String libelleModeRegle) {
        this.libelleModeRegle = libelleModeRegle;
        return this;
    }

    public void setLibelleModeRegle(String libelleModeRegle) {
        this.libelleModeRegle = libelleModeRegle;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public ModeRegle dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public ModeRegle dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public ModeRegle userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public ModeRegle userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public ModeRegle userDeleted(Long userDeleted) {
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
        if (!(o instanceof ModeRegle)) {
            return false;
        }
        return id != null && id.equals(((ModeRegle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ModeRegle{" +
            "id=" + getId() +
            ", codeModeRegle='" + getCodeModeRegle() + "'" +
            ", libelleModeRegle='" + getLibelleModeRegle() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
