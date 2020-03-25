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
 * A Boxe.
 */
@Entity
@Table(name = "boxe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Boxe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_boxe")
    private Integer numeroBoxe;

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

    @OneToMany(mappedBy = "box")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lit> lits = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("boxes")
    private Chambre chambre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroBoxe() {
        return numeroBoxe;
    }

    public Boxe numeroBoxe(Integer numeroBoxe) {
        this.numeroBoxe = numeroBoxe;
        return this;
    }

    public void setNumeroBoxe(Integer numeroBoxe) {
        this.numeroBoxe = numeroBoxe;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Boxe dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Boxe dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Boxe userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Boxe userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Boxe userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Lit> getLits() {
        return lits;
    }

    public Boxe lits(Set<Lit> lits) {
        this.lits = lits;
        return this;
    }

    public Boxe addLit(Lit lit) {
        this.lits.add(lit);
        lit.setBox(this);
        return this;
    }

    public Boxe removeLit(Lit lit) {
        this.lits.remove(lit);
        lit.setBox(null);
        return this;
    }

    public void setLits(Set<Lit> lits) {
        this.lits = lits;
    }

    public Chambre getChambre() {
        return chambre;
    }

    public Boxe chambre(Chambre chambre) {
        this.chambre = chambre;
        return this;
    }

    public void setChambre(Chambre chambre) {
        this.chambre = chambre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Boxe)) {
            return false;
        }
        return id != null && id.equals(((Boxe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Boxe{" +
            "id=" + getId() +
            ", numeroBoxe=" + getNumeroBoxe() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
