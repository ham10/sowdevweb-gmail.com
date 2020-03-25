package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Etablis.
 */
@Entity
@Table(name = "etablis")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Etablis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_etabl")
    private String codeEtabl;

    @Column(name = "raison_sociale_etabl")
    private String raisonSocialeEtabl;

    @Column(name = "adresse_etabl")
    private String adresseEtabl;

    @Column(name = "telephone_etabl")
    private Integer telephoneEtabl;

    @Column(name = "ninea_etabl")
    private String nineaEtabl;

    @Column(name = "rc_etabl")
    private String rcEtabl;

    @Column(name = "email_etabl")
    private String emailEtabl;

    @Column(name = "description")
    private String description;

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

    @ManyToOne
    @JsonIgnoreProperties("etablis")
    private Departement departement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeEtabl() {
        return codeEtabl;
    }

    public Etablis codeEtabl(String codeEtabl) {
        this.codeEtabl = codeEtabl;
        return this;
    }

    public void setCodeEtabl(String codeEtabl) {
        this.codeEtabl = codeEtabl;
    }

    public String getRaisonSocialeEtabl() {
        return raisonSocialeEtabl;
    }

    public Etablis raisonSocialeEtabl(String raisonSocialeEtabl) {
        this.raisonSocialeEtabl = raisonSocialeEtabl;
        return this;
    }

    public void setRaisonSocialeEtabl(String raisonSocialeEtabl) {
        this.raisonSocialeEtabl = raisonSocialeEtabl;
    }

    public String getAdresseEtabl() {
        return adresseEtabl;
    }

    public Etablis adresseEtabl(String adresseEtabl) {
        this.adresseEtabl = adresseEtabl;
        return this;
    }

    public void setAdresseEtabl(String adresseEtabl) {
        this.adresseEtabl = adresseEtabl;
    }

    public Integer getTelephoneEtabl() {
        return telephoneEtabl;
    }

    public Etablis telephoneEtabl(Integer telephoneEtabl) {
        this.telephoneEtabl = telephoneEtabl;
        return this;
    }

    public void setTelephoneEtabl(Integer telephoneEtabl) {
        this.telephoneEtabl = telephoneEtabl;
    }

    public String getNineaEtabl() {
        return nineaEtabl;
    }

    public Etablis nineaEtabl(String nineaEtabl) {
        this.nineaEtabl = nineaEtabl;
        return this;
    }

    public void setNineaEtabl(String nineaEtabl) {
        this.nineaEtabl = nineaEtabl;
    }

    public String getRcEtabl() {
        return rcEtabl;
    }

    public Etablis rcEtabl(String rcEtabl) {
        this.rcEtabl = rcEtabl;
        return this;
    }

    public void setRcEtabl(String rcEtabl) {
        this.rcEtabl = rcEtabl;
    }

    public String getEmailEtabl() {
        return emailEtabl;
    }

    public Etablis emailEtabl(String emailEtabl) {
        this.emailEtabl = emailEtabl;
        return this;
    }

    public void setEmailEtabl(String emailEtabl) {
        this.emailEtabl = emailEtabl;
    }

    public String getDescription() {
        return description;
    }

    public Etablis description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Etablis dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Etablis dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Etablis userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Etablis userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Etablis userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Departement getDepartement() {
        return departement;
    }

    public Etablis departement(Departement departement) {
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
        if (!(o instanceof Etablis)) {
            return false;
        }
        return id != null && id.equals(((Etablis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Etablis{" +
            "id=" + getId() +
            ", codeEtabl='" + getCodeEtabl() + "'" +
            ", raisonSocialeEtabl='" + getRaisonSocialeEtabl() + "'" +
            ", adresseEtabl='" + getAdresseEtabl() + "'" +
            ", telephoneEtabl=" + getTelephoneEtabl() +
            ", nineaEtabl='" + getNineaEtabl() + "'" +
            ", rcEtabl='" + getRcEtabl() + "'" +
            ", emailEtabl='" + getEmailEtabl() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
