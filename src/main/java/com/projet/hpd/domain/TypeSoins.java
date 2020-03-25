package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeSoins.
 */
@Entity
@Table(name = "type_soins")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeSoins implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_soins")
    private String codeTypeSoins;

    @Column(name = "libelle_type_soins")
    private String libelleTypeSoins;

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

    @OneToMany(mappedBy = "typeSoins")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tarif> tarifs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypeSoins() {
        return codeTypeSoins;
    }

    public TypeSoins codeTypeSoins(String codeTypeSoins) {
        this.codeTypeSoins = codeTypeSoins;
        return this;
    }

    public void setCodeTypeSoins(String codeTypeSoins) {
        this.codeTypeSoins = codeTypeSoins;
    }

    public String getLibelleTypeSoins() {
        return libelleTypeSoins;
    }

    public TypeSoins libelleTypeSoins(String libelleTypeSoins) {
        this.libelleTypeSoins = libelleTypeSoins;
        return this;
    }

    public void setLibelleTypeSoins(String libelleTypeSoins) {
        this.libelleTypeSoins = libelleTypeSoins;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypeSoins dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypeSoins dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypeSoins userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypeSoins userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypeSoins userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Tarif> getTarifs() {
        return tarifs;
    }

    public TypeSoins tarifs(Set<Tarif> tarifs) {
        this.tarifs = tarifs;
        return this;
    }

    public TypeSoins addTarif(Tarif tarif) {
        this.tarifs.add(tarif);
        tarif.setTypeSoins(this);
        return this;
    }

    public TypeSoins removeTarif(Tarif tarif) {
        this.tarifs.remove(tarif);
        tarif.setTypeSoins(null);
        return this;
    }

    public void setTarifs(Set<Tarif> tarifs) {
        this.tarifs = tarifs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeSoins)) {
            return false;
        }
        return id != null && id.equals(((TypeSoins) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeSoins{" +
            "id=" + getId() +
            ", codeTypeSoins='" + getCodeTypeSoins() + "'" +
            ", libelleTypeSoins='" + getLibelleTypeSoins() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
