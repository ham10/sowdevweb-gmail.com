package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeTarif;
import com.projet.hpd.repository.TypeTarifRepository;
import com.projet.hpd.service.TypeTarifService;
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
 * Integration tests for the {@link TypeTarifResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeTarifResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

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
    private TypeTarifRepository typeTarifRepository;

    @Autowired
    private TypeTarifService typeTarifService;

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

    private MockMvc restTypeTarifMockMvc;

    private TypeTarif typeTarif;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeTarifResource typeTarifResource = new TypeTarifResource(typeTarifService);
        this.restTypeTarifMockMvc = MockMvcBuilders.standaloneSetup(typeTarifResource)
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
    public static TypeTarif createEntity(EntityManager em) {
        TypeTarif typeTarif = new TypeTarif()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeTarif;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeTarif createUpdatedEntity(EntityManager em) {
        TypeTarif typeTarif = new TypeTarif()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeTarif;
    }

    @BeforeEach
    public void initTest() {
        typeTarif = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeTarif() throws Exception {
        int databaseSizeBeforeCreate = typeTarifRepository.findAll().size();

        // Create the TypeTarif
        restTypeTarifMockMvc.perform(post("/api/type-tarifs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeTarif)))
            .andExpect(status().isCreated());

        // Validate the TypeTarif in the database
        List<TypeTarif> typeTarifList = typeTarifRepository.findAll();
        assertThat(typeTarifList).hasSize(databaseSizeBeforeCreate + 1);
        TypeTarif testTypeTarif = typeTarifList.get(typeTarifList.size() - 1);
        assertThat(testTypeTarif.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeTarif.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeTarif.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeTarif.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeTarif.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testTypeTarif.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeTarif.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeTarif.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeTarifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeTarifRepository.findAll().size();

        // Create the TypeTarif with an existing ID
        typeTarif.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeTarifMockMvc.perform(post("/api/type-tarifs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeTarif)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTarif in the database
        List<TypeTarif> typeTarifList = typeTarifRepository.findAll();
        assertThat(typeTarifList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeTarifs() throws Exception {
        // Initialize the database
        typeTarifRepository.saveAndFlush(typeTarif);

        // Get all the typeTarifList
        restTypeTarifMockMvc.perform(get("/api/type-tarifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeTarif.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeTarif() throws Exception {
        // Initialize the database
        typeTarifRepository.saveAndFlush(typeTarif);

        // Get the typeTarif
        restTypeTarifMockMvc.perform(get("/api/type-tarifs/{id}", typeTarif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeTarif.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeTarif() throws Exception {
        // Get the typeTarif
        restTypeTarifMockMvc.perform(get("/api/type-tarifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeTarif() throws Exception {
        // Initialize the database
        typeTarifService.save(typeTarif);

        int databaseSizeBeforeUpdate = typeTarifRepository.findAll().size();

        // Update the typeTarif
        TypeTarif updatedTypeTarif = typeTarifRepository.findById(typeTarif.getId()).get();
        // Disconnect from session so that the updates on updatedTypeTarif are not directly saved in db
        em.detach(updatedTypeTarif);
        updatedTypeTarif
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeTarifMockMvc.perform(put("/api/type-tarifs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeTarif)))
            .andExpect(status().isOk());

        // Validate the TypeTarif in the database
        List<TypeTarif> typeTarifList = typeTarifRepository.findAll();
        assertThat(typeTarifList).hasSize(databaseSizeBeforeUpdate);
        TypeTarif testTypeTarif = typeTarifList.get(typeTarifList.size() - 1);
        assertThat(testTypeTarif.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeTarif.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeTarif.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeTarif.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeTarif.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testTypeTarif.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeTarif.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeTarif.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeTarif() throws Exception {
        int databaseSizeBeforeUpdate = typeTarifRepository.findAll().size();

        // Create the TypeTarif

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeTarifMockMvc.perform(put("/api/type-tarifs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeTarif)))
            .andExpect(status().isBadRequest());

        // Validate the TypeTarif in the database
        List<TypeTarif> typeTarifList = typeTarifRepository.findAll();
        assertThat(typeTarifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeTarif() throws Exception {
        // Initialize the database
        typeTarifService.save(typeTarif);

        int databaseSizeBeforeDelete = typeTarifRepository.findAll().size();

        // Delete the typeTarif
        restTypeTarifMockMvc.perform(delete("/api/type-tarifs/{id}", typeTarif.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeTarif> typeTarifList = typeTarifRepository.findAll();
        assertThat(typeTarifList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
