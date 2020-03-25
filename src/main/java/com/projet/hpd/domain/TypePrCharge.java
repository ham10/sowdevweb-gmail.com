package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypePrCharge.
 */
@Entity
@Table(name = "type_pr_charge")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypePrCharge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_pr_charge")
    private String codeTypePrCharge;

    @Column(name = "libelle_type_pr_charge")
    private String libelleTypePrCharge;

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

    @OneToMany(mappedBy = "typePrCharge")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AyantDroit> ayantDroits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypePrCharge() {
        return codeTypePrCharge;
    }

    public TypePrCharge codeTypePrCharge(String codeTypePrCharge) {
        this.codeTypePrCharge = codeTypePrCharge;
        return this;
    }

    public void setCodeTypePrCharge(String codeTypePrCharge) {
        this.codeTypePrCharge = codeTypePrCharge;
    }

    public String getLibelleTypePrCharge() {
        return libelleTypePrCharge;
    }

    public TypePrCharge libelleTypePrCharge(String libelleTypePrCharge) {
        this.libelleTypePrCharge = libelleTypePrCharge;
        return this;
    }

    public void setLibelleTypePrCharge(String libelleTypePrCharge) {
        this.libelleTypePrCharge = libelleTypePrCharge;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypePrCharge dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypePrCharge dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypePrCharge userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypePrCharge userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypePrCharge userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<AyantDroit> getAyantDroits() {
        return ayantDroits;
    }

    public TypePrCharge ayantDroits(Set<AyantDroit> ayantDroits) {
        this.ayantDroits = ayantDroits;
        return this;
    }

    public TypePrCharge addAyantDroit(AyantDroit ayantDroit) {
        this.ayantDroits.add(ayantDroit);
        ayantDroit.setTypePrCharge(this);
        return this;
    }

    public TypePrCharge removeAyantDroit(AyantDroit ayantDroit) {
        this.ayantDroits.remove(ayantDroit);
        ayantDroit.setTypePrCharge(null);
        return this;
    }

    public void setAyantDroits(Set<AyantDroit> ayantDroits) {
        this.ayantDroits = ayantDroits;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypePrCharge)) {
            return false;
        }
        return id != null && id.equals(((TypePrCharge) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypePrCharge{" +
            "id=" + getId() +
            ", codeTypePrCharge='" + getCodeTypePrCharge() + "'" +
            ", libelleTypePrCharge='" + getLibelleTypePrCharge() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
