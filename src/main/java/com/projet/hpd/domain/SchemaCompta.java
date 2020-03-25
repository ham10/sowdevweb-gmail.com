package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A SchemaCompta.
 */
@Entity
@Table(name = "schema_compta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchemaCompta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private Integer code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "sens")
    private String sens;

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

    @OneToOne
    @JoinColumn(unique = true)
    private Operation operation;

    @ManyToOne
    @JsonIgnoreProperties("schemaComptas")
    private NatureOp natureOperation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public SchemaCompta code(Integer code) {
        this.code = code;
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public SchemaCompta libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getSens() {
        return sens;
    }

    public SchemaCompta sens(String sens) {
        this.sens = sens;
        return this;
    }

    public void setSens(String sens) {
        this.sens = sens;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public SchemaCompta dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public SchemaCompta dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public SchemaCompta dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public SchemaCompta userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public SchemaCompta userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public SchemaCompta userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Operation getOperation() {
        return operation;
    }

    public SchemaCompta operation(Operation operation) {
        this.operation = operation;
        return this;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public NatureOp getNatureOperation() {
        return natureOperation;
    }

    public SchemaCompta natureOperation(NatureOp natureOp) {
        this.natureOperation = natureOp;
        return this;
    }

    public void setNatureOperation(NatureOp natureOp) {
        this.natureOperation = natureOp;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchemaCompta)) {
            return false;
        }
        return id != null && id.equals(((SchemaCompta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SchemaCompta{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", libelle='" + getLibelle() + "'" +
            ", sens='" + getSens() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
