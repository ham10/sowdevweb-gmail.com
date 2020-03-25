package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle_item")
    private String libelleItem;

    @Column(name = "description_item")
    private String descriptionItem;

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

    @OneToMany(mappedBy = "item")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Fonctionnalite> fonctionnalites = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("items")
    private Module module;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleItem() {
        return libelleItem;
    }

    public Item libelleItem(String libelleItem) {
        this.libelleItem = libelleItem;
        return this;
    }

    public void setLibelleItem(String libelleItem) {
        this.libelleItem = libelleItem;
    }

    public String getDescriptionItem() {
        return descriptionItem;
    }

    public Item descriptionItem(String descriptionItem) {
        this.descriptionItem = descriptionItem;
        return this;
    }

    public void setDescriptionItem(String descriptionItem) {
        this.descriptionItem = descriptionItem;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Item dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Item dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Item userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Item userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Item userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Fonctionnalite> getFonctionnalites() {
        return fonctionnalites;
    }

    public Item fonctionnalites(Set<Fonctionnalite> fonctionnalites) {
        this.fonctionnalites = fonctionnalites;
        return this;
    }

    public Item addFonctionnalite(Fonctionnalite fonctionnalite) {
        this.fonctionnalites.add(fonctionnalite);
        fonctionnalite.setItem(this);
        return this;
    }

    public Item removeFonctionnalite(Fonctionnalite fonctionnalite) {
        this.fonctionnalites.remove(fonctionnalite);
        fonctionnalite.setItem(null);
        return this;
    }

    public void setFonctionnalites(Set<Fonctionnalite> fonctionnalites) {
        this.fonctionnalites = fonctionnalites;
    }

    public Module getModule() {
        return module;
    }

    public Item module(Module module) {
        this.module = module;
        return this;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        return id != null && id.equals(((Item) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", libelleItem='" + getLibelleItem() + "'" +
            ", descriptionItem='" + getDescriptionItem() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
