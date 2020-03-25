package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Annotation.
 */
@Entity
@Table(name = "annotation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Annotation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "note")
    private Integer note;

    @Column(name = "observation")
    private String observation;

    @Column(name = "nb_questions")
    private Integer nbQuestions;

    @Column(name = "moyenne")
    private Float moyenne;

    @Column(name = "date_deleted")
    private Instant dateDeleted;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_update")
    private Long userUpdate;

    @Column(name = "user_delete")
    private Long userDelete;

    @ManyToOne
    @JsonIgnoreProperties("annotations")
    private Questionnaire questionnaire;

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

    public Annotation code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getNote() {
        return note;
    }

    public Annotation note(Integer note) {
        this.note = note;
        return this;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public String getObservation() {
        return observation;
    }

    public Annotation observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Integer getNbQuestions() {
        return nbQuestions;
    }

    public Annotation nbQuestions(Integer nbQuestions) {
        this.nbQuestions = nbQuestions;
        return this;
    }

    public void setNbQuestions(Integer nbQuestions) {
        this.nbQuestions = nbQuestions;
    }

    public Float getMoyenne() {
        return moyenne;
    }

    public Annotation moyenne(Float moyenne) {
        this.moyenne = moyenne;
        return this;
    }

    public void setMoyenne(Float moyenne) {
        this.moyenne = moyenne;
    }

    public Instant getDateDeleted() {
        return dateDeleted;
    }

    public Annotation dateDeleted(Instant dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(Instant dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Annotation dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public Annotation dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Annotation userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdate() {
        return userUpdate;
    }

    public Annotation userUpdate(Long userUpdate) {
        this.userUpdate = userUpdate;
        return this;
    }

    public void setUserUpdate(Long userUpdate) {
        this.userUpdate = userUpdate;
    }

    public Long getUserDelete() {
        return userDelete;
    }

    public Annotation userDelete(Long userDelete) {
        this.userDelete = userDelete;
        return this;
    }

    public void setUserDelete(Long userDelete) {
        this.userDelete = userDelete;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public Annotation questionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
        return this;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Annotation)) {
            return false;
        }
        return id != null && id.equals(((Annotation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Annotation{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", note=" + getNote() +
            ", observation='" + getObservation() + "'" +
            ", nbQuestions=" + getNbQuestions() +
            ", moyenne=" + getMoyenne() +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdate=" + getUserUpdate() +
            ", userDelete=" + getUserDelete() +
            "}";
    }
}
