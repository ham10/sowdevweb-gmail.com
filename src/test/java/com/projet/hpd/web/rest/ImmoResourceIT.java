package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Immo;
import com.projet.hpd.repository.ImmoRepository;
import com.projet.hpd.service.ImmoService;
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
 * Integration tests for the {@link ImmoResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class ImmoResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Double DEFAULT_VALEUR_AQUISITION = 1D;
    private static final Double UPDATED_VALEUR_AQUISITION = 2D;

    private static final LocalDate DEFAULT_DATE_AQUISITION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_AQUISITION = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_VALEUR_NET_COMPTABLE = 1D;
    private static final Double UPDATED_VALEUR_NET_COMPTABLE = 2D;

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final Integer DEFAULT_DUREE = 1;
    private static final Integer UPDATED_DUREE = 2;

    private static final Integer DEFAULT_NBRE_ECHEANCE = 1;
    private static final Integer UPDATED_NBRE_ECHEANCE = 2;

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
    private ImmoRepository immoRepository;

    @Autowired
    private ImmoService immoService;

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

    private MockMvc restImmoMockMvc;

    private Immo immo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImmoResource immoResource = new ImmoResource(immoService);
        this.restImmoMockMvc = MockMvcBuilders.standaloneSetup(immoResource)
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
    public static Immo createEntity(EntityManager em) {
        Immo immo = new Immo()
            .libelle(DEFAULT_LIBELLE)
            .valeurAquisition(DEFAULT_VALEUR_AQUISITION)
            .dateAquisition(DEFAULT_DATE_AQUISITION)
            .valeurNetComptable(DEFAULT_VALEUR_NET_COMPTABLE)
            .montant(DEFAULT_MONTANT)
            .duree(DEFAULT_DUREE)
            .nbreEcheance(DEFAULT_NBRE_ECHEANCE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return immo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Immo createUpdatedEntity(EntityManager em) {
        Immo immo = new Immo()
            .libelle(UPDATED_LIBELLE)
            .valeurAquisition(UPDATED_VALEUR_AQUISITION)
            .dateAquisition(UPDATED_DATE_AQUISITION)
            .valeurNetComptable(UPDATED_VALEUR_NET_COMPTABLE)
            .montant(UPDATED_MONTANT)
            .duree(UPDATED_DUREE)
            .nbreEcheance(UPDATED_NBRE_ECHEANCE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return immo;
    }

    @BeforeEach
    public void initTest() {
        immo = createEntity(em);
    }

    @Test
    @Transactional
    public void createImmo() throws Exception {
        int databaseSizeBeforeCreate = immoRepository.findAll().size();

        // Create the Immo
        restImmoMockMvc.perform(post("/api/immos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(immo)))
            .andExpect(status().isCreated());

        // Validate the Immo in the database
        List<Immo> immoList = immoRepository.findAll();
        assertThat(immoList).hasSize(databaseSizeBeforeCreate + 1);
        Immo testImmo = immoList.get(immoList.size() - 1);
        assertThat(testImmo.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testImmo.getValeurAquisition()).isEqualTo(DEFAULT_VALEUR_AQUISITION);
        assertThat(testImmo.getDateAquisition()).isEqualTo(DEFAULT_DATE_AQUISITION);
        assertThat(testImmo.getValeurNetComptable()).isEqualTo(DEFAULT_VALEUR_NET_COMPTABLE);
        assertThat(testImmo.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testImmo.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testImmo.getNbreEcheance()).isEqualTo(DEFAULT_NBRE_ECHEANCE);
        assertThat(testImmo.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testImmo.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testImmo.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testImmo.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testImmo.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testImmo.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createImmoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = immoRepository.findAll().size();

        // Create the Immo with an existing ID
        immo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImmoMockMvc.perform(post("/api/immos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(immo)))
            .andExpect(status().isBadRequest());

        // Validate the Immo in the database
        List<Immo> immoList = immoRepository.findAll();
        assertThat(immoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllImmos() throws Exception {
        // Initialize the database
        immoRepository.saveAndFlush(immo);

        // Get all the immoList
        restImmoMockMvc.perform(get("/api/immos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(immo.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].valeurAquisition").value(hasItem(DEFAULT_VALEUR_AQUISITION.doubleValue())))
            .andExpect(jsonPath("$.[*].dateAquisition").value(hasItem(DEFAULT_DATE_AQUISITION.toString())))
            .andExpect(jsonPath("$.[*].valeurNetComptable").value(hasItem(DEFAULT_VALEUR_NET_COMPTABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].nbreEcheance").value(hasItem(DEFAULT_NBRE_ECHEANCE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getImmo() throws Exception {
        // Initialize the database
        immoRepository.saveAndFlush(immo);

        // Get the immo
        restImmoMockMvc.perform(get("/api/immos/{id}", immo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(immo.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.valeurAquisition").value(DEFAULT_VALEUR_AQUISITION.doubleValue()))
            .andExpect(jsonPath("$.dateAquisition").value(DEFAULT_DATE_AQUISITION.toString()))
            .andExpect(jsonPath("$.valeurNetComptable").value(DEFAULT_VALEUR_NET_COMPTABLE.doubleValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE))
            .andExpect(jsonPath("$.nbreEcheance").value(DEFAULT_NBRE_ECHEANCE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingImmo() throws Exception {
        // Get the immo
        restImmoMockMvc.perform(get("/api/immos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImmo() throws Exception {
        // Initialize the database
        immoService.save(immo);

        int databaseSizeBeforeUpdate = immoRepository.findAll().size();

        // Update the immo
        Immo updatedImmo = immoRepository.findById(immo.getId()).get();
        // Disconnect from session so that the updates on updatedImmo are not directly saved in db
        em.detach(updatedImmo);
        updatedImmo
            .libelle(UPDATED_LIBELLE)
            .valeurAquisition(UPDATED_VALEUR_AQUISITION)
            .dateAquisition(UPDATED_DATE_AQUISITION)
            .valeurNetComptable(UPDATED_VALEUR_NET_COMPTABLE)
            .montant(UPDATED_MONTANT)
            .duree(UPDATED_DUREE)
            .nbreEcheance(UPDATED_NBRE_ECHEANCE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restImmoMockMvc.perform(put("/api/immos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedImmo)))
            .andExpect(status().isOk());

        // Validate the Immo in the database
        List<Immo> immoList = immoRepository.findAll();
        assertThat(immoList).hasSize(databaseSizeBeforeUpdate);
        Immo testImmo = immoList.get(immoList.size() - 1);
        assertThat(testImmo.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testImmo.getValeurAquisition()).isEqualTo(UPDATED_VALEUR_AQUISITION);
        assertThat(testImmo.getDateAquisition()).isEqualTo(UPDATED_DATE_AQUISITION);
        assertThat(testImmo.getValeurNetComptable()).isEqualTo(UPDATED_VALEUR_NET_COMPTABLE);
        assertThat(testImmo.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testImmo.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testImmo.getNbreEcheance()).isEqualTo(UPDATED_NBRE_ECHEANCE);
        assertThat(testImmo.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testImmo.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testImmo.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testImmo.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testImmo.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testImmo.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingImmo() throws Exception {
        int databaseSizeBeforeUpdate = immoRepository.findAll().size();

        // Create the Immo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImmoMockMvc.perform(put("/api/immos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(immo)))
            .andExpect(status().isBadRequest());

        // Validate the Immo in the database
        List<Immo> immoList = immoRepository.findAll();
        assertThat(immoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImmo() throws Exception {
        // Initialize the database
        immoService.save(immo);

        int databaseSizeBeforeDelete = immoRepository.findAll().size();

        // Delete the immo
        restImmoMockMvc.perform(delete("/api/immos/{id}", immo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Immo> immoList = immoRepository.findAll();
        assertThat(immoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
