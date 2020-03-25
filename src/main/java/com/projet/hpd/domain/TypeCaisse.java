package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A TypeCaisse.
 */
@Entity
@Table(name = "type_caisse")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypeCaisse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_caisse")
    private String codeTypeCaisse;

    @Column(name = "libelle_type_caisse")
    private String libelleTypeCaisse;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeTypeCaisse() {
        return codeTypeCaisse;
    }

    public TypeCaisse codeTypeCaisse(String codeTypeCaisse) {
        this.codeTypeCaisse = codeTypeCaisse;
        return this;
    }

    public void setCodeTypeCaisse(String codeTypeCaisse) {
        this.codeTypeCaisse = codeTypeCaisse;
    }

    public String getLibelleTypeCaisse() {
        return libelleTypeCaisse;
    }

    public TypeCaisse libelleTypeCaisse(String libelleTypeCaisse) {
        this.libelleTypeCaisse = libelleTypeCaisse;
        return this;
    }

    public void setLibelleTypeCaisse(String libelleTypeCaisse) {
        this.libelleTypeCaisse = libelleTypeCaisse;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypeCaisse dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypeCaisse dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypeCaisse userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypeCaisse userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypeCaisse userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeCaisse)) {
            return false;
        }
        return id != null && id.equals(((TypeCaisse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypeCaisse{" +
            "id=" + getId() +
            ", codeTypeCaisse='" + getCodeTypeCaisse() + "'" +
            ", libelleTypeCaisse='" + getLibelleTypeCaisse() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
