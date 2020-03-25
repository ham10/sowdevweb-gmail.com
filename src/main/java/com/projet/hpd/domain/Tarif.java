package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Tarif.
 */
@Entity
@Table(name = "tarif")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tarif implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "montant")
    private Double montant;

    @Column(name = "pourcentage")
    private Integer pourcentage;

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
    @JsonIgnoreProperties("tarifs")
    private TypeSoins typeSoins;

    @ManyToOne
    @JsonIgnoreProperties("tarifs")
    private SousFamille sousfamille;

    @ManyToOne
    @JsonIgnoreProperties("tarifs")
    private ActeMedical actemedical;

    @ManyToOne
    @JsonIgnoreProperties("tarifs")
    private CatChambre categorieChambre;

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

    public Tarif code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Tarif libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getMontant() {
        return montant;
    }

    public Tarif montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Integer getPourcentage() {
        return pourcentage;
    }

    public Tarif pourcentage(Integer pourcentage) {
        this.pourcentage = pourcentage;
        return this;
    }

    public void setPourcentage(Integer pourcentage) {
        this.pourcentage = pourcentage;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Tarif dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Tarif dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDate getDateDeleted() {
        return dateDeleted;
    }

    public Tarif dateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(LocalDate dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Tarif userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Tarif userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Tarif userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public TypeSoins getTypeSoins() {
        return typeSoins;
    }

    public Tarif typeSoins(TypeSoins typeSoins) {
        this.typeSoins = typeSoins;
        return this;
    }

    public void setTypeSoins(TypeSoins typeSoins) {
        this.typeSoins = typeSoins;
    }

    public SousFamille getSousfamille() {
        return sousfamille;
    }

    public Tarif sousfamille(SousFamille sousFamille) {
        this.sousfamille = sousFamille;
        return this;
    }

    public void setSousfamille(SousFamille sousFamille) {
        this.sousfamille = sousFamille;
    }

    public ActeMedical getActemedical() {
        return actemedical;
    }

    public Tarif actemedical(ActeMedical acteMedical) {
        this.actemedical = acteMedical;
        return this;
    }

    public void setActemedical(ActeMedical acteMedical) {
        this.actemedical = acteMedical;
    }

    public CatChambre getCategorieChambre() {
        return categorieChambre;
    }

    public Tarif categorieChambre(CatChambre catChambre) {
        this.categorieChambre = catChambre;
        return this;
    }

    public void setCategorieChambre(CatChambre catChambre) {
        this.categorieChambre = catChambre;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tarif)) {
            return false;
        }
        return id != null && id.equals(((Tarif) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tarif{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", montant=" + getMontant() +
            ", pourcentage=" + getPourcentage() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
