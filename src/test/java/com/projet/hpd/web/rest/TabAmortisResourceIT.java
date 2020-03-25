package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TabAmortis;
import com.projet.hpd.repository.TabAmortisRepository;
import com.projet.hpd.service.TabAmortisService;
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
 * Integration tests for the {@link TabAmortisResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TabAmortisResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_ETAT = "BBBBBBBBBB";

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
    private TabAmortisRepository tabAmortisRepository;

    @Autowired
    private TabAmortisService tabAmortisService;

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

    private MockMvc restTabAmortisMockMvc;

    private TabAmortis tabAmortis;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TabAmortisResource tabAmortisResource = new TabAmortisResource(tabAmortisService);
        this.restTabAmortisMockMvc = MockMvcBuilders.standaloneSetup(tabAmortisResource)
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
    public static TabAmortis createEntity(EntityManager em) {
        TabAmortis tabAmortis = new TabAmortis()
            .libelle(DEFAULT_LIBELLE)
            .montant(DEFAULT_MONTANT)
            .date(DEFAULT_DATE)
            .etat(DEFAULT_ETAT)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return tabAmortis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TabAmortis createUpdatedEntity(EntityManager em) {
        TabAmortis tabAmortis = new TabAmortis()
            .libelle(UPDATED_LIBELLE)
            .montant(UPDATED_MONTANT)
            .date(UPDATED_DATE)
            .etat(UPDATED_ETAT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return tabAmortis;
    }

    @BeforeEach
    public void initTest() {
        tabAmortis = createEntity(em);
    }

    @Test
    @Transactional
    public void createTabAmortis() throws Exception {
        int databaseSizeBeforeCreate = tabAmortisRepository.findAll().size();

        // Create the TabAmortis
        restTabAmortisMockMvc.perform(post("/api/tab-amortis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tabAmortis)))
            .andExpect(status().isCreated());

        // Validate the TabAmortis in the database
        List<TabAmortis> tabAmortisList = tabAmortisRepository.findAll();
        assertThat(tabAmortisList).hasSize(databaseSizeBeforeCreate + 1);
        TabAmortis testTabAmortis = tabAmortisList.get(tabAmortisList.size() - 1);
        assertThat(testTabAmortis.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTabAmortis.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testTabAmortis.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTabAmortis.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testTabAmortis.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTabAmortis.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTabAmortis.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testTabAmortis.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTabAmortis.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTabAmortis.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTabAmortisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tabAmortisRepository.findAll().size();

        // Create the TabAmortis with an existing ID
        tabAmortis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTabAmortisMockMvc.perform(post("/api/tab-amortis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tabAmortis)))
            .andExpect(status().isBadRequest());

        // Validate the TabAmortis in the database
        List<TabAmortis> tabAmortisList = tabAmortisRepository.findAll();
        assertThat(tabAmortisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTabAmortis() throws Exception {
        // Initialize the database
        tabAmortisRepository.saveAndFlush(tabAmortis);

        // Get all the tabAmortisList
        restTabAmortisMockMvc.perform(get("/api/tab-amortis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tabAmortis.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTabAmortis() throws Exception {
        // Initialize the database
        tabAmortisRepository.saveAndFlush(tabAmortis);

        // Get the tabAmortis
        restTabAmortisMockMvc.perform(get("/api/tab-amortis/{id}", tabAmortis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tabAmortis.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTabAmortis() throws Exception {
        // Get the tabAmortis
        restTabAmortisMockMvc.perform(get("/api/tab-amortis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTabAmortis() throws Exception {
        // Initialize the database
        tabAmortisService.save(tabAmortis);

        int databaseSizeBeforeUpdate = tabAmortisRepository.findAll().size();

        // Update the tabAmortis
        TabAmortis updatedTabAmortis = tabAmortisRepository.findById(tabAmortis.getId()).get();
        // Disconnect from session so that the updates on updatedTabAmortis are not directly saved in db
        em.detach(updatedTabAmortis);
        updatedTabAmortis
            .libelle(UPDATED_LIBELLE)
            .montant(UPDATED_MONTANT)
            .date(UPDATED_DATE)
            .etat(UPDATED_ETAT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTabAmortisMockMvc.perform(put("/api/tab-amortis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTabAmortis)))
            .andExpect(status().isOk());

        // Validate the TabAmortis in the database
        List<TabAmortis> tabAmortisList = tabAmortisRepository.findAll();
        assertThat(tabAmortisList).hasSize(databaseSizeBeforeUpdate);
        TabAmortis testTabAmortis = tabAmortisList.get(tabAmortisList.size() - 1);
        assertThat(testTabAmortis.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTabAmortis.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testTabAmortis.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTabAmortis.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testTabAmortis.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTabAmortis.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTabAmortis.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testTabAmortis.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTabAmortis.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTabAmortis.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTabAmortis() throws Exception {
        int databaseSizeBeforeUpdate = tabAmortisRepository.findAll().size();

        // Create the TabAmortis

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTabAmortisMockMvc.perform(put("/api/tab-amortis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tabAmortis)))
            .andExpect(status().isBadRequest());

        // Validate the TabAmortis in the database
        List<TabAmortis> tabAmortisList = tabAmortisRepository.findAll();
        assertThat(tabAmortisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTabAmortis() throws Exception {
        // Initialize the database
        tabAmortisService.save(tabAmortis);

        int databaseSizeBeforeDelete = tabAmortisRepository.findAll().size();

        // Delete the tabAmortis
        restTabAmortisMockMvc.perform(delete("/api/tab-amortis/{id}", tabAmortis.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TabAmortis> tabAmortisList = tabAmortisRepository.findAll();
        assertThat(tabAmortisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
