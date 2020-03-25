package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.LigneCommande;
import com.projet.hpd.repository.LigneCommandeRepository;
import com.projet.hpd.service.LigneCommandeService;
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
 * Integration tests for the {@link LigneCommandeResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class LigneCommandeResourceIT {

    private static final String DEFAULT_PRODUIT = "AAAAAAAAAA";
    private static final String UPDATED_PRODUIT = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITE = 1;
    private static final Integer UPDATED_QUANTITE = 2;

    private static final Double DEFAULT_PRIX_UNITAIRE = 1D;
    private static final Double UPDATED_PRIX_UNITAIRE = 2D;

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
    private LigneCommandeRepository ligneCommandeRepository;

    @Autowired
    private LigneCommandeService ligneCommandeService;

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

    private MockMvc restLigneCommandeMockMvc;

    private LigneCommande ligneCommande;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LigneCommandeResource ligneCommandeResource = new LigneCommandeResource(ligneCommandeService);
        this.restLigneCommandeMockMvc = MockMvcBuilders.standaloneSetup(ligneCommandeResource)
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
    public static LigneCommande createEntity(EntityManager em) {
        LigneCommande ligneCommande = new LigneCommande()
            .produit(DEFAULT_PRODUIT)
            .quantite(DEFAULT_QUANTITE)
            .prixUnitaire(DEFAULT_PRIX_UNITAIRE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return ligneCommande;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LigneCommande createUpdatedEntity(EntityManager em) {
        LigneCommande ligneCommande = new LigneCommande()
            .produit(UPDATED_PRODUIT)
            .quantite(UPDATED_QUANTITE)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return ligneCommande;
    }

    @BeforeEach
    public void initTest() {
        ligneCommande = createEntity(em);
    }

    @Test
    @Transactional
    public void createLigneCommande() throws Exception {
        int databaseSizeBeforeCreate = ligneCommandeRepository.findAll().size();

        // Create the LigneCommande
        restLigneCommandeMockMvc.perform(post("/api/ligne-commandes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ligneCommande)))
            .andExpect(status().isCreated());

        // Validate the LigneCommande in the database
        List<LigneCommande> ligneCommandeList = ligneCommandeRepository.findAll();
        assertThat(ligneCommandeList).hasSize(databaseSizeBeforeCreate + 1);
        LigneCommande testLigneCommande = ligneCommandeList.get(ligneCommandeList.size() - 1);
        assertThat(testLigneCommande.getProduit()).isEqualTo(DEFAULT_PRODUIT);
        assertThat(testLigneCommande.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testLigneCommande.getPrixUnitaire()).isEqualTo(DEFAULT_PRIX_UNITAIRE);
        assertThat(testLigneCommande.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testLigneCommande.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testLigneCommande.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testLigneCommande.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testLigneCommande.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createLigneCommandeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ligneCommandeRepository.findAll().size();

        // Create the LigneCommande with an existing ID
        ligneCommande.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLigneCommandeMockMvc.perform(post("/api/ligne-commandes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ligneCommande)))
            .andExpect(status().isBadRequest());

        // Validate the LigneCommande in the database
        List<LigneCommande> ligneCommandeList = ligneCommandeRepository.findAll();
        assertThat(ligneCommandeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLigneCommandes() throws Exception {
        // Initialize the database
        ligneCommandeRepository.saveAndFlush(ligneCommande);

        // Get all the ligneCommandeList
        restLigneCommandeMockMvc.perform(get("/api/ligne-commandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ligneCommande.getId().intValue())))
            .andExpect(jsonPath("$.[*].produit").value(hasItem(DEFAULT_PRODUIT)))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE)))
            .andExpect(jsonPath("$.[*].prixUnitaire").value(hasItem(DEFAULT_PRIX_UNITAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getLigneCommande() throws Exception {
        // Initialize the database
        ligneCommandeRepository.saveAndFlush(ligneCommande);

        // Get the ligneCommande
        restLigneCommandeMockMvc.perform(get("/api/ligne-commandes/{id}", ligneCommande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ligneCommande.getId().intValue()))
            .andExpect(jsonPath("$.produit").value(DEFAULT_PRODUIT))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE))
            .andExpect(jsonPath("$.prixUnitaire").value(DEFAULT_PRIX_UNITAIRE.doubleValue()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLigneCommande() throws Exception {
        // Get the ligneCommande
        restLigneCommandeMockMvc.perform(get("/api/ligne-commandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLigneCommande() throws Exception {
        // Initialize the database
        ligneCommandeService.save(ligneCommande);

        int databaseSizeBeforeUpdate = ligneCommandeRepository.findAll().size();

        // Update the ligneCommande
        LigneCommande updatedLigneCommande = ligneCommandeRepository.findById(ligneCommande.getId()).get();
        // Disconnect from session so that the updates on updatedLigneCommande are not directly saved in db
        em.detach(updatedLigneCommande);
        updatedLigneCommande
            .produit(UPDATED_PRODUIT)
            .quantite(UPDATED_QUANTITE)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restLigneCommandeMockMvc.perform(put("/api/ligne-commandes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLigneCommande)))
            .andExpect(status().isOk());

        // Validate the LigneCommande in the database
        List<LigneCommande> ligneCommandeList = ligneCommandeRepository.findAll();
        assertThat(ligneCommandeList).hasSize(databaseSizeBeforeUpdate);
        LigneCommande testLigneCommande = ligneCommandeList.get(ligneCommandeList.size() - 1);
        assertThat(testLigneCommande.getProduit()).isEqualTo(UPDATED_PRODUIT);
        assertThat(testLigneCommande.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testLigneCommande.getPrixUnitaire()).isEqualTo(UPDATED_PRIX_UNITAIRE);
        assertThat(testLigneCommande.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testLigneCommande.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testLigneCommande.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testLigneCommande.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testLigneCommande.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingLigneCommande() throws Exception {
        int databaseSizeBeforeUpdate = ligneCommandeRepository.findAll().size();

        // Create the LigneCommande

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLigneCommandeMockMvc.perform(put("/api/ligne-commandes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ligneCommande)))
            .andExpect(status().isBadRequest());

        // Validate the LigneCommande in the database
        List<LigneCommande> ligneCommandeList = ligneCommandeRepository.findAll();
        assertThat(ligneCommandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLigneCommande() throws Exception {
        // Initialize the database
        ligneCommandeService.save(ligneCommande);

        int databaseSizeBeforeDelete = ligneCommandeRepository.findAll().size();

        // Delete the ligneCommande
        restLigneCommandeMockMvc.perform(delete("/api/ligne-commandes/{id}", ligneCommande.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LigneCommande> ligneCommandeList = ligneCommandeRepository.findAll();
        assertThat(ligneCommandeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
