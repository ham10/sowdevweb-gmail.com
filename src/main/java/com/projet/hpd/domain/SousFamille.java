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
 * A SousFamille.
 */
@Entity
@Table(name = "sous_famille")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SousFamille implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "description")
    private String description;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_updated")
    private LocalDate dateUpdated;

    @Column(name = "date_deleted")
    private LocalDate dateDeleted;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_updated")
    private Long userUpdated;

    @Column(name = "user_deleted")
    private Long userDeleted;

    @OneToMany(mappedBy = "sousfamille")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tarif> tarifs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("sousFamilles")
    private Famille famille;

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

    public SousFamille code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public SousFamille libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public SousFamille description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public SousFamille dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public SousFamille dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public SousFamille dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public SousFamille userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public SousFamille userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public SousFamille userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Tarif> getTarifs() {
        return tarifs;
    }

    public SousFamille tarifs(Set<Tarif> tarifs) {
        this.tarifs = tarifs;
        return this;
    }

    public SousFamille addTarif(Tarif tarif) {
        this.tarifs.add(tarif);
        tarif.setSousfamille(this);
        return this;
    }

    public SousFamille removeTarif(Tarif tarif) {
        this.tarifs.remove(tarif);
        tarif.setSousfamille(null);
        return this;
    }

    public void setTarifs(Set<Tarif> tarifs) {
        this.tarifs = tarifs;
    }

    public Famille getFamille() {
        return famille;
    }

    public SousFamille famille(Famille famille) {
        this.famille = famille;
        return this;
    }

    public void setFamille(Famille famille) {
        this.famille = famille;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SousFamille)) {
            return false;
        }
        return id != null && id.equals(((SousFamille) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SousFamille{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
