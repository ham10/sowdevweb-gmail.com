package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.LigneLivraison;
import com.projet.hpd.repository.LigneLivraisonRepository;
import com.projet.hpd.service.LigneLivraisonService;
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
 * Integration tests for the {@link LigneLivraisonResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class LigneLivraisonResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITE = 1;
    private static final Integer UPDATED_QUANTITE = 2;

    private static final Double DEFAULT_PRIX_UNITAIRE = 1D;
    private static final Double UPDATED_PRIX_UNITAIRE = 2D;

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
    private LigneLivraisonRepository ligneLivraisonRepository;

    @Autowired
    private LigneLivraisonService ligneLivraisonService;

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

    private MockMvc restLigneLivraisonMockMvc;

    private LigneLivraison ligneLivraison;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LigneLivraisonResource ligneLivraisonResource = new LigneLivraisonResource(ligneLivraisonService);
        this.restLigneLivraisonMockMvc = MockMvcBuilders.standaloneSetup(ligneLivraisonResource)
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
    public static LigneLivraison createEntity(EntityManager em) {
        LigneLivraison ligneLivraison = new LigneLivraison()
            .designation(DEFAULT_DESIGNATION)
            .quantite(DEFAULT_QUANTITE)
            .prixUnitaire(DEFAULT_PRIX_UNITAIRE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return ligneLivraison;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneLivraison createUpdatedEntity(EntityManager em) {
        LigneLivraison ligneLivraison = new LigneLivraison()
            .designation(UPDATED_DESIGNATION)
            .quantite(UPDATED_QUANTITE)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return ligneLivraison;
    }

    @BeforeEach
    public void initTest() {
        ligneLivraison = createEntity(em);
    }

    @Test
    @Transactional
    public void createLigneLivraison() throws Exception {
        int databaseSizeBeforeCreate = ligneLivraisonRepository.findAll().size();

        // Create the LigneLivraison
        restLigneLivraisonMockMvc.perform(post("/api/ligne-livraisons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ligneLivraison)))
            .andExpect(status().isCreated());

        // Validate the LigneLivraison in the database
        List<LigneLivraison> ligneLivraisonList = ligneLivraisonRepository.findAll();
        assertThat(ligneLivraisonList).hasSize(databaseSizeBeforeCreate + 1);
        LigneLivraison testLigneLivraison = ligneLivraisonList.get(ligneLivraisonList.size() - 1);
        assertThat(testLigneLivraison.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testLigneLivraison.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testLigneLivraison.getPrixUnitaire()).isEqualTo(DEFAULT_PRIX_UNITAIRE);
        assertThat(testLigneLivraison.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testLigneLivraison.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testLigneLivraison.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testLigneLivraison.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testLigneLivraison.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testLigneLivraison.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createLigneLivraisonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ligneLivraisonRepository.findAll().size();

        // Create the LigneLivraison with an existing ID
        ligneLivraison.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLigneLivraisonMockMvc.perform(post("/api/ligne-livraisons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ligneLivraison)))
            .andExpect(status().isBadRequest());

        // Validate the LigneLivraison in the database
        List<LigneLivraison> ligneLivraisonList = ligneLivraisonRepository.findAll();
        assertThat(ligneLivraisonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLigneLivraisons() throws Exception {
        // Initialize the database
        ligneLivraisonRepository.saveAndFlush(ligneLivraison);

        // Get all the ligneLivraisonList
        restLigneLivraisonMockMvc.perform(get("/api/ligne-livraisons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ligneLivraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE)))
            .andExpect(jsonPath("$.[*].prixUnitaire").value(hasItem(DEFAULT_PRIX_UNITAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getLigneLivraison() throws Exception {
        // Initialize the database
        ligneLivraisonRepository.saveAndFlush(ligneLivraison);

        // Get the ligneLivraison
        restLigneLivraisonMockMvc.perform(get("/api/ligne-livraisons/{id}", ligneLivraison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ligneLivraison.getId().intValue()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE))
            .andExpect(jsonPath("$.prixUnitaire").value(DEFAULT_PRIX_UNITAIRE.doubleValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLigneLivraison() throws Exception {
        // Get the ligneLivraison
        restLigneLivraisonMockMvc.perform(get("/api/ligne-livraisons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLigneLivraison() throws Exception {
        // Initialize the database
        ligneLivraisonService.save(ligneLivraison);

        int databaseSizeBeforeUpdate = ligneLivraisonRepository.findAll().size();

        // Update the ligneLivraison
        LigneLivraison updatedLigneLivraison = ligneLivraisonRepository.findById(ligneLivraison.getId()).get();
        // Disconnect from session so that the updates on updatedLigneLivraison are not directly saved in db
        em.detach(updatedLigneLivraison);
        updatedLigneLivraison
            .designation(UPDATED_DESIGNATION)
            .quantite(UPDATED_QUANTITE)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restLigneLivraisonMockMvc.perform(put("/api/ligne-livraisons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLigneLivraison)))
            .andExpect(status().isOk());

        // Validate the LigneLivraison in the database
        List<LigneLivraison> ligneLivraisonList = ligneLivraisonRepository.findAll();
        assertThat(ligneLivraisonList).hasSize(databaseSizeBeforeUpdate);
        LigneLivraison testLigneLivraison = ligneLivraisonList.get(ligneLivraisonList.size() - 1);
        assertThat(testLigneLivraison.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testLigneLivraison.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testLigneLivraison.getPrixUnitaire()).isEqualTo(UPDATED_PRIX_UNITAIRE);
        assertThat(testLigneLivraison.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testLigneLivraison.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testLigneLivraison.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testLigneLivraison.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testLigneLivraison.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testLigneLivraison.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingLigneLivraison() throws Exception {
        int databaseSizeBeforeUpdate = ligneLivraisonRepository.findAll().size();

        // Create the LigneLivraison

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigneLivraisonMockMvc.perform(put("/api/ligne-livraisons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ligneLivraison)))
            .andExpect(status().isBadRequest());

        // Validate the LigneLivraison in the database
        List<LigneLivraison> ligneLivraisonList = ligneLivraisonRepository.findAll();
        assertThat(ligneLivraisonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLigneLivraison() throws Exception {
        // Initialize the database
        ligneLivraisonService.save(ligneLivraison);

        int databaseSizeBeforeDelete = ligneLivraisonRepository.findAll().size();

        // Delete the ligneLivraison
        restLigneLivraisonMockMvc.perform(delete("/api/ligne-livraisons/{id}", ligneLivraison.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LigneLivraison> ligneLivraisonList = ligneLivraisonRepository.findAll();
        assertThat(ligneLivraisonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
