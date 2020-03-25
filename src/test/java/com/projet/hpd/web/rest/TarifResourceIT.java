package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Tarif;
import com.projet.hpd.repository.TarifRepository;
import com.projet.hpd.service.TarifService;
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
 * Integration tests for the {@link TarifResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TarifResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final Integer DEFAULT_POURCENTAGE = 1;
    private static final Integer UPDATED_POURCENTAGE = 2;

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
    private TarifRepository tarifRepository;

    @Autowired
    private TarifService tarifService;

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

    private MockMvc restTarifMockMvc;

    private Tarif tarif;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TarifResource tarifResource = new TarifResource(tarifService);
        this.restTarifMockMvc = MockMvcBuilders.standaloneSetup(tarifResource)
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
    public static Tarif createEntity(EntityManager em) {
        Tarif tarif = new Tarif()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .montant(DEFAULT_MONTANT)
            .pourcentage(DEFAULT_POURCENTAGE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return tarif;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tarif createUpdatedEntity(EntityManager em) {
        Tarif tarif = new Tarif()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .montant(UPDATED_MONTANT)
            .pourcentage(UPDATED_POURCENTAGE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return tarif;
    }

    @BeforeEach
    public void initTest() {
        tarif = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarif() throws Exception {
        int databaseSizeBeforeCreate = tarifRepository.findAll().size();

        // Create the Tarif
        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarif)))
            .andExpect(status().isCreated());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeCreate + 1);
        Tarif testTarif = tarifList.get(tarifList.size() - 1);
        assertThat(testTarif.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTarif.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTarif.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testTarif.getPourcentage()).isEqualTo(DEFAULT_POURCENTAGE);
        assertThat(testTarif.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTarif.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTarif.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testTarif.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTarif.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTarif.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTarifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifRepository.findAll().size();

        // Create the Tarif with an existing ID
        tarif.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifMockMvc.perform(post("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarif)))
            .andExpect(status().isBadRequest());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTarifs() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get all the tarifList
        restTarifMockMvc.perform(get("/api/tarifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarif.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].pourcentage").value(hasItem(DEFAULT_POURCENTAGE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTarif() throws Exception {
        // Initialize the database
        tarifRepository.saveAndFlush(tarif);

        // Get the tarif
        restTarifMockMvc.perform(get("/api/tarifs/{id}", tarif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tarif.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.pourcentage").value(DEFAULT_POURCENTAGE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTarif() throws Exception {
        // Get the tarif
        restTarifMockMvc.perform(get("/api/tarifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarif() throws Exception {
        // Initialize the database
        tarifService.save(tarif);

        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();

        // Update the tarif
        Tarif updatedTarif = tarifRepository.findById(tarif.getId()).get();
        // Disconnect from session so that the updates on updatedTarif are not directly saved in db
        em.detach(updatedTarif);
        updatedTarif
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .montant(UPDATED_MONTANT)
            .pourcentage(UPDATED_POURCENTAGE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTarifMockMvc.perform(put("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarif)))
            .andExpect(status().isOk());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate);
        Tarif testTarif = tarifList.get(tarifList.size() - 1);
        assertThat(testTarif.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTarif.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTarif.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testTarif.getPourcentage()).isEqualTo(UPDATED_POURCENTAGE);
        assertThat(testTarif.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTarif.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTarif.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testTarif.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTarif.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTarif.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTarif() throws Exception {
        int databaseSizeBeforeUpdate = tarifRepository.findAll().size();

        // Create the Tarif

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTarifMockMvc.perform(put("/api/tarifs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tarif)))
            .andExpect(status().isBadRequest());

        // Validate the Tarif in the database
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTarif() throws Exception {
        // Initialize the database
        tarifService.save(tarif);

        int databaseSizeBeforeDelete = tarifRepository.findAll().size();

        // Delete the tarif
        restTarifMockMvc.perform(delete("/api/tarifs/{id}", tarif.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tarif> tarifList = tarifRepository.findAll();
        assertThat(tarifList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
