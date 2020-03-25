package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Services.
 */
@Entity
@Table(name = "services")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Services implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "description")
    private LocalDate description;

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

    @OneToMany(mappedBy = "serv")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CatChambre> catChambres = new HashSet<>();

    @OneToMany(mappedBy = "serv")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DosMedical> dosMedicals = new HashSet<>();

    @OneToMany(mappedBy = "serv")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BonDeCommande> bonDeCommandes = new HashSet<>();

    @OneToMany(mappedBy = "serv")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Plat> plats = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("services")
    private TypeServices typeServices;

    @ManyToOne
    @JsonIgnoreProperties("services")
    private DeptServices deptServices;

    @ManyToOne
    @JsonIgnoreProperties("services")
    private Specialite specialite;

    @ManyToOne
    @JsonIgnoreProperties("services")
    private CompteGene compteGeneral;

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

    public Services code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Services libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public LocalDate getDescription() {
        return description;
    }

    public Services description(LocalDate description) {
        this.description = description;
        return this;
    }

    public void setDescription(LocalDate description) {
        this.description = description;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Services dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Services dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Services userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Services userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Services userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<CatChambre> getCatChambres() {
        return catChambres;
    }

    public Services catChambres(Set<CatChambre> catChambres) {
        this.catChambres = catChambres;
        return this;
    }

    public Services addCatChambre(CatChambre catChambre) {
        this.catChambres.add(catChambre);
        catChambre.setServ(this);
        return this;
    }

    public Services removeCatChambre(CatChambre catChambre) {
        this.catChambres.remove(catChambre);
        catChambre.setServ(null);
        return this;
    }

    public void setCatChambres(Set<CatChambre> catChambres) {
        this.catChambres = catChambres;
    }

    public Set<DosMedical> getDosMedicals() {
        return dosMedicals;
    }

    public Services dosMedicals(Set<DosMedical> dosMedicals) {
        this.dosMedicals = dosMedicals;
        return this;
    }

    public Services addDosMedical(DosMedical dosMedical) {
        this.dosMedicals.add(dosMedical);
        dosMedical.setServ(this);
        return this;
    }

    public Services removeDosMedical(DosMedical dosMedical) {
        this.dosMedicals.remove(dosMedical);
        dosMedical.setServ(null);
        return this;
    }

    public void setDosMedicals(Set<DosMedical> dosMedicals) {
        this.dosMedicals = dosMedicals;
    }

    public Set<BonDeCommande> getBonDeCommandes() {
        return bonDeCommandes;
    }

    public Services bonDeCommandes(Set<BonDeCommande> bonDeCommandes) {
        this.bonDeCommandes = bonDeCommandes;
        return this;
    }

    public Services addBonDeCommande(BonDeCommande bonDeCommande) {
        this.bonDeCommandes.add(bonDeCommande);
        bonDeCommande.setServ(this);
        return this;
    }

    public Services removeBonDeCommande(BonDeCommande bonDeCommande) {
        this.bonDeCommandes.remove(bonDeCommande);
        bonDeCommande.setServ(null);
        return this;
    }

    public void setBonDeCommandes(Set<BonDeCommande> bonDeCommandes) {
        this.bonDeCommandes = bonDeCommandes;
    }

    public Set<Plat> getPlats() {
        return plats;
    }

    public Services plats(Set<Plat> plats) {
        this.plats = plats;
        return this;
    }

    public Services addPlat(Plat plat) {
        this.plats.add(plat);
        plat.setServ(this);
        return this;
    }

    public Services removePlat(Plat plat) {
        this.plats.remove(plat);
        plat.setServ(null);
        return this;
    }

    public void setPlats(Set<Plat> plats) {
        this.plats = plats;
    }

    public TypeServices getTypeServices() {
        return typeServices;
    }

    public Services typeServices(TypeServices typeServices) {
        this.typeServices = typeServices;
        return this;
    }

    public void setTypeServices(TypeServices typeServices) {
        this.typeServices = typeServices;
    }

    public DeptServices getDeptServices() {
        return deptServices;
    }

    public Services deptServices(DeptServices deptServices) {
        this.deptServices = deptServices;
        return this;
    }

    public void setDeptServices(DeptServices deptServices) {
        this.deptServices = deptServices;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public Services specialite(Specialite specialite) {
        this.specialite = specialite;
        return this;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public CompteGene getCompteGeneral() {
        return compteGeneral;
    }

    public Services compteGeneral(CompteGene compteGene) {
        this.compteGeneral = compteGene;
        return this;
    }

    public void setCompteGeneral(CompteGene compteGene) {
        this.compteGeneral = compteGene;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Services)) {
            return false;
        }
        return id != null && id.equals(((Services) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Services{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
