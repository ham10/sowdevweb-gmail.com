package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.EtatCaisse;
import com.projet.hpd.repository.EtatCaisseRepository;
import com.projet.hpd.service.EtatCaisseService;
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
 * Integration tests for the {@link EtatCaisseResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class EtatCaisseResourceIT {

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

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
    private EtatCaisseRepository etatCaisseRepository;

    @Autowired
    private EtatCaisseService etatCaisseService;

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

    private MockMvc restEtatCaisseMockMvc;

    private EtatCaisse etatCaisse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtatCaisseResource etatCaisseResource = new EtatCaisseResource(etatCaisseService);
        this.restEtatCaisseMockMvc = MockMvcBuilders.standaloneSetup(etatCaisseResource)
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
    public static EtatCaisse createEntity(EntityManager em) {
        EtatCaisse etatCaisse = new EtatCaisse()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return etatCaisse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatCaisse createUpdatedEntity(EntityManager em) {
        EtatCaisse etatCaisse = new EtatCaisse()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return etatCaisse;
    }

    @BeforeEach
    public void initTest() {
        etatCaisse = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtatCaisse() throws Exception {
        int databaseSizeBeforeCreate = etatCaisseRepository.findAll().size();

        // Create the EtatCaisse
        restEtatCaisseMockMvc.perform(post("/api/etat-caisses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatCaisse)))
            .andExpect(status().isCreated());

        // Validate the EtatCaisse in the database
        List<EtatCaisse> etatCaisseList = etatCaisseRepository.findAll();
        assertThat(etatCaisseList).hasSize(databaseSizeBeforeCreate + 1);
        EtatCaisse testEtatCaisse = etatCaisseList.get(etatCaisseList.size() - 1);
        assertThat(testEtatCaisse.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEtatCaisse.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testEtatCaisse.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testEtatCaisse.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testEtatCaisse.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testEtatCaisse.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testEtatCaisse.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testEtatCaisse.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createEtatCaisseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etatCaisseRepository.findAll().size();

        // Create the EtatCaisse with an existing ID
        etatCaisse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatCaisseMockMvc.perform(post("/api/etat-caisses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatCaisse)))
            .andExpect(status().isBadRequest());

        // Validate the EtatCaisse in the database
        List<EtatCaisse> etatCaisseList = etatCaisseRepository.findAll();
        assertThat(etatCaisseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEtatCaisses() throws Exception {
        // Initialize the database
        etatCaisseRepository.saveAndFlush(etatCaisse);

        // Get all the etatCaisseList
        restEtatCaisseMockMvc.perform(get("/api/etat-caisses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatCaisse.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getEtatCaisse() throws Exception {
        // Initialize the database
        etatCaisseRepository.saveAndFlush(etatCaisse);

        // Get the etatCaisse
        restEtatCaisseMockMvc.perform(get("/api/etat-caisses/{id}", etatCaisse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etatCaisse.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEtatCaisse() throws Exception {
        // Get the etatCaisse
        restEtatCaisseMockMvc.perform(get("/api/etat-caisses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtatCaisse() throws Exception {
        // Initialize the database
        etatCaisseService.save(etatCaisse);

        int databaseSizeBeforeUpdate = etatCaisseRepository.findAll().size();

        // Update the etatCaisse
        EtatCaisse updatedEtatCaisse = etatCaisseRepository.findById(etatCaisse.getId()).get();
        // Disconnect from session so that the updates on updatedEtatCaisse are not directly saved in db
        em.detach(updatedEtatCaisse);
        updatedEtatCaisse
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restEtatCaisseMockMvc.perform(put("/api/etat-caisses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtatCaisse)))
            .andExpect(status().isOk());

        // Validate the EtatCaisse in the database
        List<EtatCaisse> etatCaisseList = etatCaisseRepository.findAll();
        assertThat(etatCaisseList).hasSize(databaseSizeBeforeUpdate);
        EtatCaisse testEtatCaisse = etatCaisseList.get(etatCaisseList.size() - 1);
        assertThat(testEtatCaisse.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEtatCaisse.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEtatCaisse.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testEtatCaisse.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testEtatCaisse.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testEtatCaisse.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testEtatCaisse.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testEtatCaisse.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingEtatCaisse() throws Exception {
        int databaseSizeBeforeUpdate = etatCaisseRepository.findAll().size();

        // Create the EtatCaisse

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatCaisseMockMvc.perform(put("/api/etat-caisses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatCaisse)))
            .andExpect(status().isBadRequest());

        // Validate the EtatCaisse in the database
        List<EtatCaisse> etatCaisseList = etatCaisseRepository.findAll();
        assertThat(etatCaisseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtatCaisse() throws Exception {
        // Initialize the database
        etatCaisseService.save(etatCaisse);

        int databaseSizeBeforeDelete = etatCaisseRepository.findAll().size();

        // Delete the etatCaisse
        restEtatCaisseMockMvc.perform(delete("/api/etat-caisses/{id}", etatCaisse.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtatCaisse> etatCaisseList = etatCaisseRepository.findAll();
        assertThat(etatCaisseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
