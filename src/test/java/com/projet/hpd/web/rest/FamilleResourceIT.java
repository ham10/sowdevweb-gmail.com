package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Famille;
import com.projet.hpd.repository.FamilleRepository;
import com.projet.hpd.service.FamilleService;
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
 * Integration tests for the {@link FamilleResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class FamilleResourceIT {

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
    private FamilleRepository familleRepository;

    @Autowired
    private FamilleService familleService;

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

    private MockMvc restFamilleMockMvc;

    private Famille famille;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FamilleResource familleResource = new FamilleResource(familleService);
        this.restFamilleMockMvc = MockMvcBuilders.standaloneSetup(familleResource)
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
    public static Famille createEntity(EntityManager em) {
        Famille famille = new Famille()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return famille;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Famille createUpdatedEntity(EntityManager em) {
        Famille famille = new Famille()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return famille;
    }

    @BeforeEach
    public void initTest() {
        famille = createEntity(em);
    }

    @Test
    @Transactional
    public void createFamille() throws Exception {
        int databaseSizeBeforeCreate = familleRepository.findAll().size();

        // Create the Famille
        restFamilleMockMvc.perform(post("/api/familles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(famille)))
            .andExpect(status().isCreated());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeCreate + 1);
        Famille testFamille = familleList.get(familleList.size() - 1);
        assertThat(testFamille.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFamille.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testFamille.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testFamille.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testFamille.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testFamille.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testFamille.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testFamille.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createFamilleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = familleRepository.findAll().size();

        // Create the Famille with an existing ID
        famille.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilleMockMvc.perform(post("/api/familles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(famille)))
            .andExpect(status().isBadRequest());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFamilles() throws Exception {
        // Initialize the database
        familleRepository.saveAndFlush(famille);

        // Get all the familleList
        restFamilleMockMvc.perform(get("/api/familles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(famille.getId().intValue())))
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
    public void getFamille() throws Exception {
        // Initialize the database
        familleRepository.saveAndFlush(famille);

        // Get the famille
        restFamilleMockMvc.perform(get("/api/familles/{id}", famille.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(famille.getId().intValue()))
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
    public void getNonExistingFamille() throws Exception {
        // Get the famille
        restFamilleMockMvc.perform(get("/api/familles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFamille() throws Exception {
        // Initialize the database
        familleService.save(famille);

        int databaseSizeBeforeUpdate = familleRepository.findAll().size();

        // Update the famille
        Famille updatedFamille = familleRepository.findById(famille.getId()).get();
        // Disconnect from session so that the updates on updatedFamille are not directly saved in db
        em.detach(updatedFamille);
        updatedFamille
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restFamilleMockMvc.perform(put("/api/familles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFamille)))
            .andExpect(status().isOk());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeUpdate);
        Famille testFamille = familleList.get(familleList.size() - 1);
        assertThat(testFamille.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFamille.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testFamille.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testFamille.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testFamille.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testFamille.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testFamille.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testFamille.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingFamille() throws Exception {
        int databaseSizeBeforeUpdate = familleRepository.findAll().size();

        // Create the Famille

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilleMockMvc.perform(put("/api/familles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(famille)))
            .andExpect(status().isBadRequest());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFamille() throws Exception {
        // Initialize the database
        familleService.save(famille);

        int databaseSizeBeforeDelete = familleRepository.findAll().size();

        // Delete the famille
        restFamilleMockMvc.perform(delete("/api/familles/{id}", famille.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
