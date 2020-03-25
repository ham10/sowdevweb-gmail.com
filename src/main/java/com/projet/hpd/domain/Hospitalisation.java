package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Hospitalisation.
 */
@Entity
@Table(name = "hospitalisation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Hospitalisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_entre")
    private LocalDate dateEntre;

    @Column(name = "date_sortie")
    private LocalDate dateSortie;

    @Column(name = "observation")
    private String observation;

    @Column(name = "mode_sortie")
    private String modeSortie;

    @Column(name = "objet_patient")
    private String objetPatient;

    @Column(name = "date_accouchement")
    private Instant dateAccouchement;

    @Column(name = "date_operation")
    private LocalDate dateOperation;

    @Column(name = "date_reveil")
    private LocalDate dateReveil;

    @Column(name = "poids_bebe")
    private Double poidsBebe;

    @Column(name = "taille_bebe")
    private Double tailleBebe;

    @Column(name = "poids_placenta")
    private Double poidsPlacenta;

    @Column(name = "genre_bebe")
    private String genreBebe;

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

    @OneToOne
    @JoinColumn(unique = true)
    private Lit lit;

    @ManyToOne
    @JsonIgnoreProperties("hospitalisations")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateEntre() {
        return dateEntre;
    }

    public Hospitalisation dateEntre(LocalDate dateEntre) {
        this.dateEntre = dateEntre;
        return this;
    }

    public void setDateEntre(LocalDate dateEntre) {
        this.dateEntre = dateEntre;
    }

    public LocalDate getDateSortie() {
        return dateSortie;
    }

    public Hospitalisation dateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
        return this;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getObservation() {
        return observation;
    }

    public Hospitalisation observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getModeSortie() {
        return modeSortie;
    }

    public Hospitalisation modeSortie(String modeSortie) {
        this.modeSortie = modeSortie;
        return this;
    }

    public void setModeSortie(String modeSortie) {
        this.modeSortie = modeSortie;
    }

    public String getObjetPatient() {
        return objetPatient;
    }

    public Hospitalisation objetPatient(String objetPatient) {
        this.objetPatient = objetPatient;
        return this;
    }

    public void setObjetPatient(String objetPatient) {
        this.objetPatient = objetPatient;
    }

    public Instant getDateAccouchement() {
        return dateAccouchement;
    }

    public Hospitalisation dateAccouchement(Instant dateAccouchement) {
        this.dateAccouchement = dateAccouchement;
        return this;
    }

    public void setDateAccouchement(Instant dateAccouchement) {
        this.dateAccouchement = dateAccouchement;
    }

    public LocalDate getDateOperation() {
        return dateOperation;
    }

    public Hospitalisation dateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
        return this;
    }

    public void setDateOperation(LocalDate dateOperation) {
        this.dateOperation = dateOperation;
    }

    public LocalDate getDateReveil() {
        return dateReveil;
    }

    public Hospitalisation dateReveil(LocalDate dateReveil) {
        this.dateReveil = dateReveil;
        return this;
    }

    public void setDateReveil(LocalDate dateReveil) {
        this.dateReveil = dateReveil;
    }

    public Double getPoidsBebe() {
        return poidsBebe;
    }

    public Hospitalisation poidsBebe(Double poidsBebe) {
        this.poidsBebe = poidsBebe;
        return this;
    }

    public void setPoidsBebe(Double poidsBebe) {
        this.poidsBebe = poidsBebe;
    }

    public Double getTailleBebe() {
        return tailleBebe;
    }

    public Hospitalisation tailleBebe(Double tailleBebe) {
        this.tailleBebe = tailleBebe;
        return this;
    }

    public void setTailleBebe(Double tailleBebe) {
        this.tailleBebe = tailleBebe;
    }

    public Double getPoidsPlacenta() {
        return poidsPlacenta;
    }

    public Hospitalisation poidsPlacenta(Double poidsPlacenta) {
        this.poidsPlacenta = poidsPlacenta;
        return this;
    }

    public void setPoidsPlacenta(Double poidsPlacenta) {
        this.poidsPlacenta = poidsPlacenta;
    }

    public String getGenreBebe() {
        return genreBebe;
    }

    public Hospitalisation genreBebe(String genreBebe) {
        this.genreBebe = genreBebe;
        return this;
    }

    public void setGenreBebe(String genreBebe) {
        this.genreBebe = genreBebe;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Hospitalisation dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Hospitalisation dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Hospitalisation userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Hospitalisation userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Hospitalisation userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Lit getLit() {
        return lit;
    }

    public Hospitalisation lit(Lit lit) {
        this.lit = lit;
        return this;
    }

    public void setLit(Lit lit) {
        this.lit = lit;
    }

    public Patient getPatient() {
        return patient;
    }

    public Hospitalisation patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hospitalisation)) {
            return false;
        }
        return id != null && id.equals(((Hospitalisation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Hospitalisation{" +
            "id=" + getId() +
            ", dateEntre='" + getDateEntre() + "'" +
            ", dateSortie='" + getDateSortie() + "'" +
            ", observation='" + getObservation() + "'" +
            ", modeSortie='" + getModeSortie() + "'" +
            ", objetPatient='" + getObjetPatient() + "'" +
            ", dateAccouchement='" + getDateAccouchement() + "'" +
            ", dateOperation='" + getDateOperation() + "'" +
            ", dateReveil='" + getDateReveil() + "'" +
            ", poidsBebe=" + getPoidsBebe() +
            ", tailleBebe=" + getTailleBebe() +
            ", poidsPlacenta=" + getPoidsPlacenta() +
            ", genreBebe='" + getGenreBebe() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
