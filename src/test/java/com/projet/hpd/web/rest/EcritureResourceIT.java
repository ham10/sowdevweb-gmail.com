package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Ecriture;
import com.projet.hpd.repository.EcritureRepository;
import com.projet.hpd.service.EcritureService;
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
 * Integration tests for the {@link EcritureResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class EcritureResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final String DEFAULT_SENS = "AAAAAAAAAA";
    private static final String UPDATED_SENS = "BBBBBBBBBB";

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
    private EcritureRepository ecritureRepository;

    @Autowired
    private EcritureService ecritureService;

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

    private MockMvc restEcritureMockMvc;

    private Ecriture ecriture;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EcritureResource ecritureResource = new EcritureResource(ecritureService);
        this.restEcritureMockMvc = MockMvcBuilders.standaloneSetup(ecritureResource)
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
    public static Ecriture createEntity(EntityManager em) {
        Ecriture ecriture = new Ecriture()
            .libelle(DEFAULT_LIBELLE)
            .date(DEFAULT_DATE)
            .montant(DEFAULT_MONTANT)
            .sens(DEFAULT_SENS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return ecriture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ecriture createUpdatedEntity(EntityManager em) {
        Ecriture ecriture = new Ecriture()
            .libelle(UPDATED_LIBELLE)
            .date(UPDATED_DATE)
            .montant(UPDATED_MONTANT)
            .sens(UPDATED_SENS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return ecriture;
    }

    @BeforeEach
    public void initTest() {
        ecriture = createEntity(em);
    }

    @Test
    @Transactional
    public void createEcriture() throws Exception {
        int databaseSizeBeforeCreate = ecritureRepository.findAll().size();

        // Create the Ecriture
        restEcritureMockMvc.perform(post("/api/ecritures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ecriture)))
            .andExpect(status().isCreated());

        // Validate the Ecriture in the database
        List<Ecriture> ecritureList = ecritureRepository.findAll();
        assertThat(ecritureList).hasSize(databaseSizeBeforeCreate + 1);
        Ecriture testEcriture = ecritureList.get(ecritureList.size() - 1);
        assertThat(testEcriture.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testEcriture.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testEcriture.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testEcriture.getSens()).isEqualTo(DEFAULT_SENS);
        assertThat(testEcriture.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testEcriture.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testEcriture.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testEcriture.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testEcriture.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testEcriture.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createEcritureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ecritureRepository.findAll().size();

        // Create the Ecriture with an existing ID
        ecriture.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEcritureMockMvc.perform(post("/api/ecritures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ecriture)))
            .andExpect(status().isBadRequest());

        // Validate the Ecriture in the database
        List<Ecriture> ecritureList = ecritureRepository.findAll();
        assertThat(ecritureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEcritures() throws Exception {
        // Initialize the database
        ecritureRepository.saveAndFlush(ecriture);

        // Get all the ecritureList
        restEcritureMockMvc.perform(get("/api/ecritures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ecriture.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].sens").value(hasItem(DEFAULT_SENS)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getEcriture() throws Exception {
        // Initialize the database
        ecritureRepository.saveAndFlush(ecriture);

        // Get the ecriture
        restEcritureMockMvc.perform(get("/api/ecritures/{id}", ecriture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ecriture.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.sens").value(DEFAULT_SENS))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEcriture() throws Exception {
        // Get the ecriture
        restEcritureMockMvc.perform(get("/api/ecritures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEcriture() throws Exception {
        // Initialize the database
        ecritureService.save(ecriture);

        int databaseSizeBeforeUpdate = ecritureRepository.findAll().size();

        // Update the ecriture
        Ecriture updatedEcriture = ecritureRepository.findById(ecriture.getId()).get();
        // Disconnect from session so that the updates on updatedEcriture are not directly saved in db
        em.detach(updatedEcriture);
        updatedEcriture
            .libelle(UPDATED_LIBELLE)
            .date(UPDATED_DATE)
            .montant(UPDATED_MONTANT)
            .sens(UPDATED_SENS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restEcritureMockMvc.perform(put("/api/ecritures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEcriture)))
            .andExpect(status().isOk());

        // Validate the Ecriture in the database
        List<Ecriture> ecritureList = ecritureRepository.findAll();
        assertThat(ecritureList).hasSize(databaseSizeBeforeUpdate);
        Ecriture testEcriture = ecritureList.get(ecritureList.size() - 1);
        assertThat(testEcriture.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEcriture.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testEcriture.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testEcriture.getSens()).isEqualTo(UPDATED_SENS);
        assertThat(testEcriture.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testEcriture.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testEcriture.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testEcriture.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testEcriture.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testEcriture.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingEcriture() throws Exception {
        int databaseSizeBeforeUpdate = ecritureRepository.findAll().size();

        // Create the Ecriture

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEcritureMockMvc.perform(put("/api/ecritures")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ecriture)))
            .andExpect(status().isBadRequest());

        // Validate the Ecriture in the database
        List<Ecriture> ecritureList = ecritureRepository.findAll();
        assertThat(ecritureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEcriture() throws Exception {
        // Initialize the database
        ecritureService.save(ecriture);

        int databaseSizeBeforeDelete = ecritureRepository.findAll().size();

        // Delete the ecriture
        restEcritureMockMvc.perform(delete("/api/ecritures/{id}", ecriture.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ecriture> ecritureList = ecritureRepository.findAll();
        assertThat(ecritureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
