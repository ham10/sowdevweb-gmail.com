package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Activite.
 */
@Entity
@Table(name = "activite")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date_activite")
    private LocalDate dateActivite;

    @Column(name = "heure_connexion_activite")
    private String heureConnexionActivite;

    @Column(name = "heure_de_connexion_activite")
    private String heureDeConnexionActivite;

    @Column(name = "adresse_ip_activite")
    private String adresseIpActivite;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "adresse_mac")
    private String adresseMac;

    @OneToMany(mappedBy = "activite")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Evenement> evenements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateActivite() {
        return dateActivite;
    }

    public Activite dateActivite(LocalDate dateActivite) {
        this.dateActivite = dateActivite;
        return this;
    }

    public void setDateActivite(LocalDate dateActivite) {
        this.dateActivite = dateActivite;
    }

    public String getHeureConnexionActivite() {
        return heureConnexionActivite;
    }

    public Activite heureConnexionActivite(String heureConnexionActivite) {
        this.heureConnexionActivite = heureConnexionActivite;
        return this;
    }

    public void setHeureConnexionActivite(String heureConnexionActivite) {
        this.heureConnexionActivite = heureConnexionActivite;
    }

    public String getHeureDeConnexionActivite() {
        return heureDeConnexionActivite;
    }

    public Activite heureDeConnexionActivite(String heureDeConnexionActivite) {
        this.heureDeConnexionActivite = heureDeConnexionActivite;
        return this;
    }

    public void setHeureDeConnexionActivite(String heureDeConnexionActivite) {
        this.heureDeConnexionActivite = heureDeConnexionActivite;
    }

    public String getAdresseIpActivite() {
        return adresseIpActivite;
    }

    public Activite adresseIpActivite(String adresseIpActivite) {
        this.adresseIpActivite = adresseIpActivite;
        return this;
    }

    public void setAdresseIpActivite(String adresseIpActivite) {
        this.adresseIpActivite = adresseIpActivite;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Activite dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Activite userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public String getAdresseMac() {
        return adresseMac;
    }

    public Activite adresseMac(String adresseMac) {
        this.adresseMac = adresseMac;
        return this;
    }

    public void setAdresseMac(String adresseMac) {
        this.adresseMac = adresseMac;
    }

    public Set<Evenement> getEvenements() {
        return evenements;
    }

    public Activite evenements(Set<Evenement> evenements) {
        this.evenements = evenements;
        return this;
    }

    public Activite addEvenement(Evenement evenement) {
        this.evenements.add(evenement);
        evenement.setActivite(this);
        return this;
    }

    public Activite removeEvenement(Evenement evenement) {
        this.evenements.remove(evenement);
        evenement.setActivite(null);
        return this;
    }

    public void setEvenements(Set<Evenement> evenements) {
        this.evenements = evenements;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activite)) {
            return false;
        }
        return id != null && id.equals(((Activite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Activite{" +
            "id=" + getId() +
            ", dateActivite='" + getDateActivite() + "'" +
            ", heureConnexionActivite='" + getHeureConnexionActivite() + "'" +
            ", heureDeConnexionActivite='" + getHeureDeConnexionActivite() + "'" +
            ", adresseIpActivite='" + getAdresseIpActivite() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", adresseMac='" + getAdresseMac() + "'" +
            "}";
    }
}
