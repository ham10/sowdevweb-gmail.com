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
 * A CatChambre.
 */
@Entity
@Table(name = "cat_chambre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CatChambre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle_cat_chambre")
    private String libelleCatChambre;

    @Column(name = "description_cat_chambre")
    private String descriptionCatChambre;

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

    @OneToMany(mappedBy = "categorieChambre")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Chambre> chambres = new HashSet<>();

    @OneToMany(mappedBy = "categorieChambre")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tarif> tarifs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("catChambres")
    private Services serv;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleCatChambre() {
        return libelleCatChambre;
    }

    public CatChambre libelleCatChambre(String libelleCatChambre) {
        this.libelleCatChambre = libelleCatChambre;
        return this;
    }

    public void setLibelleCatChambre(String libelleCatChambre) {
        this.libelleCatChambre = libelleCatChambre;
    }

    public String getDescriptionCatChambre() {
        return descriptionCatChambre;
    }

    public CatChambre descriptionCatChambre(String descriptionCatChambre) {
        this.descriptionCatChambre = descriptionCatChambre;
        return this;
    }

    public void setDescriptionCatChambre(String descriptionCatChambre) {
        this.descriptionCatChambre = descriptionCatChambre;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public CatChambre dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public CatChambre dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public CatChambre userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public CatChambre userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public CatChambre userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Chambre> getChambres() {
        return chambres;
    }

    public CatChambre chambres(Set<Chambre> chambres) {
        this.chambres = chambres;
        return this;
    }

    public CatChambre addChambre(Chambre chambre) {
        this.chambres.add(chambre);
        chambre.setCategorieChambre(this);
        return this;
    }

    public CatChambre removeChambre(Chambre chambre) {
        this.chambres.remove(chambre);
        chambre.setCategorieChambre(null);
        return this;
    }

    public void setChambres(Set<Chambre> chambres) {
        this.chambres = chambres;
    }

    public Set<Tarif> getTarifs() {
        return tarifs;
    }

    public CatChambre tarifs(Set<Tarif> tarifs) {
        this.tarifs = tarifs;
        return this;
    }

    public CatChambre addTarif(Tarif tarif) {
        this.tarifs.add(tarif);
        tarif.setCategorieChambre(this);
        return this;
    }

    public CatChambre removeTarif(Tarif tarif) {
        this.tarifs.remove(tarif);
        tarif.setCategorieChambre(null);
        return this;
    }

    public void setTarifs(Set<Tarif> tarifs) {
        this.tarifs = tarifs;
    }

    public Services getServ() {
        return serv;
    }

    public CatChambre serv(Services services) {
        this.serv = services;
        return this;
    }

    public void setServ(Services services) {
        this.serv = services;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatChambre)) {
            return false;
        }
        return id != null && id.equals(((CatChambre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CatChambre{" +
            "id=" + getId() +
            ", libelleCatChambre='" + getLibelleCatChambre() + "'" +
            ", descriptionCatChambre='" + getDescriptionCatChambre() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
