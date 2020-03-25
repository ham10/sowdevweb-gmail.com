package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Questionnaire;
import com.projet.hpd.repository.QuestionnaireRepository;
import com.projet.hpd.service.QuestionnaireService;
import com.projet.hpd.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.projet.hpd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link QuestionnaireResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class QuestionnaireResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_DELETED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DELETED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_UPDATE = 1L;
    private static final Long UPDATED_USER_UPDATE = 2L;

    private static final Long DEFAULT_USER_DELETE = 1L;
    private static final Long UPDATED_USER_DELETE = 2L;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restQuestionnaireMockMvc;

    private Questionnaire questionnaire;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionnaireResource questionnaireResource = new QuestionnaireResource(questionnaireService);
        this.restQuestionnaireMockMvc = MockMvcBuilders.standaloneSetup(questionnaireResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questionnaire createEntity(EntityManager em) {
        Questionnaire questionnaire = new Questionnaire()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdate(DEFAULT_USER_UPDATE)
            .userDelete(DEFAULT_USER_DELETE);
        return questionnaire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questionnaire createUpdatedEntity(EntityManager em) {
        Questionnaire questionnaire = new Questionnaire()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .dateDeleted(UPDATED_DATE_DELETED)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdate(UPDATED_USER_UPDATE)
            .userDelete(UPDATED_USER_DELETE);
        return questionnaire;
    }

    @BeforeEach
    public void initTest() {
        questionnaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionnaire() throws Exception {
        int databaseSizeBeforeCreate = questionnaireRepository.findAll().size();

        // Create the Questionnaire
        restQuestionnaireMockMvc.perform(post("/api/questionnaires")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionnaire)))
            .andExpect(status().isCreated());

        // Validate the Questionnaire in the database
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeCreate + 1);
        Questionnaire testQuestionnaire = questionnaireList.get(questionnaireList.size() - 1);
        assertThat(testQuestionnaire.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testQuestionnaire.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testQuestionnaire.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testQuestionnaire.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testQuestionnaire.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testQuestionnaire.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testQuestionnaire.getUserUpdate()).isEqualTo(DEFAULT_USER_UPDATE);
        assertThat(testQuestionnaire.getUserDelete()).isEqualTo(DEFAULT_USER_DELETE);
    }

    @Test
    @Transactional
    public void createQuestionnaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionnaireRepository.findAll().size();

        // Create the Questionnaire with an existing ID
        questionnaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionnaireMockMvc.perform(post("/api/questionnaires")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionnaire)))
            .andExpect(status().isBadRequest());

        // Validate the Questionnaire in the database
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQuestionnaires() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);

        // Get all the questionnaireList
        restQuestionnaireMockMvc.perform(get("/api/questionnaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionnaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdate").value(hasItem(DEFAULT_USER_UPDATE.intValue())))
            .andExpect(jsonPath("$.[*].userDelete").value(hasItem(DEFAULT_USER_DELETE.intValue())));
    }
    
    @Test
    @Transactional
    public void getQuestionnaire() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);

        // Get the questionnaire
        restQuestionnaireMockMvc.perform(get("/api/questionnaires/{id}", questionnaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionnaire.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdate").value(DEFAULT_USER_UPDATE.intValue()))
            .andExpect(jsonPath("$.userDelete").value(DEFAULT_USER_DELETE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionnaire() throws Exception {
        // Get the questionnaire
        restQuestionnaireMockMvc.perform(get("/api/questionnaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionnaire() throws Exception {
        // Initialize the database
        questionnaireService.save(questionnaire);

        int databaseSizeBeforeUpdate = questionnaireRepository.findAll().size();

        // Update the questionnaire
        Questionnaire updatedQuestionnaire = questionnaireRepository.findById(questionnaire.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionnaire are not directly saved in db
        em.detach(updatedQuestionnaire);
        updatedQuestionnaire
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .dateDeleted(UPDATED_DATE_DELETED)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdate(UPDATED_USER_UPDATE)
            .userDelete(UPDATED_USER_DELETE);

        restQuestionnaireMockMvc.perform(put("/api/questionnaires")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionnaire)))
            .andExpect(status().isOk());

        // Validate the Questionnaire in the database
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeUpdate);
        Questionnaire testQuestionnaire = questionnaireList.get(questionnaireList.size() - 1);
        assertThat(testQuestionnaire.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testQuestionnaire.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testQuestionnaire.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testQuestionnaire.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testQuestionnaire.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testQuestionnaire.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testQuestionnaire.getUserUpdate()).isEqualTo(UPDATED_USER_UPDATE);
        assertThat(testQuestionnaire.getUserDelete()).isEqualTo(UPDATED_USER_DELETE);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionnaire() throws Exception {
        int databaseSizeBeforeUpdate = questionnaireRepository.findAll().size();

        // Create the Questionnaire

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionnaireMockMvc.perform(put("/api/questionnaires")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionnaire)))
            .andExpect(status().isBadRequest());

        // Validate the Questionnaire in the database
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionnaire() throws Exception {
        // Initialize the database
        questionnaireService.save(questionnaire);

        int databaseSizeBeforeDelete = questionnaireRepository.findAll().size();

        // Delete the questionnaire
        restQuestionnaireMockMvc.perform(delete("/api/questionnaires/{id}", questionnaire.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
