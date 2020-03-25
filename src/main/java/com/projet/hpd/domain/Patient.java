package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code_patient", nullable = false)
    private String codePatient;

    @NotNull
    @Column(name = "nom_patient", nullable = false)
    private String nomPatient;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @NotNull
    @Column(name = "genre", nullable = false)
    private String genre;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "numero_piece")
    private String numeroPiece;

    @Column(name = "code_barre")
    private String codeBarre;

    @Column(name = "entreprise")
    private String entreprise;

    @Column(name = "ville")
    private String ville;

    @Column(name = "quartier")
    private String quartier;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "lieu_naissance")
    private String lieuNaissance;

    @Column(name = "fonction_patient")
    private String fonctionPatient;

    @Column(name = "situation_sociale")
    private String situationSociale;

    @Column(name = "solde")
    private Double solde;

    @Column(name = "carte_patient")
    private String cartePatient;

    @Column(name = "bloque")
    private Boolean bloque;

    @Column(name = "date_validite")
    private LocalDate dateValidite;

    @Column(name = "motif_blocage")
    private String motifBlocage;

    @Column(name = "prenom_pere_patient")
    private String prenomPerePatient;

    @Column(name = "nom_mere_patient")
    private String nomMerePatient;

    @Column(name = "prenom_mere_patient")
    private String prenomMerePatient;

    @Column(name = "motif_admission")
    private String motifAdmission;

    @Column(name = "personne_a_contacter")
    private String personneAContacter;

    @Column(name = "adresse_pers_a_contacter")
    private String adressePersAContacter;

    @Column(name = "tel_pers_a_contacter")
    private String telPersAContacter;

    @Column(name = "lien_parente_pers_a_contacter")
    private String lienParentePersAContacter;

    @Column(name = "nom_accompagnant")
    private String nomAccompagnant;

    @Column(name = "prenom_accompagnant")
    private String prenomAccompagnant;

    @Column(name = "tel_accompagnant")
    private String telAccompagnant;

    @Column(name = "habitude_de_vie")
    private String habitudeDeVie;

    @Column(name = "physio_pathologique")
    private String physioPathologique;

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

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DosMedical> dosMedicals = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Hospitalisation> hospitalisations = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Compte> comptes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("patients")
    private GroupeSan groupeSanguin;

    @ManyToOne
    @JsonIgnoreProperties("patients")
    private TypePatient typePatient;

    @ManyToOne
    @JsonIgnoreProperties("patients")
    private Departement departement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodePatient() {
        return codePatient;
    }

    public Patient codePatient(String codePatient) {
        this.codePatient = codePatient;
        return this;
    }

    public void setCodePatient(String codePatient) {
        this.codePatient = codePatient;
    }

    public String getNomPatient() {
        return nomPatient;
    }

    public Patient nomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
        return this;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
    }

    public String getPrenom() {
        return prenom;
    }

    public Patient prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Patient adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public Patient email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Patient dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getGenre() {
        return genre;
    }

    public Patient genre(String genre) {
        this.genre = genre;
        return this;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Patient photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Patient photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getPassword() {
        return password;
    }

    public Patient password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public Patient telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNumeroPiece() {
        return numeroPiece;
    }

    public Patient numeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
        return this;
    }

    public void setNumeroPiece(String numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public Patient codeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
        return this;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public Patient entreprise(String entreprise) {
        this.entreprise = entreprise;
        return this;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getVille() {
        return ville;
    }

    public Patient ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getQuartier() {
        return quartier;
    }

    public Patient quartier(String quartier) {
        this.quartier = quartier;
        return this;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getLongitude() {
        return longitude;
    }

    public Patient longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public Patient latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public Patient lieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
        return this;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getFonctionPatient() {
        return fonctionPatient;
    }

    public Patient fonctionPatient(String fonctionPatient) {
        this.fonctionPatient = fonctionPatient;
        return this;
    }

    public void setFonctionPatient(String fonctionPatient) {
        this.fonctionPatient = fonctionPatient;
    }

    public String getSituationSociale() {
        return situationSociale;
    }

    public Patient situationSociale(String situationSociale) {
        this.situationSociale = situationSociale;
        return this;
    }

    public void setSituationSociale(String situationSociale) {
        this.situationSociale = situationSociale;
    }

    public Double getSolde() {
        return solde;
    }

    public Patient solde(Double solde) {
        this.solde = solde;
        return this;
    }

    public void setSolde(Double solde) {
        this.solde = solde;
    }

    public String getCartePatient() {
        return cartePatient;
    }

    public Patient cartePatient(String cartePatient) {
        this.cartePatient = cartePatient;
        return this;
    }

    public void setCartePatient(String cartePatient) {
        this.cartePatient = cartePatient;
    }

    public Boolean isBloque() {
        return bloque;
    }

    public Patient bloque(Boolean bloque) {
        this.bloque = bloque;
        return this;
    }

    public void setBloque(Boolean bloque) {
        this.bloque = bloque;
    }

    public LocalDate getDateValidite() {
        return dateValidite;
    }

    public Patient dateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
        return this;
    }

    public void setDateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
    }

    public String getMotifBlocage() {
        return motifBlocage;
    }

    public Patient motifBlocage(String motifBlocage) {
        this.motifBlocage = motifBlocage;
        return this;
    }

    public void setMotifBlocage(String motifBlocage) {
        this.motifBlocage = motifBlocage;
    }

    public String getPrenomPerePatient() {
        return prenomPerePatient;
    }

    public Patient prenomPerePatient(String prenomPerePatient) {
        this.prenomPerePatient = prenomPerePatient;
        return this;
    }

    public void setPrenomPerePatient(String prenomPerePatient) {
        this.prenomPerePatient = prenomPerePatient;
    }

    public String getNomMerePatient() {
        return nomMerePatient;
    }

    public Patient nomMerePatient(String nomMerePatient) {
        this.nomMerePatient = nomMerePatient;
        return this;
    }

    public void setNomMerePatient(String nomMerePatient) {
        this.nomMerePatient = nomMerePatient;
    }

    public String getPrenomMerePatient() {
        return prenomMerePatient;
    }

    public Patient prenomMerePatient(String prenomMerePatient) {
        this.prenomMerePatient = prenomMerePatient;
        return this;
    }

    public void setPrenomMerePatient(String prenomMerePatient) {
        this.prenomMerePatient = prenomMerePatient;
    }

    public String getMotifAdmission() {
        return motifAdmission;
    }

    public Patient motifAdmission(String motifAdmission) {
        this.motifAdmission = motifAdmission;
        return this;
    }

    public void setMotifAdmission(String motifAdmission) {
        this.motifAdmission = motifAdmission;
    }

    public String getPersonneAContacter() {
        return personneAContacter;
    }

    public Patient personneAContacter(String personneAContacter) {
        this.personneAContacter = personneAContacter;
        return this;
    }

    public void setPersonneAContacter(String personneAContacter) {
        this.personneAContacter = personneAContacter;
    }

    public String getAdressePersAContacter() {
        return adressePersAContacter;
    }

    public Patient adressePersAContacter(String adressePersAContacter) {
        this.adressePersAContacter = adressePersAContacter;
        return this;
    }

    public void setAdressePersAContacter(String adressePersAContacter) {
        this.adressePersAContacter = adressePersAContacter;
    }

    public String getTelPersAContacter() {
        return telPersAContacter;
    }

    public Patient telPersAContacter(String telPersAContacter) {
        this.telPersAContacter = telPersAContacter;
        return this;
    }

    public void setTelPersAContacter(String telPersAContacter) {
        this.telPersAContacter = telPersAContacter;
    }

    public String getLienParentePersAContacter() {
        return lienParentePersAContacter;
    }

    public Patient lienParentePersAContacter(String lienParentePersAContacter) {
        this.lienParentePersAContacter = lienParentePersAContacter;
        return this;
    }

    public void setLienParentePersAContacter(String lienParentePersAContacter) {
        this.lienParentePersAContacter = lienParentePersAContacter;
    }

    public String getNomAccompagnant() {
        return nomAccompagnant;
    }

    public Patient nomAccompagnant(String nomAccompagnant) {
        this.nomAccompagnant = nomAccompagnant;
        return this;
    }

    public void setNomAccompagnant(String nomAccompagnant) {
        this.nomAccompagnant = nomAccompagnant;
    }

    public String getPrenomAccompagnant() {
        return prenomAccompagnant;
    }

    public Patient prenomAccompagnant(String prenomAccompagnant) {
        this.prenomAccompagnant = prenomAccompagnant;
        return this;
    }

    public void setPrenomAccompagnant(String prenomAccompagnant) {
        this.prenomAccompagnant = prenomAccompagnant;
    }

    public String getTelAccompagnant() {
        return telAccompagnant;
    }

    public Patient telAccompagnant(String telAccompagnant) {
        this.telAccompagnant = telAccompagnant;
        return this;
    }

    public void setTelAccompagnant(String telAccompagnant) {
        this.telAccompagnant = telAccompagnant;
    }

    public String getHabitudeDeVie() {
        return habitudeDeVie;
    }

    public Patient habitudeDeVie(String habitudeDeVie) {
        this.habitudeDeVie = habitudeDeVie;
        return this;
    }

    public void setHabitudeDeVie(String habitudeDeVie) {
        this.habitudeDeVie = habitudeDeVie;
    }

    public String getPhysioPathologique() {
        return physioPathologique;
    }

    public Patient physioPathologique(String physioPathologique) {
        this.physioPathologique = physioPathologique;
        return this;
    }

    public void setPhysioPathologique(String physioPathologique) {
        this.physioPathologique = physioPathologique;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Patient dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Patient dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Patient userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Patient userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Patient userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<DosMedical> getDosMedicals() {
        return dosMedicals;
    }

    public Patient dosMedicals(Set<DosMedical> dosMedicals) {
        this.dosMedicals = dosMedicals;
        return this;
    }

    public Patient addDosMedical(DosMedical dosMedical) {
        this.dosMedicals.add(dosMedical);
        dosMedical.setPatient(this);
        return this;
    }

    public Patient removeDosMedical(DosMedical dosMedical) {
        this.dosMedicals.remove(dosMedical);
        dosMedical.setPatient(null);
        return this;
    }

    public void setDosMedicals(Set<DosMedical> dosMedicals) {
        this.dosMedicals = dosMedicals;
    }

    public Set<Hospitalisation> getHospitalisations() {
        return hospitalisations;
    }

    public Patient hospitalisations(Set<Hospitalisation> hospitalisations) {
        this.hospitalisations = hospitalisations;
        return this;
    }

    public Patient addHospitalisation(Hospitalisation hospitalisation) {
        this.hospitalisations.add(hospitalisation);
        hospitalisation.setPatient(this);
        return this;
    }

    public Patient removeHospitalisation(Hospitalisation hospitalisation) {
        this.hospitalisations.remove(hospitalisation);
        hospitalisation.setPatient(null);
        return this;
    }

    public void setHospitalisations(Set<Hospitalisation> hospitalisations) {
        this.hospitalisations = hospitalisations;
    }

    public Set<Compte> getComptes() {
        return comptes;
    }

    public Patient comptes(Set<Compte> comptes) {
        this.comptes = comptes;
        return this;
    }

    public Patient addCompte(Compte compte) {
        this.comptes.add(compte);
        compte.setPatient(this);
        return this;
    }

    public Patient removeCompte(Compte compte) {
        this.comptes.remove(compte);
        compte.setPatient(null);
        return this;
    }

    public void setComptes(Set<Compte> comptes) {
        this.comptes = comptes;
    }

    public GroupeSan getGroupeSanguin() {
        return groupeSanguin;
    }

    public Patient groupeSanguin(GroupeSan groupeSan) {
        this.groupeSanguin = groupeSan;
        return this;
    }

    public void setGroupeSanguin(GroupeSan groupeSan) {
        this.groupeSanguin = groupeSan;
    }

    public TypePatient getTypePatient() {
        return typePatient;
    }

    public Patient typePatient(TypePatient typePatient) {
        this.typePatient = typePatient;
        return this;
    }

    public void setTypePatient(TypePatient typePatient) {
        this.typePatient = typePatient;
    }

    public Departement getDepartement() {
        return departement;
    }

    public Patient departement(Departement departement) {
        this.departement = departement;
        return this;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return id != null && id.equals(((Patient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", codePatient='" + getCodePatient() + "'" +
            ", nomPatient='" + getNomPatient() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", email='" + getEmail() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", genre='" + getGenre() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", password='" + getPassword() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", numeroPiece='" + getNumeroPiece() + "'" +
            ", codeBarre='" + getCodeBarre() + "'" +
            ", entreprise='" + getEntreprise() + "'" +
            ", ville='" + getVille() + "'" +
            ", quartier='" + getQuartier() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", lieuNaissance='" + getLieuNaissance() + "'" +
            ", fonctionPatient='" + getFonctionPatient() + "'" +
            ", situationSociale='" + getSituationSociale() + "'" +
            ", solde=" + getSolde() +
            ", cartePatient='" + getCartePatient() + "'" +
            ", bloque='" + isBloque() + "'" +
            ", dateValidite='" + getDateValidite() + "'" +
            ", motifBlocage='" + getMotifBlocage() + "'" +
            ", prenomPerePatient='" + getPrenomPerePatient() + "'" +
            ", nomMerePatient='" + getNomMerePatient() + "'" +
            ", prenomMerePatient='" + getPrenomMerePatient() + "'" +
            ", motifAdmission='" + getMotifAdmission() + "'" +
            ", personneAContacter='" + getPersonneAContacter() + "'" +
            ", adressePersAContacter='" + getAdressePersAContacter() + "'" +
            ", telPersAContacter='" + getTelPersAContacter() + "'" +
            ", lienParentePersAContacter='" + getLienParentePersAContacter() + "'" +
            ", nomAccompagnant='" + getNomAccompagnant() + "'" +
            ", prenomAccompagnant='" + getPrenomAccompagnant() + "'" +
            ", telAccompagnant='" + getTelAccompagnant() + "'" +
            ", habitudeDeVie='" + getHabitudeDeVie() + "'" +
            ", physioPathologique='" + getPhysioPathologique() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
