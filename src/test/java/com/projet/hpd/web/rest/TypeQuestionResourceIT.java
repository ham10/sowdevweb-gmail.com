package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeQuestion;
import com.projet.hpd.repository.TypeQuestionRepository;
import com.projet.hpd.service.TypeQuestionService;
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
 * Integration tests for the {@link TypeQuestionResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeQuestionResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BELLE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BELLE_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MULTIPLICITE_CHOIX = 1;
    private static final Integer UPDATED_MULTIPLICITE_CHOIX = 2;

    private static final Instant DEFAULT_DATE_DELETED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DELETED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_USER_UPDATE = 1L;
    private static final Long UPDATED_USER_UPDATE = 2L;

    private static final Long DEFAULT_USER_DELETE = 1L;
    private static final Long UPDATED_USER_DELETE = 2L;

    @Autowired
    private TypeQuestionRepository typeQuestionRepository;

    @Autowired
    private TypeQuestionService typeQuestionService;

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

    private MockMvc restTypeQuestionMockMvc;

    private TypeQuestion typeQuestion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeQuestionResource typeQuestionResource = new TypeQuestionResource(typeQuestionService);
        this.restTypeQuestionMockMvc = MockMvcBuilders.standaloneSetup(typeQuestionResource)
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
    public static TypeQuestion createEntity(EntityManager em) {
        TypeQuestion typeQuestion = new TypeQuestion()
            .code(DEFAULT_CODE)
            .belleType(DEFAULT_BELLE_TYPE)
            .multipliciteChoix(DEFAULT_MULTIPLICITE_CHOIX)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userUpdate(DEFAULT_USER_UPDATE)
            .userDelete(DEFAULT_USER_DELETE);
        return typeQuestion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeQuestion createUpdatedEntity(EntityManager em) {
        TypeQuestion typeQuestion = new TypeQuestion()
            .code(UPDATED_CODE)
            .belleType(UPDATED_BELLE_TYPE)
            .multipliciteChoix(UPDATED_MULTIPLICITE_CHOIX)
            .dateDeleted(UPDATED_DATE_DELETED)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userUpdate(UPDATED_USER_UPDATE)
            .userDelete(UPDATED_USER_DELETE);
        return typeQuestion;
    }

    @BeforeEach
    public void initTest() {
        typeQuestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeQuestion() throws Exception {
        int databaseSizeBeforeCreate = typeQuestionRepository.findAll().size();

        // Create the TypeQuestion
        restTypeQuestionMockMvc.perform(post("/api/type-questions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeQuestion)))
            .andExpect(status().isCreated());

        // Validate the TypeQuestion in the database
        List<TypeQuestion> typeQuestionList = typeQuestionRepository.findAll();
        assertThat(typeQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        TypeQuestion testTypeQuestion = typeQuestionList.get(typeQuestionList.size() - 1);
        assertThat(testTypeQuestion.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeQuestion.getBelleType()).isEqualTo(DEFAULT_BELLE_TYPE);
        assertThat(testTypeQuestion.getMultipliciteChoix()).isEqualTo(DEFAULT_MULTIPLICITE_CHOIX);
        assertThat(testTypeQuestion.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testTypeQuestion.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeQuestion.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeQuestion.getUserUpdate()).isEqualTo(DEFAULT_USER_UPDATE);
        assertThat(testTypeQuestion.getUserDelete()).isEqualTo(DEFAULT_USER_DELETE);
    }

    @Test
    @Transactional
    public void createTypeQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeQuestionRepository.findAll().size();

        // Create the TypeQuestion with an existing ID
        typeQuestion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeQuestionMockMvc.perform(post("/api/type-questions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeQuestion)))
            .andExpect(status().isBadRequest());

        // Validate the TypeQuestion in the database
        List<TypeQuestion> typeQuestionList = typeQuestionRepository.findAll();
        assertThat(typeQuestionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeQuestions() throws Exception {
        // Initialize the database
        typeQuestionRepository.saveAndFlush(typeQuestion);

        // Get all the typeQuestionList
        restTypeQuestionMockMvc.perform(get("/api/type-questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].belleType").value(hasItem(DEFAULT_BELLE_TYPE)))
            .andExpect(jsonPath("$.[*].multipliciteChoix").value(hasItem(DEFAULT_MULTIPLICITE_CHOIX)))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userUpdate").value(hasItem(DEFAULT_USER_UPDATE.intValue())))
            .andExpect(jsonPath("$.[*].userDelete").value(hasItem(DEFAULT_USER_DELETE.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeQuestion() throws Exception {
        // Initialize the database
        typeQuestionRepository.saveAndFlush(typeQuestion);

        // Get the typeQuestion
        restTypeQuestionMockMvc.perform(get("/api/type-questions/{id}", typeQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeQuestion.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.belleType").value(DEFAULT_BELLE_TYPE))
            .andExpect(jsonPath("$.multipliciteChoix").value(DEFAULT_MULTIPLICITE_CHOIX))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userUpdate").value(DEFAULT_USER_UPDATE.intValue()))
            .andExpect(jsonPath("$.userDelete").value(DEFAULT_USER_DELETE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeQuestion() throws Exception {
        // Get the typeQuestion
        restTypeQuestionMockMvc.perform(get("/api/type-questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeQuestion() throws Exception {
        // Initialize the database
        typeQuestionService.save(typeQuestion);

        int databaseSizeBeforeUpdate = typeQuestionRepository.findAll().size();

        // Update the typeQuestion
        TypeQuestion updatedTypeQuestion = typeQuestionRepository.findById(typeQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedTypeQuestion are not directly saved in db
        em.detach(updatedTypeQuestion);
        updatedTypeQuestion
            .code(UPDATED_CODE)
            .belleType(UPDATED_BELLE_TYPE)
            .multipliciteChoix(UPDATED_MULTIPLICITE_CHOIX)
            .dateDeleted(UPDATED_DATE_DELETED)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userUpdate(UPDATED_USER_UPDATE)
            .userDelete(UPDATED_USER_DELETE);

        restTypeQuestionMockMvc.perform(put("/api/type-questions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeQuestion)))
            .andExpect(status().isOk());

        // Validate the TypeQuestion in the database
        List<TypeQuestion> typeQuestionList = typeQuestionRepository.findAll();
        assertThat(typeQuestionList).hasSize(databaseSizeBeforeUpdate);
        TypeQuestion testTypeQuestion = typeQuestionList.get(typeQuestionList.size() - 1);
        assertThat(testTypeQuestion.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeQuestion.getBelleType()).isEqualTo(UPDATED_BELLE_TYPE);
        assertThat(testTypeQuestion.getMultipliciteChoix()).isEqualTo(UPDATED_MULTIPLICITE_CHOIX);
        assertThat(testTypeQuestion.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testTypeQuestion.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeQuestion.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeQuestion.getUserUpdate()).isEqualTo(UPDATED_USER_UPDATE);
        assertThat(testTypeQuestion.getUserDelete()).isEqualTo(UPDATED_USER_DELETE);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeQuestion() throws Exception {
        int databaseSizeBeforeUpdate = typeQuestionRepository.findAll().size();

        // Create the TypeQuestion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeQuestionMockMvc.perform(put("/api/type-questions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeQuestion)))
            .andExpect(status().isBadRequest());

        // Validate the TypeQuestion in the database
        List<TypeQuestion> typeQuestionList = typeQuestionRepository.findAll();
        assertThat(typeQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeQuestion() throws Exception {
        // Initialize the database
        typeQuestionService.save(typeQuestion);

        int databaseSizeBeforeDelete = typeQuestionRepository.findAll().size();

        // Delete the typeQuestion
        restTypeQuestionMockMvc.perform(delete("/api/type-questions/{id}", typeQuestion.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeQuestion> typeQuestionList = typeQuestionRepository.findAll();
        assertThat(typeQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
