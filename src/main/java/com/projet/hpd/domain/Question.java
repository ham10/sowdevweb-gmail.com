package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Question implements Serializable {

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
    private String description;

    @Column(name = "obligatoire")
    private Boolean obligatoire;

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
    @JsonIgnoreProperties("questions")
    private Questionnaire questionnaire;

    @ManyToOne
    @JsonIgnoreProperties("questions")
    private TypeQuestion typeQuestion;

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

    public Question code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public Question libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public Question description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isObligatoire() {
        return obligatoire;
    }

    public Question obligatoire(Boolean obligatoire) {
        this.obligatoire = obligatoire;
        return this;
    }

    public void setObligatoire(Boolean obligatoire) {
        this.obligatoire = obligatoire;
    }

    public Instant getDateDeleted() {
        return dateDeleted;
    }

    public Question dateDeleted(Instant dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(Instant dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Question dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public Question dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Question userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdate() {
        return userUpdate;
    }

    public Question userUpdate(Long userUpdate) {
        this.userUpdate = userUpdate;
        return this;
    }

    public void setUserUpdate(Long userUpdate) {
        this.userUpdate = userUpdate;
    }

    public Long getUserDelete() {
        return userDelete;
    }

    public Question userDelete(Long userDelete) {
        this.userDelete = userDelete;
        return this;
    }

    public void setUserDelete(Long userDelete) {
        this.userDelete = userDelete;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public Question questionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
        return this;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public TypeQuestion getTypeQuestion() {
        return typeQuestion;
    }

    public Question typeQuestion(TypeQuestion typeQuestion) {
        this.typeQuestion = typeQuestion;
        return this;
    }

    public void setTypeQuestion(TypeQuestion typeQuestion) {
        this.typeQuestion = typeQuestion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        return id != null && id.equals(((Question) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", obligatoire='" + isObligatoire() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdate=" + getUserUpdate() +
            ", userDelete=" + getUserDelete() +
            "}";
    }
}
