package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A TypePiece.
 */
@Entity
@Table(name = "type_piece")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TypePiece implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_type_piece")
    private String codeTypePiece;

    @Column(name = "libelle_type_piece")
    private String libelleTypePiece;

    @Column(name = "description_type_piece")
    private String descriptionTypePiece;

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

    public String getCodeTypePiece() {
        return codeTypePiece;
    }

    public TypePiece codeTypePiece(String codeTypePiece) {
        this.codeTypePiece = codeTypePiece;
        return this;
    }

    public void setCodeTypePiece(String codeTypePiece) {
        this.codeTypePiece = codeTypePiece;
    }

    public String getLibelleTypePiece() {
        return libelleTypePiece;
    }

    public TypePiece libelleTypePiece(String libelleTypePiece) {
        this.libelleTypePiece = libelleTypePiece;
        return this;
    }

    public void setLibelleTypePiece(String libelleTypePiece) {
        this.libelleTypePiece = libelleTypePiece;
    }

    public String getDescriptionTypePiece() {
        return descriptionTypePiece;
    }

    public TypePiece descriptionTypePiece(String descriptionTypePiece) {
        this.descriptionTypePiece = descriptionTypePiece;
        return this;
    }

    public void setDescriptionTypePiece(String descriptionTypePiece) {
        this.descriptionTypePiece = descriptionTypePiece;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public TypePiece dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public TypePiece dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public TypePiece userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public TypePiece userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public TypePiece userDeleted(Long userDeleted) {
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
        if (!(o instanceof TypePiece)) {
            return false;
        }
        return id != null && id.equals(((TypePiece) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TypePiece{" +
            "id=" + getId() +
            ", codeTypePiece='" + getCodeTypePiece() + "'" +
            ", libelleTypePiece='" + getLibelleTypePiece() + "'" +
            ", descriptionTypePiece='" + getDescriptionTypePiece() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
