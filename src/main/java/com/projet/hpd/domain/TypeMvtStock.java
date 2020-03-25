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
 * A TypeMvtStock.
 */
@Entity
@Table(name = "type_mvt_stock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeMvtStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_mvt_stock")
    private String codeTypeMvtStock;

    @Column(name = "libelle_type_mvt_stock")
    private String libelleTypeMvtStock;

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

    @OneToMany(mappedBy = "typeMvtStock")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Mouvement> mouvements = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("typeMvtStocks")
    private TypeSortie typeSortie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypeMvtStock() {
        return codeTypeMvtStock;
    }

    public TypeMvtStock codeTypeMvtStock(String codeTypeMvtStock) {
        this.codeTypeMvtStock = codeTypeMvtStock;
        return this;
    }

    public void setCodeTypeMvtStock(String codeTypeMvtStock) {
        this.codeTypeMvtStock = codeTypeMvtStock;
    }

    public String getLibelleTypeMvtStock() {
        return libelleTypeMvtStock;
    }

    public TypeMvtStock libelleTypeMvtStock(String libelleTypeMvtStock) {
        this.libelleTypeMvtStock = libelleTypeMvtStock;
        return this;
    }

    public void setLibelleTypeMvtStock(String libelleTypeMvtStock) {
        this.libelleTypeMvtStock = libelleTypeMvtStock;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypeMvtStock dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypeMvtStock dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypeMvtStock userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypeMvtStock userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypeMvtStock userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Mouvement> getMouvements() {
        return mouvements;
    }

    public TypeMvtStock mouvements(Set<Mouvement> mouvements) {
        this.mouvements = mouvements;
        return this;
    }

    public TypeMvtStock addMouvement(Mouvement mouvement) {
        this.mouvements.add(mouvement);
        mouvement.setTypeMvtStock(this);
        return this;
    }

    public TypeMvtStock removeMouvement(Mouvement mouvement) {
        this.mouvements.remove(mouvement);
        mouvement.setTypeMvtStock(null);
        return this;
    }

    public void setMouvements(Set<Mouvement> mouvements) {
        this.mouvements = mouvements;
    }

    public TypeSortie getTypeSortie() {
        return typeSortie;
    }

    public TypeMvtStock typeSortie(TypeSortie typeSortie) {
        this.typeSortie = typeSortie;
        return this;
    }

    public void setTypeSortie(TypeSortie typeSortie) {
        this.typeSortie = typeSortie;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeMvtStock)) {
            return false;
        }
        return id != null && id.equals(((TypeMvtStock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeMvtStock{" +
            "id=" + getId() +
            ", codeTypeMvtStock='" + getCodeTypeMvtStock() + "'" +
            ", libelleTypeMvtStock='" + getLibelleTypeMvtStock() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
