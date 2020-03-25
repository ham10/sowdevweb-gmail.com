package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeImmo.
 */
@Entity
@Table(name = "type_immo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeImmo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "mode_calcul")
    private String modeCalcul;

    @Column(name = "taux")
    private Double taux;

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

    @OneToMany(mappedBy = "typeImmo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Immo> immos = new HashSet<>();

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

    public TypeImmo code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getModeCalcul() {
        return modeCalcul;
    }

    public TypeImmo modeCalcul(String modeCalcul) {
        this.modeCalcul = modeCalcul;
        return this;
    }

    public void setModeCalcul(String modeCalcul) {
        this.modeCalcul = modeCalcul;
    }

    public Double getTaux() {
        return taux;
    }

    public TypeImmo taux(Double taux) {
        this.taux = taux;
        return this;
    }

    public void setTaux(Double taux) {
        this.taux = taux;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypeImmo dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypeImmo dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public TypeImmo dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypeImmo userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypeImmo userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypeImmo userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Immo> getImmos() {
        return immos;
    }

    public TypeImmo immos(Set<Immo> immos) {
        this.immos = immos;
        return this;
    }

    public TypeImmo addImmo(Immo immo) {
        this.immos.add(immo);
        immo.setTypeImmo(this);
        return this;
    }

    public TypeImmo removeImmo(Immo immo) {
        this.immos.remove(immo);
        immo.setTypeImmo(null);
        return this;
    }

    public void setImmos(Set<Immo> immos) {
        this.immos = immos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeImmo)) {
            return false;
        }
        return id != null && id.equals(((TypeImmo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeImmo{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", modeCalcul='" + getModeCalcul() + "'" +
            ", taux=" + getTaux() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
