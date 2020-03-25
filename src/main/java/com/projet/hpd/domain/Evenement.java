package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Evenement.
 */
@Entity
@Table(name = "evenement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Evenement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "heure")
    private String heure;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "description")
    private String description;

    @Column(name = "date_created")
    private String dateCreated;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_updated")
    private Long userUpdated;

    @Column(name = "id_entity")
    private Integer idEntity;

    @Column(name = "entity_toucher")
    private String entityToucher;

    @ManyToOne
    @JsonIgnoreProperties("evenements")
    private Activite activite;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeure() {
        return heure;
    }

    public Evenement heure(String heure) {
        this.heure = heure;
        return this;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getEventName() {
        return eventName;
    }

    public Evenement eventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public Evenement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public Evenement dateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Evenement userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdated() {
        return userUpdated;
    }

    public Evenement userUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
        return this;
    }

    public void setUserUpdated(Long userUpdated) {
        this.userUpdated = userUpdated;
    }

    public Integer getIdEntity() {
        return idEntity;
    }

    public Evenement idEntity(Integer idEntity) {
        this.idEntity = idEntity;
        return this;
    }

    public void setIdEntity(Integer idEntity) {
        this.idEntity = idEntity;
    }

    public String getEntityToucher() {
        return entityToucher;
    }

    public Evenement entityToucher(String entityToucher) {
        this.entityToucher = entityToucher;
        return this;
    }

    public void setEntityToucher(String entityToucher) {
        this.entityToucher = entityToucher;
    }

    public Activite getActivite() {
        return activite;
    }

    public Evenement activite(Activite activite) {
        this.activite = activite;
        return this;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Evenement)) {
            return false;
        }
        return id != null && id.equals(((Evenement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Evenement{" +
            "id=" + getId() +
            ", heure='" + getHeure() + "'" +
            ", eventName='" + getEventName() + "'" +
            ", description='" + getDescription() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdated=" + getUserUpdated() +
            ", idEntity=" + getIdEntity() +
            ", entityToucher='" + getEntityToucher() + "'" +
            "}";
    }
}
