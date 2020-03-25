package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Jour;
import com.projet.hpd.repository.JourRepository;
import com.projet.hpd.service.JourService;
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
 * Integration tests for the {@link JourResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class JourResourceIT {

    private static final String DEFAULT_LIBELLE_JOUR = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_JOUR = "BBBBBBBBBB";

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
    private JourRepository jourRepository;

    @Autowired
    private JourService jourService;

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

    private MockMvc restJourMockMvc;

    private Jour jour;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JourResource jourResource = new JourResource(jourService);
        this.restJourMockMvc = MockMvcBuilders.standaloneSetup(jourResource)
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
    public static Jour createEntity(EntityManager em) {
        Jour jour = new Jour()
            .libelleJour(DEFAULT_LIBELLE_JOUR)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return jour;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jour createUpdatedEntity(EntityManager em) {
        Jour jour = new Jour()
            .libelleJour(UPDATED_LIBELLE_JOUR)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return jour;
    }

    @BeforeEach
    public void initTest() {
        jour = createEntity(em);
    }

    @Test
    @Transactional
    public void createJour() throws Exception {
        int databaseSizeBeforeCreate = jourRepository.findAll().size();

        // Create the Jour
        restJourMockMvc.perform(post("/api/jours")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jour)))
            .andExpect(status().isCreated());

        // Validate the Jour in the database
        List<Jour> jourList = jourRepository.findAll();
        assertThat(jourList).hasSize(databaseSizeBeforeCreate + 1);
        Jour testJour = jourList.get(jourList.size() - 1);
        assertThat(testJour.getLibelleJour()).isEqualTo(DEFAULT_LIBELLE_JOUR);
        assertThat(testJour.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testJour.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testJour.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testJour.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testJour.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createJourWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jourRepository.findAll().size();

        // Create the Jour with an existing ID
        jour.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourMockMvc.perform(post("/api/jours")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jour)))
            .andExpect(status().isBadRequest());

        // Validate the Jour in the database
        List<Jour> jourList = jourRepository.findAll();
        assertThat(jourList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJours() throws Exception {
        // Initialize the database
        jourRepository.saveAndFlush(jour);

        // Get all the jourList
        restJourMockMvc.perform(get("/api/jours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jour.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleJour").value(hasItem(DEFAULT_LIBELLE_JOUR)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getJour() throws Exception {
        // Initialize the database
        jourRepository.saveAndFlush(jour);

        // Get the jour
        restJourMockMvc.perform(get("/api/jours/{id}", jour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jour.getId().intValue()))
            .andExpect(jsonPath("$.libelleJour").value(DEFAULT_LIBELLE_JOUR))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJour() throws Exception {
        // Get the jour
        restJourMockMvc.perform(get("/api/jours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJour() throws Exception {
        // Initialize the database
        jourService.save(jour);

        int databaseSizeBeforeUpdate = jourRepository.findAll().size();

        // Update the jour
        Jour updatedJour = jourRepository.findById(jour.getId()).get();
        // Disconnect from session so that the updates on updatedJour are not directly saved in db
        em.detach(updatedJour);
        updatedJour
            .libelleJour(UPDATED_LIBELLE_JOUR)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restJourMockMvc.perform(put("/api/jours")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJour)))
            .andExpect(status().isOk());

        // Validate the Jour in the database
        List<Jour> jourList = jourRepository.findAll();
        assertThat(jourList).hasSize(databaseSizeBeforeUpdate);
        Jour testJour = jourList.get(jourList.size() - 1);
        assertThat(testJour.getLibelleJour()).isEqualTo(UPDATED_LIBELLE_JOUR);
        assertThat(testJour.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testJour.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testJour.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testJour.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testJour.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingJour() throws Exception {
        int databaseSizeBeforeUpdate = jourRepository.findAll().size();

        // Create the Jour

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourMockMvc.perform(put("/api/jours")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(jour)))
            .andExpect(status().isBadRequest());

        // Validate the Jour in the database
        List<Jour> jourList = jourRepository.findAll();
        assertThat(jourList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJour() throws Exception {
        // Initialize the database
        jourService.save(jour);

        int databaseSizeBeforeDelete = jourRepository.findAll().size();

        // Delete the jour
        restJourMockMvc.perform(delete("/api/jours/{id}", jour.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Jour> jourList = jourRepository.findAll();
        assertThat(jourList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
