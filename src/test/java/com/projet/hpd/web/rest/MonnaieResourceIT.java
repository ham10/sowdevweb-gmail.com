package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Monnaie;
import com.projet.hpd.repository.MonnaieRepository;
import com.projet.hpd.service.MonnaieService;
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
 * Integration tests for the {@link MonnaieResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class MonnaieResourceIT {

    private static final String DEFAULT_LIBELLE_MONNAIE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_MONNAIE = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT_MONNAIE = 1D;
    private static final Double UPDATED_MONTANT_MONNAIE = 2D;

    private static final String DEFAULT_NATURE_MONNAIE = "AAAAAAAAAA";
    private static final String UPDATED_NATURE_MONNAIE = "BBBBBBBBBB";

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
    private MonnaieRepository monnaieRepository;

    @Autowired
    private MonnaieService monnaieService;

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

    private MockMvc restMonnaieMockMvc;

    private Monnaie monnaie;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MonnaieResource monnaieResource = new MonnaieResource(monnaieService);
        this.restMonnaieMockMvc = MockMvcBuilders.standaloneSetup(monnaieResource)
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
    public static Monnaie createEntity(EntityManager em) {
        Monnaie monnaie = new Monnaie()
            .libelleMonnaie(DEFAULT_LIBELLE_MONNAIE)
            .montantMonnaie(DEFAULT_MONTANT_MONNAIE)
            .natureMonnaie(DEFAULT_NATURE_MONNAIE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return monnaie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Monnaie createUpdatedEntity(EntityManager em) {
        Monnaie monnaie = new Monnaie()
            .libelleMonnaie(UPDATED_LIBELLE_MONNAIE)
            .montantMonnaie(UPDATED_MONTANT_MONNAIE)
            .natureMonnaie(UPDATED_NATURE_MONNAIE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return monnaie;
    }

    @BeforeEach
    public void initTest() {
        monnaie = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonnaie() throws Exception {
        int databaseSizeBeforeCreate = monnaieRepository.findAll().size();

        // Create the Monnaie
        restMonnaieMockMvc.perform(post("/api/monnaies")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monnaie)))
            .andExpect(status().isCreated());

        // Validate the Monnaie in the database
        List<Monnaie> monnaieList = monnaieRepository.findAll();
        assertThat(monnaieList).hasSize(databaseSizeBeforeCreate + 1);
        Monnaie testMonnaie = monnaieList.get(monnaieList.size() - 1);
        assertThat(testMonnaie.getLibelleMonnaie()).isEqualTo(DEFAULT_LIBELLE_MONNAIE);
        assertThat(testMonnaie.getMontantMonnaie()).isEqualTo(DEFAULT_MONTANT_MONNAIE);
        assertThat(testMonnaie.getNatureMonnaie()).isEqualTo(DEFAULT_NATURE_MONNAIE);
        assertThat(testMonnaie.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testMonnaie.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testMonnaie.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testMonnaie.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testMonnaie.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createMonnaieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monnaieRepository.findAll().size();

        // Create the Monnaie with an existing ID
        monnaie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonnaieMockMvc.perform(post("/api/monnaies")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monnaie)))
            .andExpect(status().isBadRequest());

        // Validate the Monnaie in the database
        List<Monnaie> monnaieList = monnaieRepository.findAll();
        assertThat(monnaieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMonnaies() throws Exception {
        // Initialize the database
        monnaieRepository.saveAndFlush(monnaie);

        // Get all the monnaieList
        restMonnaieMockMvc.perform(get("/api/monnaies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monnaie.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleMonnaie").value(hasItem(DEFAULT_LIBELLE_MONNAIE)))
            .andExpect(jsonPath("$.[*].montantMonnaie").value(hasItem(DEFAULT_MONTANT_MONNAIE.doubleValue())))
            .andExpect(jsonPath("$.[*].natureMonnaie").value(hasItem(DEFAULT_NATURE_MONNAIE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getMonnaie() throws Exception {
        // Initialize the database
        monnaieRepository.saveAndFlush(monnaie);

        // Get the monnaie
        restMonnaieMockMvc.perform(get("/api/monnaies/{id}", monnaie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(monnaie.getId().intValue()))
            .andExpect(jsonPath("$.libelleMonnaie").value(DEFAULT_LIBELLE_MONNAIE))
            .andExpect(jsonPath("$.montantMonnaie").value(DEFAULT_MONTANT_MONNAIE.doubleValue()))
            .andExpect(jsonPath("$.natureMonnaie").value(DEFAULT_NATURE_MONNAIE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMonnaie() throws Exception {
        // Get the monnaie
        restMonnaieMockMvc.perform(get("/api/monnaies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonnaie() throws Exception {
        // Initialize the database
        monnaieService.save(monnaie);

        int databaseSizeBeforeUpdate = monnaieRepository.findAll().size();

        // Update the monnaie
        Monnaie updatedMonnaie = monnaieRepository.findById(monnaie.getId()).get();
        // Disconnect from session so that the updates on updatedMonnaie are not directly saved in db
        em.detach(updatedMonnaie);
        updatedMonnaie
            .libelleMonnaie(UPDATED_LIBELLE_MONNAIE)
            .montantMonnaie(UPDATED_MONTANT_MONNAIE)
            .natureMonnaie(UPDATED_NATURE_MONNAIE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restMonnaieMockMvc.perform(put("/api/monnaies")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMonnaie)))
            .andExpect(status().isOk());

        // Validate the Monnaie in the database
        List<Monnaie> monnaieList = monnaieRepository.findAll();
        assertThat(monnaieList).hasSize(databaseSizeBeforeUpdate);
        Monnaie testMonnaie = monnaieList.get(monnaieList.size() - 1);
        assertThat(testMonnaie.getLibelleMonnaie()).isEqualTo(UPDATED_LIBELLE_MONNAIE);
        assertThat(testMonnaie.getMontantMonnaie()).isEqualTo(UPDATED_MONTANT_MONNAIE);
        assertThat(testMonnaie.getNatureMonnaie()).isEqualTo(UPDATED_NATURE_MONNAIE);
        assertThat(testMonnaie.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testMonnaie.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testMonnaie.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testMonnaie.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testMonnaie.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingMonnaie() throws Exception {
        int databaseSizeBeforeUpdate = monnaieRepository.findAll().size();

        // Create the Monnaie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonnaieMockMvc.perform(put("/api/monnaies")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(monnaie)))
            .andExpect(status().isBadRequest());

        // Validate the Monnaie in the database
        List<Monnaie> monnaieList = monnaieRepository.findAll();
        assertThat(monnaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMonnaie() throws Exception {
        // Initialize the database
        monnaieService.save(monnaie);

        int databaseSizeBeforeDelete = monnaieRepository.findAll().size();

        // Delete the monnaie
        restMonnaieMockMvc.perform(delete("/api/monnaies/{id}", monnaie.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Monnaie> monnaieList = monnaieRepository.findAll();
        assertThat(monnaieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
