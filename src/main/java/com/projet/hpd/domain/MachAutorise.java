package com.projet.hpd.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A MachAutorise.
 */
@Entity
@Table(name = "mach_autorise")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MachAutorise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_mach_autorise")
    private String numeroMachAutorise;

    @Column(name = "adresse_mac_mach_autorise")
    private String adresseMacMachAutorise;

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

    public String getNumeroMachAutorise() {
        return numeroMachAutorise;
    }

    public MachAutorise numeroMachAutorise(String numeroMachAutorise) {
        this.numeroMachAutorise = numeroMachAutorise;
        return this;
    }

    public void setNumeroMachAutorise(String numeroMachAutorise) {
        this.numeroMachAutorise = numeroMachAutorise;
    }

    public String getAdresseMacMachAutorise() {
        return adresseMacMachAutorise;
    }

    public MachAutorise adresseMacMachAutorise(String adresseMacMachAutorise) {
        this.adresseMacMachAutorise = adresseMacMachAutorise;
        return this;
    }

    public void setAdresseMacMachAutorise(String adresseMacMachAutorise) {
        this.adresseMacMachAutorise = adresseMacMachAutorise;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public MachAutorise dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public MachAutorise dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public MachAutorise userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public MachAutorise userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public MachAutorise userDeleted(Long userDeleted) {
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
        if (!(o instanceof MachAutorise)) {
            return false;
        }
        return id != null && id.equals(((MachAutorise) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MachAutorise{" +
            "id=" + getId() +
            ", numeroMachAutorise='" + getNumeroMachAutorise() + "'" +
            ", adresseMacMachAutorise='" + getAdresseMacMachAutorise() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
