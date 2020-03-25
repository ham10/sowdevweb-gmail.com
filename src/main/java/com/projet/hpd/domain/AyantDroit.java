package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A AyantDroit.
 */
@Entity
@Table(name = "ayant_droit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AyantDroit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "nom")
    private String nom;

    @Column(name = "lien_parente")
    private String lienParente;

    @Column(name = "date_nais")
    private LocalDate dateNais;

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

    @ManyToOne
    @JsonIgnoreProperties("ayantDroits")
    private TypePrCharge typePrCharge;

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

    public AyantDroit code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrenom() {
        return prenom;
    }

    public AyantDroit prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public AyantDroit nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLienParente() {
        return lienParente;
    }

    public AyantDroit lienParente(String lienParente) {
        this.lienParente = lienParente;
        return this;
    }

    public void setLienParente(String lienParente) {
        this.lienParente = lienParente;
    }

    public LocalDate getDateNais() {
        return dateNais;
    }

    public AyantDroit dateNais(LocalDate dateNais) {
        this.dateNais = dateNais;
        return this;
    }

    public void setDateNais(LocalDate dateNais) {
        this.dateNais = dateNais;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public AyantDroit dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public AyantDroit dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public AyantDroit dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public AyantDroit userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public AyantDroit userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public AyantDroit userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public TypePrCharge getTypePrCharge() {
        return typePrCharge;
    }

    public AyantDroit typePrCharge(TypePrCharge typePrCharge) {
        this.typePrCharge = typePrCharge;
        return this;
    }

    public void setTypePrCharge(TypePrCharge typePrCharge) {
        this.typePrCharge = typePrCharge;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AyantDroit)) {
            return false;
        }
        return id != null && id.equals(((AyantDroit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AyantDroit{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", lienParente='" + getLienParente() + "'" +
            ", dateNais='" + getDateNais() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
