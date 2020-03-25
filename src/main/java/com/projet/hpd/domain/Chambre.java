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
 * A Chambre.
 */
@Entity
@Table(name = "chambre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chambre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_chambre")
    private Integer numeroChambre;

    @Column(name = "post_tel_chambre")
    private Integer postTelChambre;

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

    @OneToMany(mappedBy = "chambre")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Boxe> boxes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("chambres")
    private CatChambre categorieChambre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroChambre() {
        return numeroChambre;
    }

    public Chambre numeroChambre(Integer numeroChambre) {
        this.numeroChambre = numeroChambre;
        return this;
    }

    public void setNumeroChambre(Integer numeroChambre) {
        this.numeroChambre = numeroChambre;
    }

    public Integer getPostTelChambre() {
        return postTelChambre;
    }

    public Chambre postTelChambre(Integer postTelChambre) {
        this.postTelChambre = postTelChambre;
        return this;
    }

    public void setPostTelChambre(Integer postTelChambre) {
        this.postTelChambre = postTelChambre;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public Chambre dateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public Chambre dateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Chambre userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Chambre userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Long getUserDeleted() {
        return userDeleted;
    }

    public Chambre userDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
        return this;
    }

    public void setUserDeleted(Long userDeleted) {
        this.userDeleted = userDeleted;
    }

    public Set<Boxe> getBoxes() {
        return boxes;
    }

    public Chambre boxes(Set<Boxe> boxes) {
        this.boxes = boxes;
        return this;
    }

    public Chambre addBoxe(Boxe boxe) {
        this.boxes.add(boxe);
        boxe.setChambre(this);
        return this;
    }

    public Chambre removeBoxe(Boxe boxe) {
        this.boxes.remove(boxe);
        boxe.setChambre(null);
        return this;
    }

    public void setBoxes(Set<Boxe> boxes) {
        this.boxes = boxes;
    }

    public CatChambre getCategorieChambre() {
        return categorieChambre;
    }

    public Chambre categorieChambre(CatChambre catChambre) {
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
        if (!(o instanceof Chambre)) {
            return false;
        }
        return id != null && id.equals(((Chambre) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Chambre{" +
            "id=" + getId() +
            ", numeroChambre=" + getNumeroChambre() +
            ", postTelChambre=" + getPostTelChambre() +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", userDeleted=" + getUserDeleted() +
            "}";
    }
}
