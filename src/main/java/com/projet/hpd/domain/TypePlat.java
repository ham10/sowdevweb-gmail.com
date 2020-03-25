package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypePlat.
 */
@Entity
@Table(name = "type_plat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypePlat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_plat")
    private String codeTypePlat;

    @Column(name = "libelle_type_plat")
    private String libelleTypePlat;

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

    @OneToMany(mappedBy = "typePlat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Plat> plats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypePlat() {
        return codeTypePlat;
    }

    public TypePlat codeTypePlat(String codeTypePlat) {
        this.codeTypePlat = codeTypePlat;
        return this;
    }

    public void setCodeTypePlat(String codeTypePlat) {
        this.codeTypePlat = codeTypePlat;
    }

    public String getLibelleTypePlat() {
        return libelleTypePlat;
    }

    public TypePlat libelleTypePlat(String libelleTypePlat) {
        this.libelleTypePlat = libelleTypePlat;
        return this;
    }

    public void setLibelleTypePlat(String libelleTypePlat) {
        this.libelleTypePlat = libelleTypePlat;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypePlat dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypePlat dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypePlat userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypePlat userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypePlat userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Plat> getPlats() {
        return plats;
    }

    public TypePlat plats(Set<Plat> plats) {
        this.plats = plats;
        return this;
    }

    public TypePlat addPlat(Plat plat) {
        this.plats.add(plat);
        plat.setTypePlat(this);
        return this;
    }

    public TypePlat removePlat(Plat plat) {
        this.plats.remove(plat);
        plat.setTypePlat(null);
        return this;
    }

    public void setPlats(Set<Plat> plats) {
        this.plats = plats;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypePlat)) {
            return false;
        }
        return id != null && id.equals(((TypePlat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypePlat{" +
            "id=" + getId() +
            ", codeTypePlat='" + getCodeTypePlat() + "'" +
            ", libelleTypePlat='" + getLibelleTypePlat() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
