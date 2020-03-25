package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.SousFamille;
import com.projet.hpd.repository.SousFamilleRepository;
import com.projet.hpd.service.SousFamilleService;
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
 * Integration tests for the {@link SousFamilleResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class SousFamilleResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DELETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DELETED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_UPDATED = 1L;
    private static final Long UPDATED_USER_UPDATED = 2L;

    private static final Long DEFAULT_USER_DELETED = 1L;
    private static final Long UPDATED_USER_DELETED = 2L;

    @Autowired
    private SousFamilleRepository sousFamilleRepository;

    @Autowired
    private SousFamilleService sousFamilleService;

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

    private MockMvc restSousFamilleMockMvc;

    private SousFamille sousFamille;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SousFamilleResource sousFamilleResource = new SousFamilleResource(sousFamilleService);
        this.restSousFamilleMockMvc = MockMvcBuilders.standaloneSetup(sousFamilleResource)
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
    public static SousFamille createEntity(EntityManager em) {
        SousFamille sousFamille = new SousFamille()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return sousFamille;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SousFamille createUpdatedEntity(EntityManager em) {
        SousFamille sousFamille = new SousFamille()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return sousFamille;
    }

    @BeforeEach
    public void initTest() {
        sousFamille = createEntity(em);
    }

    @Test
    @Transactional
    public void createSousFamille() throws Exception {
        int databaseSizeBeforeCreate = sousFamilleRepository.findAll().size();

        // Create the SousFamille
        restSousFamilleMockMvc.perform(post("/api/sous-familles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousFamille)))
            .andExpect(status().isCreated());

        // Validate the SousFamille in the database
        List<SousFamille> sousFamilleList = sousFamilleRepository.findAll();
        assertThat(sousFamilleList).hasSize(databaseSizeBeforeCreate + 1);
        SousFamille testSousFamille = sousFamilleList.get(sousFamilleList.size() - 1);
        assertThat(testSousFamille.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSousFamille.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testSousFamille.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSousFamille.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testSousFamille.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testSousFamille.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testSousFamille.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testSousFamille.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testSousFamille.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createSousFamilleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sousFamilleRepository.findAll().size();

        // Create the SousFamille with an existing ID
        sousFamille.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSousFamilleMockMvc.perform(post("/api/sous-familles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousFamille)))
            .andExpect(status().isBadRequest());

        // Validate the SousFamille in the database
        List<SousFamille> sousFamilleList = sousFamilleRepository.findAll();
        assertThat(sousFamilleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSousFamilles() throws Exception {
        // Initialize the database
        sousFamilleRepository.saveAndFlush(sousFamille);

        // Get all the sousFamilleList
        restSousFamilleMockMvc.perform(get("/api/sous-familles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sousFamille.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getSousFamille() throws Exception {
        // Initialize the database
        sousFamilleRepository.saveAndFlush(sousFamille);

        // Get the sousFamille
        restSousFamilleMockMvc.perform(get("/api/sous-familles/{id}", sousFamille.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sousFamille.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSousFamille() throws Exception {
        // Get the sousFamille
        restSousFamilleMockMvc.perform(get("/api/sous-familles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSousFamille() throws Exception {
        // Initialize the database
        sousFamilleService.save(sousFamille);

        int databaseSizeBeforeUpdate = sousFamilleRepository.findAll().size();

        // Update the sousFamille
        SousFamille updatedSousFamille = sousFamilleRepository.findById(sousFamille.getId()).get();
        // Disconnect from session so that the updates on updatedSousFamille are not directly saved in db
        em.detach(updatedSousFamille);
        updatedSousFamille
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restSousFamilleMockMvc.perform(put("/api/sous-familles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSousFamille)))
            .andExpect(status().isOk());

        // Validate the SousFamille in the database
        List<SousFamille> sousFamilleList = sousFamilleRepository.findAll();
        assertThat(sousFamilleList).hasSize(databaseSizeBeforeUpdate);
        SousFamille testSousFamille = sousFamilleList.get(sousFamilleList.size() - 1);
        assertThat(testSousFamille.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSousFamille.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testSousFamille.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSousFamille.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testSousFamille.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testSousFamille.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testSousFamille.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testSousFamille.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testSousFamille.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingSousFamille() throws Exception {
        int databaseSizeBeforeUpdate = sousFamilleRepository.findAll().size();

        // Create the SousFamille

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSousFamilleMockMvc.perform(put("/api/sous-familles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sousFamille)))
            .andExpect(status().isBadRequest());

        // Validate the SousFamille in the database
        List<SousFamille> sousFamilleList = sousFamilleRepository.findAll();
        assertThat(sousFamilleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSousFamille() throws Exception {
        // Initialize the database
        sousFamilleService.save(sousFamille);

        int databaseSizeBeforeDelete = sousFamilleRepository.findAll().size();

        // Delete the sousFamille
        restSousFamilleMockMvc.perform(delete("/api/sous-familles/{id}", sousFamille.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SousFamille> sousFamilleList = sousFamilleRepository.findAll();
        assertThat(sousFamilleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
