package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Inventaire;
import com.projet.hpd.repository.InventaireRepository;
import com.projet.hpd.service.InventaireService;
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
import java.util.List;

import static com.projet.hpd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InventaireResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class InventaireResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITE_ENTRANT = 1D;
    private static final Double UPDATED_QUANTITE_ENTRANT = 2D;

    private static final Double DEFAULT_QUANTITE_INITIALE = 1D;
    private static final Double UPDATED_QUANTITE_INITIALE = 2D;

    private static final Double DEFAULT_QUANTITE_SORTANT = 1D;
    private static final Double UPDATED_QUANTITE_SORTANT = 2D;

    private static final Integer DEFAULT_NOMBRE_SORTANT = 1;
    private static final Integer UPDATED_NOMBRE_SORTANT = 2;

    private static final Integer DEFAULT_NOMBRE_LIVRAISON = 1;
    private static final Integer UPDATED_NOMBRE_LIVRAISON = 2;

    private static final Integer DEFAULT_NOMBRE_RETOUR = 1;
    private static final Integer UPDATED_NOMBRE_RETOUR = 2;

    @Autowired
    private InventaireRepository inventaireRepository;

    @Autowired
    private InventaireService inventaireService;

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

    private MockMvc restInventaireMockMvc;

    private Inventaire inventaire;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventaireResource inventaireResource = new InventaireResource(inventaireService);
        this.restInventaireMockMvc = MockMvcBuilders.standaloneSetup(inventaireResource)
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
    public static Inventaire createEntity(EntityManager em) {
        Inventaire inventaire = new Inventaire()
            .code(DEFAULT_CODE)
            .date(DEFAULT_DATE)
            .quantiteEntrant(DEFAULT_QUANTITE_ENTRANT)
            .quantiteInitiale(DEFAULT_QUANTITE_INITIALE)
            .quantiteSortant(DEFAULT_QUANTITE_SORTANT)
            .nombreSortant(DEFAULT_NOMBRE_SORTANT)
            .nombreLivraison(DEFAULT_NOMBRE_LIVRAISON)
            .nombreRetour(DEFAULT_NOMBRE_RETOUR);
        return inventaire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventaire createUpdatedEntity(EntityManager em) {
        Inventaire inventaire = new Inventaire()
            .code(UPDATED_CODE)
            .date(UPDATED_DATE)
            .quantiteEntrant(UPDATED_QUANTITE_ENTRANT)
            .quantiteInitiale(UPDATED_QUANTITE_INITIALE)
            .quantiteSortant(UPDATED_QUANTITE_SORTANT)
            .nombreSortant(UPDATED_NOMBRE_SORTANT)
            .nombreLivraison(UPDATED_NOMBRE_LIVRAISON)
            .nombreRetour(UPDATED_NOMBRE_RETOUR);
        return inventaire;
    }

    @BeforeEach
    public void initTest() {
        inventaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventaire() throws Exception {
        int databaseSizeBeforeCreate = inventaireRepository.findAll().size();

        // Create the Inventaire
        restInventaireMockMvc.perform(post("/api/inventaires")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventaire)))
            .andExpect(status().isCreated());

        // Validate the Inventaire in the database
        List<Inventaire> inventaireList = inventaireRepository.findAll();
        assertThat(inventaireList).hasSize(databaseSizeBeforeCreate + 1);
        Inventaire testInventaire = inventaireList.get(inventaireList.size() - 1);
        assertThat(testInventaire.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testInventaire.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testInventaire.getQuantiteEntrant()).isEqualTo(DEFAULT_QUANTITE_ENTRANT);
        assertThat(testInventaire.getQuantiteInitiale()).isEqualTo(DEFAULT_QUANTITE_INITIALE);
        assertThat(testInventaire.getQuantiteSortant()).isEqualTo(DEFAULT_QUANTITE_SORTANT);
        assertThat(testInventaire.getNombreSortant()).isEqualTo(DEFAULT_NOMBRE_SORTANT);
        assertThat(testInventaire.getNombreLivraison()).isEqualTo(DEFAULT_NOMBRE_LIVRAISON);
        assertThat(testInventaire.getNombreRetour()).isEqualTo(DEFAULT_NOMBRE_RETOUR);
    }

    @Test
    @Transactional
    public void createInventaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventaireRepository.findAll().size();

        // Create the Inventaire with an existing ID
        inventaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventaireMockMvc.perform(post("/api/inventaires")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventaire)))
            .andExpect(status().isBadRequest());

        // Validate the Inventaire in the database
        List<Inventaire> inventaireList = inventaireRepository.findAll();
        assertThat(inventaireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventaires() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList
        restInventaireMockMvc.perform(get("/api/inventaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE)))
            .andExpect(jsonPath("$.[*].quantiteEntrant").value(hasItem(DEFAULT_QUANTITE_ENTRANT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteInitiale").value(hasItem(DEFAULT_QUANTITE_INITIALE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantiteSortant").value(hasItem(DEFAULT_QUANTITE_SORTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].nombreSortant").value(hasItem(DEFAULT_NOMBRE_SORTANT)))
            .andExpect(jsonPath("$.[*].nombreLivraison").value(hasItem(DEFAULT_NOMBRE_LIVRAISON)))
            .andExpect(jsonPath("$.[*].nombreRetour").value(hasItem(DEFAULT_NOMBRE_RETOUR)));
    }
    
    @Test
    @Transactional
    public void getInventaire() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get the inventaire
        restInventaireMockMvc.perform(get("/api/inventaires/{id}", inventaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventaire.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE))
            .andExpect(jsonPath("$.quantiteEntrant").value(DEFAULT_QUANTITE_ENTRANT.doubleValue()))
            .andExpect(jsonPath("$.quantiteInitiale").value(DEFAULT_QUANTITE_INITIALE.doubleValue()))
            .andExpect(jsonPath("$.quantiteSortant").value(DEFAULT_QUANTITE_SORTANT.doubleValue()))
            .andExpect(jsonPath("$.nombreSortant").value(DEFAULT_NOMBRE_SORTANT))
            .andExpect(jsonPath("$.nombreLivraison").value(DEFAULT_NOMBRE_LIVRAISON))
            .andExpect(jsonPath("$.nombreRetour").value(DEFAULT_NOMBRE_RETOUR));
    }

    @Test
    @Transactional
    public void getNonExistingInventaire() throws Exception {
        // Get the inventaire
        restInventaireMockMvc.perform(get("/api/inventaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventaire() throws Exception {
        // Initialize the database
        inventaireService.save(inventaire);

        int databaseSizeBeforeUpdate = inventaireRepository.findAll().size();

        // Update the inventaire
        Inventaire updatedInventaire = inventaireRepository.findById(inventaire.getId()).get();
        // Disconnect from session so that the updates on updatedInventaire are not directly saved in db
        em.detach(updatedInventaire);
        updatedInventaire
            .code(UPDATED_CODE)
            .date(UPDATED_DATE)
            .quantiteEntrant(UPDATED_QUANTITE_ENTRANT)
            .quantiteInitiale(UPDATED_QUANTITE_INITIALE)
            .quantiteSortant(UPDATED_QUANTITE_SORTANT)
            .nombreSortant(UPDATED_NOMBRE_SORTANT)
            .nombreLivraison(UPDATED_NOMBRE_LIVRAISON)
            .nombreRetour(UPDATED_NOMBRE_RETOUR);

        restInventaireMockMvc.perform(put("/api/inventaires")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventaire)))
            .andExpect(status().isOk());

        // Validate the Inventaire in the database
        List<Inventaire> inventaireList = inventaireRepository.findAll();
        assertThat(inventaireList).hasSize(databaseSizeBeforeUpdate);
        Inventaire testInventaire = inventaireList.get(inventaireList.size() - 1);
        assertThat(testInventaire.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testInventaire.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testInventaire.getQuantiteEntrant()).isEqualTo(UPDATED_QUANTITE_ENTRANT);
        assertThat(testInventaire.getQuantiteInitiale()).isEqualTo(UPDATED_QUANTITE_INITIALE);
        assertThat(testInventaire.getQuantiteSortant()).isEqualTo(UPDATED_QUANTITE_SORTANT);
        assertThat(testInventaire.getNombreSortant()).isEqualTo(UPDATED_NOMBRE_SORTANT);
        assertThat(testInventaire.getNombreLivraison()).isEqualTo(UPDATED_NOMBRE_LIVRAISON);
        assertThat(testInventaire.getNombreRetour()).isEqualTo(UPDATED_NOMBRE_RETOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingInventaire() throws Exception {
        int databaseSizeBeforeUpdate = inventaireRepository.findAll().size();

        // Create the Inventaire

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventaireMockMvc.perform(put("/api/inventaires")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventaire)))
            .andExpect(status().isBadRequest());

        // Validate the Inventaire in the database
        List<Inventaire> inventaireList = inventaireRepository.findAll();
        assertThat(inventaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventaire() throws Exception {
        // Initialize the database
        inventaireService.save(inventaire);

        int databaseSizeBeforeDelete = inventaireRepository.findAll().size();

        // Delete the inventaire
        restInventaireMockMvc.perform(delete("/api/inventaires/{id}", inventaire.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inventaire> inventaireList = inventaireRepository.findAll();
        assertThat(inventaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
