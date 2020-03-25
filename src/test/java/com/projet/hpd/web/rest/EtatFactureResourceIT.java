package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.EtatFacture;
import com.projet.hpd.repository.EtatFactureRepository;
import com.projet.hpd.service.EtatFactureService;
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
 * Integration tests for the {@link EtatFactureResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class EtatFactureResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

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
    private EtatFactureRepository etatFactureRepository;

    @Autowired
    private EtatFactureService etatFactureService;

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

    private MockMvc restEtatFactureMockMvc;

    private EtatFacture etatFacture;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtatFactureResource etatFactureResource = new EtatFactureResource(etatFactureService);
        this.restEtatFactureMockMvc = MockMvcBuilders.standaloneSetup(etatFactureResource)
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
    public static EtatFacture createEntity(EntityManager em) {
        EtatFacture etatFacture = new EtatFacture()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return etatFacture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatFacture createUpdatedEntity(EntityManager em) {
        EtatFacture etatFacture = new EtatFacture()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return etatFacture;
    }

    @BeforeEach
    public void initTest() {
        etatFacture = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtatFacture() throws Exception {
        int databaseSizeBeforeCreate = etatFactureRepository.findAll().size();

        // Create the EtatFacture
        restEtatFactureMockMvc.perform(post("/api/etat-factures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatFacture)))
            .andExpect(status().isCreated());

        // Validate the EtatFacture in the database
        List<EtatFacture> etatFactureList = etatFactureRepository.findAll();
        assertThat(etatFactureList).hasSize(databaseSizeBeforeCreate + 1);
        EtatFacture testEtatFacture = etatFactureList.get(etatFactureList.size() - 1);
        assertThat(testEtatFacture.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEtatFacture.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testEtatFacture.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testEtatFacture.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testEtatFacture.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testEtatFacture.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testEtatFacture.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testEtatFacture.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createEtatFactureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etatFactureRepository.findAll().size();

        // Create the EtatFacture with an existing ID
        etatFacture.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatFactureMockMvc.perform(post("/api/etat-factures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatFacture)))
            .andExpect(status().isBadRequest());

        // Validate the EtatFacture in the database
        List<EtatFacture> etatFactureList = etatFactureRepository.findAll();
        assertThat(etatFactureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEtatFactures() throws Exception {
        // Initialize the database
        etatFactureRepository.saveAndFlush(etatFacture);

        // Get all the etatFactureList
        restEtatFactureMockMvc.perform(get("/api/etat-factures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatFacture.getId().intValue())))
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
    public void getEtatFacture() throws Exception {
        // Initialize the database
        etatFactureRepository.saveAndFlush(etatFacture);

        // Get the etatFacture
        restEtatFactureMockMvc.perform(get("/api/etat-factures/{id}", etatFacture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etatFacture.getId().intValue()))
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
    public void getNonExistingEtatFacture() throws Exception {
        // Get the etatFacture
        restEtatFactureMockMvc.perform(get("/api/etat-factures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtatFacture() throws Exception {
        // Initialize the database
        etatFactureService.save(etatFacture);

        int databaseSizeBeforeUpdate = etatFactureRepository.findAll().size();

        // Update the etatFacture
        EtatFacture updatedEtatFacture = etatFactureRepository.findById(etatFacture.getId()).get();
        // Disconnect from session so that the updates on updatedEtatFacture are not directly saved in db
        em.detach(updatedEtatFacture);
        updatedEtatFacture
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restEtatFactureMockMvc.perform(put("/api/etat-factures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtatFacture)))
            .andExpect(status().isOk());

        // Validate the EtatFacture in the database
        List<EtatFacture> etatFactureList = etatFactureRepository.findAll();
        assertThat(etatFactureList).hasSize(databaseSizeBeforeUpdate);
        EtatFacture testEtatFacture = etatFactureList.get(etatFactureList.size() - 1);
        assertThat(testEtatFacture.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEtatFacture.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEtatFacture.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testEtatFacture.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testEtatFacture.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testEtatFacture.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testEtatFacture.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testEtatFacture.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingEtatFacture() throws Exception {
        int databaseSizeBeforeUpdate = etatFactureRepository.findAll().size();

        // Create the EtatFacture

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatFactureMockMvc.perform(put("/api/etat-factures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatFacture)))
            .andExpect(status().isBadRequest());

        // Validate the EtatFacture in the database
        List<EtatFacture> etatFactureList = etatFactureRepository.findAll();
        assertThat(etatFactureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtatFacture() throws Exception {
        // Initialize the database
        etatFactureService.save(etatFacture);

        int databaseSizeBeforeDelete = etatFactureRepository.findAll().size();

        // Delete the etatFacture
        restEtatFactureMockMvc.perform(delete("/api/etat-factures/{id}", etatFacture.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtatFacture> etatFactureList = etatFactureRepository.findAll();
        assertThat(etatFactureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
