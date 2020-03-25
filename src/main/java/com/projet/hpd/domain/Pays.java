package com.projet.hpd.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * The Enter the entity name entity.\n@author A true hipster
 */
@ApiModel(description = "The Enter the entity name entity.\n@author A true hipster")
@Entity
@Table(name = "pays")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pays implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_pays")
    private String codePays;

    @Column(name = "libelle_pays")
    private String libellePays;

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

    @OneToMany(mappedBy = "pays")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Region> regions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodePays() {
        return codePays;
    }

    public Pays codePays(String codePays) {
        this.codePays = codePays;
        return this;
    }

    public void setCodePays(String codePays) {
        this.codePays = codePays;
    }

    public String getLibellePays() {
        return libellePays;
    }

    public Pays libellePays(String libellePays) {
        this.libellePays = libellePays;
        return this;
    }

    public void setLibellePays(String libellePays) {
        this.libellePays = libellePays;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Pays dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Pays dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Pays userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Pays userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Pays userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public Pays regions(Set<Region> regions) {
        this.regions = regions;
        return this;
    }

    public Pays addRegion(Region region) {
        this.regions.add(region);
        region.setPays(this);
        return this;
    }

    public Pays removeRegion(Region region) {
        this.regions.remove(region);
        region.setPays(null);
        return this;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pays)) {
            return false;
        }
        return id != null && id.equals(((Pays) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pays{" +
            "id=" + getId() +
            ", codePays='" + getCodePays() + "'" +
            ", libellePays='" + getLibellePays() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
