package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Cible.
 */
@Entity
@Table(name = "cible")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cible implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle_cible")
    private String libelleCible;

    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "date_deleted")
    private Instant dateDeleted;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @Column(name = "user_created")
    private Long userCreated;

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

    public String getLibelleCible() {
        return libelleCible;
    }

    public Cible libelleCible(String libelleCible) {
        this.libelleCible = libelleCible;
        return this;
    }

    public void setLibelleCible(String libelleCible) {
        this.libelleCible = libelleCible;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public Cible idUser(Integer idUser) {
        this.idUser = idUser;
        return this;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Instant getDateDeleted() {
        return dateDeleted;
    }

    public Cible dateDeleted(Instant dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(Instant dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Cible dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public Cible dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Cible userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdate() {
        return userUpdate;
    }

    public Cible userUpdate(Long userUpdate) {
        this.userUpdate = userUpdate;
        return this;
    }

    public void setUserUpdate(Long userUpdate) {
        this.userUpdate = userUpdate;
    }

    public Long getUserDelete() {
        return userDelete;
    }

    public Cible userDelete(Long userDelete) {
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
        if (!(o instanceof Cible)) {
            return false;
        }
        return id != null && id.equals(((Cible) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cible{" +
            "id=" + getId() +
            ", libelleCible='" + getLibelleCible() + "'" +
            ", idUser=" + getIdUser() +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdate=" + getUserUpdate() +
            ", userDelete=" + getUserDelete() +
            "}";
    }
}
