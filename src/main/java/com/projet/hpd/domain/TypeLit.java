package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypeLit.
 */
@Entity
@Table(name = "type_lit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeLit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_lit")
    private String codeTypeLit;

    @Column(name = "libelle_type_lit")
    private String libelleTypeLit;

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

    @OneToMany(mappedBy = "typeLit")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lit> lits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypeLit() {
        return codeTypeLit;
    }

    public TypeLit codeTypeLit(String codeTypeLit) {
        this.codeTypeLit = codeTypeLit;
        return this;
    }

    public void setCodeTypeLit(String codeTypeLit) {
        this.codeTypeLit = codeTypeLit;
    }

    public String getLibelleTypeLit() {
        return libelleTypeLit;
    }

    public TypeLit libelleTypeLit(String libelleTypeLit) {
        this.libelleTypeLit = libelleTypeLit;
        return this;
    }

    public void setLibelleTypeLit(String libelleTypeLit) {
        this.libelleTypeLit = libelleTypeLit;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypeLit dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypeLit dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypeLit userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypeLit userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypeLit userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Lit> getLits() {
        return lits;
    }

    public TypeLit lits(Set<Lit> lits) {
        this.lits = lits;
        return this;
    }

    public TypeLit addLit(Lit lit) {
        this.lits.add(lit);
        lit.setTypeLit(this);
        return this;
    }

    public TypeLit removeLit(Lit lit) {
        this.lits.remove(lit);
        lit.setTypeLit(null);
        return this;
    }

    public void setLits(Set<Lit> lits) {
        this.lits = lits;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeLit)) {
            return false;
        }
        return id != null && id.equals(((TypeLit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeLit{" +
            "id=" + getId() +
            ", codeTypeLit='" + getCodeTypeLit() + "'" +
            ", libelleTypeLit='" + getLibelleTypeLit() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
