package com.projet.hpd.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Reponse.
 */
@Entity
@Table(name = "reponse")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Reponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "reponse")
    private String reponse;

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
    @JsonIgnoreProperties("reponses")
    private Question question;

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

    public Reponse code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReponse() {
        return reponse;
    }

    public Reponse reponse(String reponse) {
        this.reponse = reponse;
        return this;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Instant getDateDeleted() {
        return dateDeleted;
    }

    public Reponse dateDeleted(Instant dateDeleted) {
        this.dateDeleted = dateDeleted;
        return this;
    }

    public void setDateDeleted(Instant dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Reponse dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public Reponse dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Reponse userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserUpdate() {
        return userUpdate;
    }

    public Reponse userUpdate(Long userUpdate) {
        this.userUpdate = userUpdate;
        return this;
    }

    public void setUserUpdate(Long userUpdate) {
        this.userUpdate = userUpdate;
    }

    public Long getUserDelete() {
        return userDelete;
    }

    public Reponse userDelete(Long userDelete) {
        this.userDelete = userDelete;
        return this;
    }

    public void setUserDelete(Long userDelete) {
        this.userDelete = userDelete;
    }

    public Question getQuestion() {
        return question;
    }

    public Reponse question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reponse)) {
            return false;
        }
        return id != null && id.equals(((Reponse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Reponse{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", reponse='" + getReponse() + "'" +
            ", dateDeleted='" + getDateDeleted() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userUpdate=" + getUserUpdate() +
            ", userDelete=" + getUserDelete() +
            "}";
    }
}
