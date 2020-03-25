package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Fonctionnalite.
 */
@Entity
@Table(name = "fonctionnalite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fonctionnalite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle_fonctionnalite")
    private String libelleFonctionnalite;

    @Column(name = "description_fonctionnalite")
    private String descriptionFonctionnalite;

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
    @JsonIgnoreProperties("fonctionnalites")
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleFonctionnalite() {
        return libelleFonctionnalite;
    }

    public Fonctionnalite libelleFonctionnalite(String libelleFonctionnalite) {
        this.libelleFonctionnalite = libelleFonctionnalite;
        return this;
    }

    public void setLibelleFonctionnalite(String libelleFonctionnalite) {
        this.libelleFonctionnalite = libelleFonctionnalite;
    }

    public String getDescriptionFonctionnalite() {
        return descriptionFonctionnalite;
    }

    public Fonctionnalite descriptionFonctionnalite(String descriptionFonctionnalite) {
        this.descriptionFonctionnalite = descriptionFonctionnalite;
        return this;
    }

    public void setDescriptionFonctionnalite(String descriptionFonctionnalite) {
        this.descriptionFonctionnalite = descriptionFonctionnalite;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Fonctionnalite dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Fonctionnalite dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Fonctionnalite userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Fonctionnalite userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Fonctionnalite userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Item getItem() {
        return item;
    }

    public Fonctionnalite item(Item item) {
        this.item = item;
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fonctionnalite)) {
            return false;
        }
        return id != null && id.equals(((Fonctionnalite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Fonctionnalite{" +
            "id=" + getId() +
            ", libelleFonctionnalite='" + getLibelleFonctionnalite() + "'" +
            ", descriptionFonctionnalite='" + getDescriptionFonctionnalite() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
