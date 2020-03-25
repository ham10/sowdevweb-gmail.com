package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Annotation;
import com.projet.hpd.repository.AnnotationRepository;
import com.projet.hpd.service.AnnotationService;
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
 * Integration tests for the {@link AnnotationResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class AnnotationResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOTE = 1;
    private static final Integer UPDATED_NOTE = 2;

    private static final String DEFAULT_OBSERVATION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_NB_QUESTIONS = 1;
    private static final Integer UPDATED_NB_QUESTIONS = 2;

    private static final Float DEFAULT_MOYENNE = 1F;
    private static final Float UPDATED_MOYENNE = 2F;

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
    private AnnotationRepository annotationRepository;

    @Autowired
    private AnnotationService annotationService;

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

    private MockMvc restAnnotationMockMvc;

    private Annotation annotation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnnotationResource annotationResource = new AnnotationResource(annotationService);
        this.restAnnotationMockMvc = MockMvcBuilders.standaloneSetup(annotationResource)
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
    public static Annotation createEntity(EntityManager em) {
        Annotation annotation = new Annotation()
            .code(DEFAULT_CODE)
            .note(DEFAULT_NOTE)
            .observation(DEFAULT_OBSERVATION)
            .nbQuestions(DEFAULT_NB_QUESTIONS)
            .moyenne(DEFAULT_MOYENNE)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdate(DEFAULT_USER_UPDATE)
            .userDelete(DEFAULT_USER_DELETE);
        return annotation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Annotation createUpdatedEntity(EntityManager em) {
        Annotation annotation = new Annotation()
            .code(UPDATED_CODE)
            .note(UPDATED_NOTE)
            .observation(UPDATED_OBSERVATION)
            .nbQuestions(UPDATED_NB_QUESTIONS)
            .moyenne(UPDATED_MOYENNE)
            .dateDeleted(UPDATED_DATE_DELETED)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdate(UPDATED_USER_UPDATE)
            .userDelete(UPDATED_USER_DELETE);
        return annotation;
    }

    @BeforeEach
    public void initTest() {
        annotation = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnnotation() throws Exception {
        int databaseSizeBeforeCreate = annotationRepository.findAll().size();

        // Create the Annotation
        restAnnotationMockMvc.perform(post("/api/annotations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(annotation)))
            .andExpect(status().isCreated());

        // Validate the Annotation in the database
        List<Annotation> annotationList = annotationRepository.findAll();
        assertThat(annotationList).hasSize(databaseSizeBeforeCreate + 1);
        Annotation testAnnotation = annotationList.get(annotationList.size() - 1);
        assertThat(testAnnotation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAnnotation.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testAnnotation.getObservation()).isEqualTo(DEFAULT_OBSERVATION);
        assertThat(testAnnotation.getNbQuestions()).isEqualTo(DEFAULT_NB_QUESTIONS);
        assertThat(testAnnotation.getMoyenne()).isEqualTo(DEFAULT_MOYENNE);
        assertThat(testAnnotation.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testAnnotation.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testAnnotation.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testAnnotation.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testAnnotation.getUserUpdate()).isEqualTo(DEFAULT_USER_UPDATE);
        assertThat(testAnnotation.getUserDelete()).isEqualTo(DEFAULT_USER_DELETE);
    }

    @Test
    @Transactional
    public void createAnnotationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = annotationRepository.findAll().size();

        // Create the Annotation with an existing ID
        annotation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnotationMockMvc.perform(post("/api/annotations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(annotation)))
            .andExpect(status().isBadRequest());

        // Validate the Annotation in the database
        List<Annotation> annotationList = annotationRepository.findAll();
        assertThat(annotationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnnotations() throws Exception {
        // Initialize the database
        annotationRepository.saveAndFlush(annotation);

        // Get all the annotationList
        restAnnotationMockMvc.perform(get("/api/annotations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annotation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].observation").value(hasItem(DEFAULT_OBSERVATION)))
            .andExpect(jsonPath("$.[*].nbQuestions").value(hasItem(DEFAULT_NB_QUESTIONS)))
            .andExpect(jsonPath("$.[*].moyenne").value(hasItem(DEFAULT_MOYENNE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdate").value(hasItem(DEFAULT_USER_UPDATE.intValue())))
            .andExpect(jsonPath("$.[*].userDelete").value(hasItem(DEFAULT_USER_DELETE.intValue())));
    }
    
    @Test
    @Transactional
    public void getAnnotation() throws Exception {
        // Initialize the database
        annotationRepository.saveAndFlush(annotation);

        // Get the annotation
        restAnnotationMockMvc.perform(get("/api/annotations/{id}", annotation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(annotation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.observation").value(DEFAULT_OBSERVATION))
            .andExpect(jsonPath("$.nbQuestions").value(DEFAULT_NB_QUESTIONS))
            .andExpect(jsonPath("$.moyenne").value(DEFAULT_MOYENNE.doubleValue()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdate").value(DEFAULT_USER_UPDATE.intValue()))
            .andExpect(jsonPath("$.userDelete").value(DEFAULT_USER_DELETE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAnnotation() throws Exception {
        // Get the annotation
        restAnnotationMockMvc.perform(get("/api/annotations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnnotation() throws Exception {
        // Initialize the database
        annotationService.save(annotation);

        int databaseSizeBeforeUpdate = annotationRepository.findAll().size();

        // Update the annotation
        Annotation updatedAnnotation = annotationRepository.findById(annotation.getId()).get();
        // Disconnect from session so that the updates on updatedAnnotation are not directly saved in db
        em.detach(updatedAnnotation);
        updatedAnnotation
            .code(UPDATED_CODE)
            .note(UPDATED_NOTE)
            .observation(UPDATED_OBSERVATION)
            .nbQuestions(UPDATED_NB_QUESTIONS)
            .moyenne(UPDATED_MOYENNE)
            .dateDeleted(UPDATED_DATE_DELETED)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdate(UPDATED_USER_UPDATE)
            .userDelete(UPDATED_USER_DELETE);

        restAnnotationMockMvc.perform(put("/api/annotations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnnotation)))
            .andExpect(status().isOk());

        // Validate the Annotation in the database
        List<Annotation> annotationList = annotationRepository.findAll();
        assertThat(annotationList).hasSize(databaseSizeBeforeUpdate);
        Annotation testAnnotation = annotationList.get(annotationList.size() - 1);
        assertThat(testAnnotation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAnnotation.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testAnnotation.getObservation()).isEqualTo(UPDATED_OBSERVATION);
        assertThat(testAnnotation.getNbQuestions()).isEqualTo(UPDATED_NB_QUESTIONS);
        assertThat(testAnnotation.getMoyenne()).isEqualTo(UPDATED_MOYENNE);
        assertThat(testAnnotation.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testAnnotation.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testAnnotation.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testAnnotation.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testAnnotation.getUserUpdate()).isEqualTo(UPDATED_USER_UPDATE);
        assertThat(testAnnotation.getUserDelete()).isEqualTo(UPDATED_USER_DELETE);
    }

    @Test
    @Transactional
    public void updateNonExistingAnnotation() throws Exception {
        int databaseSizeBeforeUpdate = annotationRepository.findAll().size();

        // Create the Annotation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnotationMockMvc.perform(put("/api/annotations")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(annotation)))
            .andExpect(status().isBadRequest());

        // Validate the Annotation in the database
        List<Annotation> annotationList = annotationRepository.findAll();
        assertThat(annotationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnnotation() throws Exception {
        // Initialize the database
        annotationService.save(annotation);

        int databaseSizeBeforeDelete = annotationRepository.findAll().size();

        // Delete the annotation
        restAnnotationMockMvc.perform(delete("/api/annotations/{id}", annotation.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Annotation> annotationList = annotationRepository.findAll();
        assertThat(annotationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
