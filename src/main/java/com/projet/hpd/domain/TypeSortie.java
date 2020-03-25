package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeSortie.
 */
@Entity
@Table(name = "type_sortie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeSortie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_sortie")
    private String codeTypeSortie;

    @Column(name = "libelle_type_sortie")
    private String libelleTypeSortie;

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

    @OneToMany(mappedBy = "typeSortie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TypeMvtStock> typeMvtStocks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypeSortie() {
        return codeTypeSortie;
    }

    public TypeSortie codeTypeSortie(String codeTypeSortie) {
        this.codeTypeSortie = codeTypeSortie;
        return this;
    }

    public void setCodeTypeSortie(String codeTypeSortie) {
        this.codeTypeSortie = codeTypeSortie;
    }

    public String getLibelleTypeSortie() {
        return libelleTypeSortie;
    }

    public TypeSortie libelleTypeSortie(String libelleTypeSortie) {
        this.libelleTypeSortie = libelleTypeSortie;
        return this;
    }

    public void setLibelleTypeSortie(String libelleTypeSortie) {
        this.libelleTypeSortie = libelleTypeSortie;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypeSortie dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypeSortie dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypeSortie userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypeSortie userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypeSortie userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<TypeMvtStock> getTypeMvtStocks() {
        return typeMvtStocks;
    }

    public TypeSortie typeMvtStocks(Set<TypeMvtStock> typeMvtStocks) {
        this.typeMvtStocks = typeMvtStocks;
        return this;
    }

    public TypeSortie addTypeMvtStock(TypeMvtStock typeMvtStock) {
        this.typeMvtStocks.add(typeMvtStock);
        typeMvtStock.setTypeSortie(this);
        return this;
    }

    public TypeSortie removeTypeMvtStock(TypeMvtStock typeMvtStock) {
        this.typeMvtStocks.remove(typeMvtStock);
        typeMvtStock.setTypeSortie(null);
        return this;
    }

    public void setTypeMvtStocks(Set<TypeMvtStock> typeMvtStocks) {
        this.typeMvtStocks = typeMvtStocks;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeSortie)) {
            return false;
        }
        return id != null && id.equals(((TypeSortie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeSortie{" +
            "id=" + getId() +
            ", codeTypeSortie='" + getCodeTypeSortie() + "'" +
            ", libelleTypeSortie='" + getLibelleTypeSortie() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
