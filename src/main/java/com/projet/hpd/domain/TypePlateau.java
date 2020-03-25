package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypePlateau.
 */
@Entity
@Table(name = "type_plateau")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypePlateau implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_plateau")
    private String codeTypePlateau;

    @Column(name = "libelle_type_plateau")
    private String libelleTypePlateau;

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

    @OneToMany(mappedBy = "typePlateau")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Plateau> plateaus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypePlateau() {
        return codeTypePlateau;
    }

    public TypePlateau codeTypePlateau(String codeTypePlateau) {
        this.codeTypePlateau = codeTypePlateau;
        return this;
    }

    public void setCodeTypePlateau(String codeTypePlateau) {
        this.codeTypePlateau = codeTypePlateau;
    }

    public String getLibelleTypePlateau() {
        return libelleTypePlateau;
    }

    public TypePlateau libelleTypePlateau(String libelleTypePlateau) {
        this.libelleTypePlateau = libelleTypePlateau;
        return this;
    }

    public void setLibelleTypePlateau(String libelleTypePlateau) {
        this.libelleTypePlateau = libelleTypePlateau;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypePlateau dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypePlateau dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypePlateau userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypePlateau userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypePlateau userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Plateau> getPlateaus() {
        return plateaus;
    }

    public TypePlateau plateaus(Set<Plateau> plateaus) {
        this.plateaus = plateaus;
        return this;
    }

    public TypePlateau addPlateau(Plateau plateau) {
        this.plateaus.add(plateau);
        plateau.setTypePlateau(this);
        return this;
    }

    public TypePlateau removePlateau(Plateau plateau) {
        this.plateaus.remove(plateau);
        plateau.setTypePlateau(null);
        return this;
    }

    public void setPlateaus(Set<Plateau> plateaus) {
        this.plateaus = plateaus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypePlateau)) {
            return false;
        }
        return id != null && id.equals(((TypePlateau) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypePlateau{" +
            "id=" + getId() +
            ", codeTypePlateau='" + getCodeTypePlateau() + "'" +
            ", libelleTypePlateau='" + getLibelleTypePlateau() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
