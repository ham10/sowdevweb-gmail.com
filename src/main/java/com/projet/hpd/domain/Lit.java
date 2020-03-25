package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Lit.
 */
@Entity
@Table(name = "lit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_lit")
    private Integer numeroLit;

    @Column(name = "description_lit")
    private String descriptionLit;

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
    @JsonIgnoreProperties("lits")
    private TypeLit typeLit;

    @ManyToOne
    @JsonIgnoreProperties("lits")
    private Boxe box;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroLit() {
        return numeroLit;
    }

    public Lit numeroLit(Integer numeroLit) {
        this.numeroLit = numeroLit;
        return this;
    }

    public void setNumeroLit(Integer numeroLit) {
        this.numeroLit = numeroLit;
    }

    public String getDescriptionLit() {
        return descriptionLit;
    }

    public Lit descriptionLit(String descriptionLit) {
        this.descriptionLit = descriptionLit;
        return this;
    }

    public void setDescriptionLit(String descriptionLit) {
        this.descriptionLit = descriptionLit;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Lit dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Lit dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Lit userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Lit userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Lit userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public TypeLit getTypeLit() {
        return typeLit;
    }

    public Lit typeLit(TypeLit typeLit) {
        this.typeLit = typeLit;
        return this;
    }

    public void setTypeLit(TypeLit typeLit) {
        this.typeLit = typeLit;
    }

    public Boxe getBox() {
        return box;
    }

    public Lit box(Boxe boxe) {
        this.box = boxe;
        return this;
    }

    public void setBox(Boxe boxe) {
        this.box = boxe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lit)) {
            return false;
        }
        return id != null && id.equals(((Lit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Lit{" +
            "id=" + getId() +
            ", numeroLit=" + getNumeroLit() +
            ", descriptionLit='" + getDescriptionLit() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
