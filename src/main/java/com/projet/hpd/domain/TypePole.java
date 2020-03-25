package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TypePole.
 */
@Entity
@Table(name = "type_pole")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypePole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_pole")
    private String codeTypePole;

    @Column(name = "libelle_type_pole")
    private String libelleTypePole;

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

    @OneToMany(mappedBy = "typePole")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pole> poles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypePole() {
        return codeTypePole;
    }

    public TypePole codeTypePole(String codeTypePole) {
        this.codeTypePole = codeTypePole;
        return this;
    }

    public void setCodeTypePole(String codeTypePole) {
        this.codeTypePole = codeTypePole;
    }

    public String getLibelleTypePole() {
        return libelleTypePole;
    }

    public TypePole libelleTypePole(String libelleTypePole) {
        this.libelleTypePole = libelleTypePole;
        return this;
    }

    public void setLibelleTypePole(String libelleTypePole) {
        this.libelleTypePole = libelleTypePole;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypePole dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypePole dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypePole userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypePole userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypePole userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Pole> getPoles() {
        return poles;
    }

    public TypePole poles(Set<Pole> poles) {
        this.poles = poles;
        return this;
    }

    public TypePole addPole(Pole pole) {
        this.poles.add(pole);
        pole.setTypePole(this);
        return this;
    }

    public TypePole removePole(Pole pole) {
        this.poles.remove(pole);
        pole.setTypePole(null);
        return this;
    }

    public void setPoles(Set<Pole> poles) {
        this.poles = poles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypePole)) {
            return false;
        }
        return id != null && id.equals(((TypePole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypePole{" +
            "id=" + getId() +
            ", codeTypePole='" + getCodeTypePole() + "'" +
            ", libelleTypePole='" + getLibelleTypePole() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
