package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Specialite;
import com.projet.hpd.repository.SpecialiteRepository;
import com.projet.hpd.service.SpecialiteService;
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
 * Integration tests for the {@link SpecialiteResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class SpecialiteResourceIT {

    private static final String DEFAULT_CODE_SPECIALITE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_SPECIALITE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_SPECIALITE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_SPECIALITE = "BBBBBBBBBB";

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
    private SpecialiteRepository specialiteRepository;

    @Autowired
    private SpecialiteService specialiteService;

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

    private MockMvc restSpecialiteMockMvc;

    private Specialite specialite;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpecialiteResource specialiteResource = new SpecialiteResource(specialiteService);
        this.restSpecialiteMockMvc = MockMvcBuilders.standaloneSetup(specialiteResource)
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
    public static Specialite createEntity(EntityManager em) {
        Specialite specialite = new Specialite()
            .codeSpecialite(DEFAULT_CODE_SPECIALITE)
            .libelleSpecialite(DEFAULT_LIBELLE_SPECIALITE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return specialite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialite createUpdatedEntity(EntityManager em) {
        Specialite specialite = new Specialite()
            .codeSpecialite(UPDATED_CODE_SPECIALITE)
            .libelleSpecialite(UPDATED_LIBELLE_SPECIALITE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return specialite;
    }

    @BeforeEach
    public void initTest() {
        specialite = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpecialite() throws Exception {
        int databaseSizeBeforeCreate = specialiteRepository.findAll().size();

        // Create the Specialite
        restSpecialiteMockMvc.perform(post("/api/specialites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialite)))
            .andExpect(status().isCreated());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeCreate + 1);
        Specialite testSpecialite = specialiteList.get(specialiteList.size() - 1);
        assertThat(testSpecialite.getCodeSpecialite()).isEqualTo(DEFAULT_CODE_SPECIALITE);
        assertThat(testSpecialite.getLibelleSpecialite()).isEqualTo(DEFAULT_LIBELLE_SPECIALITE);
        assertThat(testSpecialite.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testSpecialite.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testSpecialite.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testSpecialite.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testSpecialite.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createSpecialiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialiteRepository.findAll().size();

        // Create the Specialite with an existing ID
        specialite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialiteMockMvc.perform(post("/api/specialites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialite)))
            .andExpect(status().isBadRequest());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSpecialites() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList
        restSpecialiteMockMvc.perform(get("/api/specialites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialite.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeSpecialite").value(hasItem(DEFAULT_CODE_SPECIALITE)))
            .andExpect(jsonPath("$.[*].libelleSpecialite").value(hasItem(DEFAULT_LIBELLE_SPECIALITE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getSpecialite() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get the specialite
        restSpecialiteMockMvc.perform(get("/api/specialites/{id}", specialite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(specialite.getId().intValue()))
            .andExpect(jsonPath("$.codeSpecialite").value(DEFAULT_CODE_SPECIALITE))
            .andExpect(jsonPath("$.libelleSpecialite").value(DEFAULT_LIBELLE_SPECIALITE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSpecialite() throws Exception {
        // Get the specialite
        restSpecialiteMockMvc.perform(get("/api/specialites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecialite() throws Exception {
        // Initialize the database
        specialiteService.save(specialite);

        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();

        // Update the specialite
        Specialite updatedSpecialite = specialiteRepository.findById(specialite.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialite are not directly saved in db
        em.detach(updatedSpecialite);
        updatedSpecialite
            .codeSpecialite(UPDATED_CODE_SPECIALITE)
            .libelleSpecialite(UPDATED_LIBELLE_SPECIALITE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restSpecialiteMockMvc.perform(put("/api/specialites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpecialite)))
            .andExpect(status().isOk());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
        Specialite testSpecialite = specialiteList.get(specialiteList.size() - 1);
        assertThat(testSpecialite.getCodeSpecialite()).isEqualTo(UPDATED_CODE_SPECIALITE);
        assertThat(testSpecialite.getLibelleSpecialite()).isEqualTo(UPDATED_LIBELLE_SPECIALITE);
        assertThat(testSpecialite.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testSpecialite.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testSpecialite.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testSpecialite.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testSpecialite.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingSpecialite() throws Exception {
        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();

        // Create the Specialite

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialiteMockMvc.perform(put("/api/specialites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialite)))
            .andExpect(status().isBadRequest());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpecialite() throws Exception {
        // Initialize the database
        specialiteService.save(specialite);

        int databaseSizeBeforeDelete = specialiteRepository.findAll().size();

        // Delete the specialite
        restSpecialiteMockMvc.perform(delete("/api/specialites/{id}", specialite.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
