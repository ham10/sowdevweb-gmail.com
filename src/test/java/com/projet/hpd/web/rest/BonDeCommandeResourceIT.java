package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.BonDeCommande;
import com.projet.hpd.repository.BonDeCommandeRepository;
import com.projet.hpd.service.BonDeCommandeService;
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
 * Integration tests for the {@link BonDeCommandeResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class BonDeCommandeResourceIT {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX_TOTAL = 1D;
    private static final Double UPDATED_PRIX_TOTAL = 2D;

    private static final LocalDate DEFAULT_DATE_COMM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_COMM = LocalDate.now(ZoneId.systemDefault());

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
    private BonDeCommandeRepository bonDeCommandeRepository;

    @Autowired
    private BonDeCommandeService bonDeCommandeService;

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

    private MockMvc restBonDeCommandeMockMvc;

    private BonDeCommande bonDeCommande;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BonDeCommandeResource bonDeCommandeResource = new BonDeCommandeResource(bonDeCommandeService);
        this.restBonDeCommandeMockMvc = MockMvcBuilders.standaloneSetup(bonDeCommandeResource)
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
    public static BonDeCommande createEntity(EntityManager em) {
        BonDeCommande bonDeCommande = new BonDeCommande()
            .numero(DEFAULT_NUMERO)
            .libelle(DEFAULT_LIBELLE)
            .prixTotal(DEFAULT_PRIX_TOTAL)
            .dateComm(DEFAULT_DATE_COMM)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return bonDeCommande;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BonDeCommande createUpdatedEntity(EntityManager em) {
        BonDeCommande bonDeCommande = new BonDeCommande()
            .numero(UPDATED_NUMERO)
            .libelle(UPDATED_LIBELLE)
            .prixTotal(UPDATED_PRIX_TOTAL)
            .dateComm(UPDATED_DATE_COMM)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return bonDeCommande;
    }

    @BeforeEach
    public void initTest() {
        bonDeCommande = createEntity(em);
    }

    @Test
    @Transactional
    public void createBonDeCommande() throws Exception {
        int databaseSizeBeforeCreate = bonDeCommandeRepository.findAll().size();

        // Create the BonDeCommande
        restBonDeCommandeMockMvc.perform(post("/api/bon-de-commandes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonDeCommande)))
            .andExpect(status().isCreated());

        // Validate the BonDeCommande in the database
        List<BonDeCommande> bonDeCommandeList = bonDeCommandeRepository.findAll();
        assertThat(bonDeCommandeList).hasSize(databaseSizeBeforeCreate + 1);
        BonDeCommande testBonDeCommande = bonDeCommandeList.get(bonDeCommandeList.size() - 1);
        assertThat(testBonDeCommande.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testBonDeCommande.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testBonDeCommande.getPrixTotal()).isEqualTo(DEFAULT_PRIX_TOTAL);
        assertThat(testBonDeCommande.getDateComm()).isEqualTo(DEFAULT_DATE_COMM);
        assertThat(testBonDeCommande.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testBonDeCommande.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testBonDeCommande.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testBonDeCommande.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testBonDeCommande.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testBonDeCommande.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createBonDeCommandeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bonDeCommandeRepository.findAll().size();

        // Create the BonDeCommande with an existing ID
        bonDeCommande.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonDeCommandeMockMvc.perform(post("/api/bon-de-commandes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonDeCommande)))
            .andExpect(status().isBadRequest());

        // Validate the BonDeCommande in the database
        List<BonDeCommande> bonDeCommandeList = bonDeCommandeRepository.findAll();
        assertThat(bonDeCommandeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBonDeCommandes() throws Exception {
        // Initialize the database
        bonDeCommandeRepository.saveAndFlush(bonDeCommande);

        // Get all the bonDeCommandeList
        restBonDeCommandeMockMvc.perform(get("/api/bon-de-commandes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonDeCommande.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].prixTotal").value(hasItem(DEFAULT_PRIX_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].dateComm").value(hasItem(DEFAULT_DATE_COMM.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getBonDeCommande() throws Exception {
        // Initialize the database
        bonDeCommandeRepository.saveAndFlush(bonDeCommande);

        // Get the bonDeCommande
        restBonDeCommandeMockMvc.perform(get("/api/bon-de-commandes/{id}", bonDeCommande.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bonDeCommande.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.prixTotal").value(DEFAULT_PRIX_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.dateComm").value(DEFAULT_DATE_COMM.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBonDeCommande() throws Exception {
        // Get the bonDeCommande
        restBonDeCommandeMockMvc.perform(get("/api/bon-de-commandes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBonDeCommande() throws Exception {
        // Initialize the database
        bonDeCommandeService.save(bonDeCommande);

        int databaseSizeBeforeUpdate = bonDeCommandeRepository.findAll().size();

        // Update the bonDeCommande
        BonDeCommande updatedBonDeCommande = bonDeCommandeRepository.findById(bonDeCommande.getId()).get();
        // Disconnect from session so that the updates on updatedBonDeCommande are not directly saved in db
        em.detach(updatedBonDeCommande);
        updatedBonDeCommande
            .numero(UPDATED_NUMERO)
            .libelle(UPDATED_LIBELLE)
            .prixTotal(UPDATED_PRIX_TOTAL)
            .dateComm(UPDATED_DATE_COMM)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restBonDeCommandeMockMvc.perform(put("/api/bon-de-commandes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBonDeCommande)))
            .andExpect(status().isOk());

        // Validate the BonDeCommande in the database
        List<BonDeCommande> bonDeCommandeList = bonDeCommandeRepository.findAll();
        assertThat(bonDeCommandeList).hasSize(databaseSizeBeforeUpdate);
        BonDeCommande testBonDeCommande = bonDeCommandeList.get(bonDeCommandeList.size() - 1);
        assertThat(testBonDeCommande.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testBonDeCommande.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testBonDeCommande.getPrixTotal()).isEqualTo(UPDATED_PRIX_TOTAL);
        assertThat(testBonDeCommande.getDateComm()).isEqualTo(UPDATED_DATE_COMM);
        assertThat(testBonDeCommande.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testBonDeCommande.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testBonDeCommande.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testBonDeCommande.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testBonDeCommande.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testBonDeCommande.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingBonDeCommande() throws Exception {
        int databaseSizeBeforeUpdate = bonDeCommandeRepository.findAll().size();

        // Create the BonDeCommande

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBonDeCommandeMockMvc.perform(put("/api/bon-de-commandes")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonDeCommande)))
            .andExpect(status().isBadRequest());

        // Validate the BonDeCommande in the database
        List<BonDeCommande> bonDeCommandeList = bonDeCommandeRepository.findAll();
        assertThat(bonDeCommandeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBonDeCommande() throws Exception {
        // Initialize the database
        bonDeCommandeService.save(bonDeCommande);

        int databaseSizeBeforeDelete = bonDeCommandeRepository.findAll().size();

        // Delete the bonDeCommande
        restBonDeCommandeMockMvc.perform(delete("/api/bon-de-commandes/{id}", bonDeCommande.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BonDeCommande> bonDeCommandeList = bonDeCommandeRepository.findAll();
        assertThat(bonDeCommandeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
