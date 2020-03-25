package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.ActeMedical;
import com.projet.hpd.repository.ActeMedicalRepository;
import com.projet.hpd.service.ActeMedicalService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.projet.hpd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ActeMedicalResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class ActeMedicalResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_A = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_A = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_UPDATED = 1L;
    private static final Long UPDATED_USER_UPDATED = 2L;

    private static final Long DEFAULT_USER_DELETED = 1L;
    private static final Long UPDATED_USER_DELETED = 2L;

    @Autowired
    private ActeMedicalRepository acteMedicalRepository;

    @Autowired
    private ActeMedicalService acteMedicalService;

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

    private MockMvc restActeMedicalMockMvc;

    private ActeMedical acteMedical;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActeMedicalResource acteMedicalResource = new ActeMedicalResource(acteMedicalService);
        this.restActeMedicalMockMvc = MockMvcBuilders.standaloneSetup(acteMedicalResource)
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
    public static ActeMedical createEntity(EntityManager em) {
        ActeMedical acteMedical = new ActeMedical()
            .code(DEFAULT_CODE)
            .libelleA(DEFAULT_LIBELLE_A)
            .description(DEFAULT_DESCRIPTION)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return acteMedical;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActeMedical createUpdatedEntity(EntityManager em) {
        ActeMedical acteMedical = new ActeMedical()
            .code(UPDATED_CODE)
            .libelleA(UPDATED_LIBELLE_A)
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return acteMedical;
    }

    @BeforeEach
    public void initTest() {
        acteMedical = createEntity(em);
    }

    @Test
    @Transactional
    public void createActeMedical() throws Exception {
        int databaseSizeBeforeCreate = acteMedicalRepository.findAll().size();

        // Create the ActeMedical
        restActeMedicalMockMvc.perform(post("/api/acte-medicals")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acteMedical)))
            .andExpect(status().isCreated());

        // Validate the ActeMedical in the database
        List<ActeMedical> acteMedicalList = acteMedicalRepository.findAll();
        assertThat(acteMedicalList).hasSize(databaseSizeBeforeCreate + 1);
        ActeMedical testActeMedical = acteMedicalList.get(acteMedicalList.size() - 1);
        assertThat(testActeMedical.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testActeMedical.getLibelleA()).isEqualTo(DEFAULT_LIBELLE_A);
        assertThat(testActeMedical.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testActeMedical.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testActeMedical.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testActeMedical.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testActeMedical.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testActeMedical.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createActeMedicalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acteMedicalRepository.findAll().size();

        // Create the ActeMedical with an existing ID
        acteMedical.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActeMedicalMockMvc.perform(post("/api/acte-medicals")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acteMedical)))
            .andExpect(status().isBadRequest());

        // Validate the ActeMedical in the database
        List<ActeMedical> acteMedicalList = acteMedicalRepository.findAll();
        assertThat(acteMedicalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllActeMedicals() throws Exception {
        // Initialize the database
        acteMedicalRepository.saveAndFlush(acteMedical);

        // Get all the acteMedicalList
        restActeMedicalMockMvc.perform(get("/api/acte-medicals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acteMedical.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelleA").value(hasItem(DEFAULT_LIBELLE_A)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getActeMedical() throws Exception {
        // Initialize the database
        acteMedicalRepository.saveAndFlush(acteMedical);

        // Get the acteMedical
        restActeMedicalMockMvc.perform(get("/api/acte-medicals/{id}", acteMedical.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(acteMedical.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelleA").value(DEFAULT_LIBELLE_A))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingActeMedical() throws Exception {
        // Get the acteMedical
        restActeMedicalMockMvc.perform(get("/api/acte-medicals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActeMedical() throws Exception {
        // Initialize the database
        acteMedicalService.save(acteMedical);

        int databaseSizeBeforeUpdate = acteMedicalRepository.findAll().size();

        // Update the acteMedical
        ActeMedical updatedActeMedical = acteMedicalRepository.findById(acteMedical.getId()).get();
        // Disconnect from session so that the updates on updatedActeMedical are not directly saved in db
        em.detach(updatedActeMedical);
        updatedActeMedical
            .code(UPDATED_CODE)
            .libelleA(UPDATED_LIBELLE_A)
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restActeMedicalMockMvc.perform(put("/api/acte-medicals")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedActeMedical)))
            .andExpect(status().isOk());

        // Validate the ActeMedical in the database
        List<ActeMedical> acteMedicalList = acteMedicalRepository.findAll();
        assertThat(acteMedicalList).hasSize(databaseSizeBeforeUpdate);
        ActeMedical testActeMedical = acteMedicalList.get(acteMedicalList.size() - 1);
        assertThat(testActeMedical.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testActeMedical.getLibelleA()).isEqualTo(UPDATED_LIBELLE_A);
        assertThat(testActeMedical.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testActeMedical.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testActeMedical.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testActeMedical.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testActeMedical.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testActeMedical.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingActeMedical() throws Exception {
        int databaseSizeBeforeUpdate = acteMedicalRepository.findAll().size();

        // Create the ActeMedical

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActeMedicalMockMvc.perform(put("/api/acte-medicals")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acteMedical)))
            .andExpect(status().isBadRequest());

        // Validate the ActeMedical in the database
        List<ActeMedical> acteMedicalList = acteMedicalRepository.findAll();
        assertThat(acteMedicalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActeMedical() throws Exception {
        // Initialize the database
        acteMedicalService.save(acteMedical);

        int databaseSizeBeforeDelete = acteMedicalRepository.findAll().size();

        // Delete the acteMedical
        restActeMedicalMockMvc.perform(delete("/api/acte-medicals/{id}", acteMedical.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ActeMedical> acteMedicalList = acteMedicalRepository.findAll();
        assertThat(acteMedicalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
