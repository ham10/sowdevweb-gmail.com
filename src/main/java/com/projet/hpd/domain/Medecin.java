package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Medecin.
 */
@Entity
@Table(name = "medecin")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Medecin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom_medecin")
    private String nomMedecin;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "email")
    private String email;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "genre_medecin")
    private String genreMedecin;

    @Column(name = "nationalite")
    private String nationalite;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "anciennete")
    private Integer anciennete;

    @Column(name = "numero_piece")
    private Integer numeroPiece;

    @Column(name = "grade_medecin")
    private String gradeMedecin;

    @Column(name = "specialite")
    private String specialite;

    @Column(name = "date_embauche")
    private LocalDate dateEmbauche;

    @Column(name = "date_validite")
    private LocalDate dateValidite;

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

    public String getNomMedecin() {
        return nomMedecin;
    }

    public Medecin nomMedecin(String nomMedecin) {
        this.nomMedecin = nomMedecin;
        return this;
    }

    public void setNomMedecin(String nomMedecin) {
        this.nomMedecin = nomMedecin;
    }

    public String getPrenom() {
        return prenom;
    }

    public Medecin prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Medecin adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public Medecin email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Medecin dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getGenreMedecin() {
        return genreMedecin;
    }

    public Medecin genreMedecin(String genreMedecin) {
        this.genreMedecin = genreMedecin;
        return this;
    }

    public void setGenreMedecin(String genreMedecin) {
        this.genreMedecin = genreMedecin;
    }

    public String getNationalite() {
        return nationalite;
    }

    public Medecin nationalite(String nationalite) {
        this.nationalite = nationalite;
        return this;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getTelephone() {
        return telephone;
    }

    public Medecin telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getAnciennete() {
        return anciennete;
    }

    public Medecin anciennete(Integer anciennete) {
        this.anciennete = anciennete;
        return this;
    }

    public void setAnciennete(Integer anciennete) {
        this.anciennete = anciennete;
    }

    public Integer getNumeroPiece() {
        return numeroPiece;
    }

    public Medecin numeroPiece(Integer numeroPiece) {
        this.numeroPiece = numeroPiece;
        return this;
    }

    public void setNumeroPiece(Integer numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

    public String getGradeMedecin() {
        return gradeMedecin;
    }

    public Medecin gradeMedecin(String gradeMedecin) {
        this.gradeMedecin = gradeMedecin;
        return this;
    }

    public void setGradeMedecin(String gradeMedecin) {
        this.gradeMedecin = gradeMedecin;
    }

    public String getSpecialite() {
        return specialite;
    }

    public Medecin specialite(String specialite) {
        this.specialite = specialite;
        return this;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public Medecin dateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
        return this;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public LocalDate getDateValidite() {
        return dateValidite;
    }

    public Medecin dateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
        return this;
    }

    public void setDateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Medecin dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Medecin dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Medecin userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Medecin userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Medecin userDeleted(Long userDeleted) {
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
        if (!(o instanceof Medecin)) {
            return false;
        }
        return id != null && id.equals(((Medecin) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Medecin{" +
            "id=" + getId() +
            ", nomMedecin='" + getNomMedecin() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", email='" + getEmail() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", genreMedecin='" + getGenreMedecin() + "'" +
            ", nationalite='" + getNationalite() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", anciennete=" + getAnciennete() +
            ", numeroPiece=" + getNumeroPiece() +
            ", gradeMedecin='" + getGradeMedecin() + "'" +
            ", specialite='" + getSpecialite() + "'" +
            ", dateEmbauche='" + getDateEmbauche() + "'" +
            ", dateValidite='" + getDateValidite() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
