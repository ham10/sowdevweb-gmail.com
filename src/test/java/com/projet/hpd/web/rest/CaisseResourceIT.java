package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Caisse;
import com.projet.hpd.repository.CaisseRepository;
import com.projet.hpd.service.CaisseService;
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
 * Integration tests for the {@link CaisseResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class CaisseResourceIT {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final LocalDate DEFAULT_SOLDE_MIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SOLDE_MIN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SOLDE_MAX = "AAAAAAAAAA";
    private static final String UPDATED_SOLDE_MAX = "BBBBBBBBBB";

    private static final Integer DEFAULT_MONTANT = 1;
    private static final Integer UPDATED_MONTANT = 2;

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
    private CaisseRepository caisseRepository;

    @Autowired
    private CaisseService caisseService;

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

    private MockMvc restCaisseMockMvc;

    private Caisse caisse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CaisseResource caisseResource = new CaisseResource(caisseService);
        this.restCaisseMockMvc = MockMvcBuilders.standaloneSetup(caisseResource)
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
    public static Caisse createEntity(EntityManager em) {
        Caisse caisse = new Caisse()
            .numero(DEFAULT_NUMERO)
            .soldeMin(DEFAULT_SOLDE_MIN)
            .soldeMax(DEFAULT_SOLDE_MAX)
            .montant(DEFAULT_MONTANT)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return caisse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Caisse createUpdatedEntity(EntityManager em) {
        Caisse caisse = new Caisse()
            .numero(UPDATED_NUMERO)
            .soldeMin(UPDATED_SOLDE_MIN)
            .soldeMax(UPDATED_SOLDE_MAX)
            .montant(UPDATED_MONTANT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return caisse;
    }

    @BeforeEach
    public void initTest() {
        caisse = createEntity(em);
    }

    @Test
    @Transactional
    public void createCaisse() throws Exception {
        int databaseSizeBeforeCreate = caisseRepository.findAll().size();

        // Create the Caisse
        restCaisseMockMvc.perform(post("/api/caisses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(caisse)))
            .andExpect(status().isCreated());

        // Validate the Caisse in the database
        List<Caisse> caisseList = caisseRepository.findAll();
        assertThat(caisseList).hasSize(databaseSizeBeforeCreate + 1);
        Caisse testCaisse = caisseList.get(caisseList.size() - 1);
        assertThat(testCaisse.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCaisse.getSoldeMin()).isEqualTo(DEFAULT_SOLDE_MIN);
        assertThat(testCaisse.getSoldeMax()).isEqualTo(DEFAULT_SOLDE_MAX);
        assertThat(testCaisse.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testCaisse.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCaisse.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testCaisse.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testCaisse.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testCaisse.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testCaisse.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createCaisseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = caisseRepository.findAll().size();

        // Create the Caisse with an existing ID
        caisse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCaisseMockMvc.perform(post("/api/caisses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(caisse)))
            .andExpect(status().isBadRequest());

        // Validate the Caisse in the database
        List<Caisse> caisseList = caisseRepository.findAll();
        assertThat(caisseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCaisses() throws Exception {
        // Initialize the database
        caisseRepository.saveAndFlush(caisse);

        // Get all the caisseList
        restCaisseMockMvc.perform(get("/api/caisses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(caisse.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].soldeMin").value(hasItem(DEFAULT_SOLDE_MIN.toString())))
            .andExpect(jsonPath("$.[*].soldeMax").value(hasItem(DEFAULT_SOLDE_MAX)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getCaisse() throws Exception {
        // Initialize the database
        caisseRepository.saveAndFlush(caisse);

        // Get the caisse
        restCaisseMockMvc.perform(get("/api/caisses/{id}", caisse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(caisse.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.soldeMin").value(DEFAULT_SOLDE_MIN.toString()))
            .andExpect(jsonPath("$.soldeMax").value(DEFAULT_SOLDE_MAX))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCaisse() throws Exception {
        // Get the caisse
        restCaisseMockMvc.perform(get("/api/caisses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCaisse() throws Exception {
        // Initialize the database
        caisseService.save(caisse);

        int databaseSizeBeforeUpdate = caisseRepository.findAll().size();

        // Update the caisse
        Caisse updatedCaisse = caisseRepository.findById(caisse.getId()).get();
        // Disconnect from session so that the updates on updatedCaisse are not directly saved in db
        em.detach(updatedCaisse);
        updatedCaisse
            .numero(UPDATED_NUMERO)
            .soldeMin(UPDATED_SOLDE_MIN)
            .soldeMax(UPDATED_SOLDE_MAX)
            .montant(UPDATED_MONTANT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restCaisseMockMvc.perform(put("/api/caisses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCaisse)))
            .andExpect(status().isOk());

        // Validate the Caisse in the database
        List<Caisse> caisseList = caisseRepository.findAll();
        assertThat(caisseList).hasSize(databaseSizeBeforeUpdate);
        Caisse testCaisse = caisseList.get(caisseList.size() - 1);
        assertThat(testCaisse.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCaisse.getSoldeMin()).isEqualTo(UPDATED_SOLDE_MIN);
        assertThat(testCaisse.getSoldeMax()).isEqualTo(UPDATED_SOLDE_MAX);
        assertThat(testCaisse.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testCaisse.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCaisse.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testCaisse.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testCaisse.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testCaisse.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testCaisse.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingCaisse() throws Exception {
        int databaseSizeBeforeUpdate = caisseRepository.findAll().size();

        // Create the Caisse

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCaisseMockMvc.perform(put("/api/caisses")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(caisse)))
            .andExpect(status().isBadRequest());

        // Validate the Caisse in the database
        List<Caisse> caisseList = caisseRepository.findAll();
        assertThat(caisseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCaisse() throws Exception {
        // Initialize the database
        caisseService.save(caisse);

        int databaseSizeBeforeDelete = caisseRepository.findAll().size();

        // Delete the caisse
        restCaisseMockMvc.perform(delete("/api/caisses/{id}", caisse.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Caisse> caisseList = caisseRepository.findAll();
        assertThat(caisseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
