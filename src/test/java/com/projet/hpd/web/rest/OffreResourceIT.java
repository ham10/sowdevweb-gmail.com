package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Offre;
import com.projet.hpd.repository.OffreRepository;
import com.projet.hpd.service.OffreService;
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
 * Integration tests for the {@link OffreResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class OffreResourceIT {

    private static final LocalDate DEFAULT_LIBELLE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LIBELLE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final Double DEFAULT_TAXE = 1D;
    private static final Double UPDATED_TAXE = 2D;

    private static final String DEFAULT_NUM_MARCHE = "AAAAAAAAAA";
    private static final String UPDATED_NUM_MARCHE = "BBBBBBBBBB";

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
    private OffreRepository offreRepository;

    @Autowired
    private OffreService offreService;

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

    private MockMvc restOffreMockMvc;

    private Offre offre;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OffreResource offreResource = new OffreResource(offreService);
        this.restOffreMockMvc = MockMvcBuilders.standaloneSetup(offreResource)
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
    public static Offre createEntity(EntityManager em) {
        Offre offre = new Offre()
            .libelle(DEFAULT_LIBELLE)
            .date(DEFAULT_DATE)
            .montant(DEFAULT_MONTANT)
            .taxe(DEFAULT_TAXE)
            .numMarche(DEFAULT_NUM_MARCHE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return offre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offre createUpdatedEntity(EntityManager em) {
        Offre offre = new Offre()
            .libelle(UPDATED_LIBELLE)
            .date(UPDATED_DATE)
            .montant(UPDATED_MONTANT)
            .taxe(UPDATED_TAXE)
            .numMarche(UPDATED_NUM_MARCHE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return offre;
    }

    @BeforeEach
    public void initTest() {
        offre = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffre() throws Exception {
        int databaseSizeBeforeCreate = offreRepository.findAll().size();

        // Create the Offre
        restOffreMockMvc.perform(post("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offre)))
            .andExpect(status().isCreated());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeCreate + 1);
        Offre testOffre = offreList.get(offreList.size() - 1);
        assertThat(testOffre.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testOffre.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testOffre.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testOffre.getTaxe()).isEqualTo(DEFAULT_TAXE);
        assertThat(testOffre.getNumMarche()).isEqualTo(DEFAULT_NUM_MARCHE);
        assertThat(testOffre.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testOffre.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testOffre.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testOffre.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testOffre.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testOffre.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createOffreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offreRepository.findAll().size();

        // Create the Offre with an existing ID
        offre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOffreMockMvc.perform(post("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offre)))
            .andExpect(status().isBadRequest());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOffres() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList
        restOffreMockMvc.perform(get("/api/offres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offre.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].taxe").value(hasItem(DEFAULT_TAXE.doubleValue())))
            .andExpect(jsonPath("$.[*].numMarche").value(hasItem(DEFAULT_NUM_MARCHE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get the offre
        restOffreMockMvc.perform(get("/api/offres/{id}", offre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offre.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.taxe").value(DEFAULT_TAXE.doubleValue()))
            .andExpect(jsonPath("$.numMarche").value(DEFAULT_NUM_MARCHE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOffre() throws Exception {
        // Get the offre
        restOffreMockMvc.perform(get("/api/offres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffre() throws Exception {
        // Initialize the database
        offreService.save(offre);

        int databaseSizeBeforeUpdate = offreRepository.findAll().size();

        // Update the offre
        Offre updatedOffre = offreRepository.findById(offre.getId()).get();
        // Disconnect from session so that the updates on updatedOffre are not directly saved in db
        em.detach(updatedOffre);
        updatedOffre
            .libelle(UPDATED_LIBELLE)
            .date(UPDATED_DATE)
            .montant(UPDATED_MONTANT)
            .taxe(UPDATED_TAXE)
            .numMarche(UPDATED_NUM_MARCHE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restOffreMockMvc.perform(put("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOffre)))
            .andExpect(status().isOk());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
        Offre testOffre = offreList.get(offreList.size() - 1);
        assertThat(testOffre.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testOffre.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testOffre.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testOffre.getTaxe()).isEqualTo(UPDATED_TAXE);
        assertThat(testOffre.getNumMarche()).isEqualTo(UPDATED_NUM_MARCHE);
        assertThat(testOffre.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testOffre.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testOffre.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testOffre.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testOffre.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testOffre.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingOffre() throws Exception {
        int databaseSizeBeforeUpdate = offreRepository.findAll().size();

        // Create the Offre

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOffreMockMvc.perform(put("/api/offres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offre)))
            .andExpect(status().isBadRequest());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOffre() throws Exception {
        // Initialize the database
        offreService.save(offre);

        int databaseSizeBeforeDelete = offreRepository.findAll().size();

        // Delete the offre
        restOffreMockMvc.perform(delete("/api/offres/{id}", offre.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
