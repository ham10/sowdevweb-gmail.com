package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Devise;
import com.projet.hpd.repository.DeviseRepository;
import com.projet.hpd.service.DeviseService;
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
 * Integration tests for the {@link DeviseResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class DeviseResourceIT {

    private static final String DEFAULT_CODEISO_DVISE = "AAAAAAAAAA";
    private static final String UPDATED_CODEISO_DVISE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_DEVISE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_DEVISE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_DEVISE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_DEVISE = "BBBBBBBBBB";

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
    private DeviseRepository deviseRepository;

    @Autowired
    private DeviseService deviseService;

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

    private MockMvc restDeviseMockMvc;

    private Devise devise;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeviseResource deviseResource = new DeviseResource(deviseService);
        this.restDeviseMockMvc = MockMvcBuilders.standaloneSetup(deviseResource)
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
    public static Devise createEntity(EntityManager em) {
        Devise devise = new Devise()
            .codeisoDvise(DEFAULT_CODEISO_DVISE)
            .libelleDevise(DEFAULT_LIBELLE_DEVISE)
            .descriptionDevise(DEFAULT_DESCRIPTION_DEVISE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return devise;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Devise createUpdatedEntity(EntityManager em) {
        Devise devise = new Devise()
            .codeisoDvise(UPDATED_CODEISO_DVISE)
            .libelleDevise(UPDATED_LIBELLE_DEVISE)
            .descriptionDevise(UPDATED_DESCRIPTION_DEVISE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return devise;
    }

    @BeforeEach
    public void initTest() {
        devise = createEntity(em);
    }

    @Test
    @Transactional
    public void createDevise() throws Exception {
        int databaseSizeBeforeCreate = deviseRepository.findAll().size();

        // Create the Devise
        restDeviseMockMvc.perform(post("/api/devises")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(devise)))
            .andExpect(status().isCreated());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeCreate + 1);
        Devise testDevise = deviseList.get(deviseList.size() - 1);
        assertThat(testDevise.getCodeisoDvise()).isEqualTo(DEFAULT_CODEISO_DVISE);
        assertThat(testDevise.getLibelleDevise()).isEqualTo(DEFAULT_LIBELLE_DEVISE);
        assertThat(testDevise.getDescriptionDevise()).isEqualTo(DEFAULT_DESCRIPTION_DEVISE);
        assertThat(testDevise.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testDevise.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testDevise.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testDevise.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testDevise.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createDeviseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviseRepository.findAll().size();

        // Create the Devise with an existing ID
        devise.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviseMockMvc.perform(post("/api/devises")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(devise)))
            .andExpect(status().isBadRequest());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDevises() throws Exception {
        // Initialize the database
        deviseRepository.saveAndFlush(devise);

        // Get all the deviseList
        restDeviseMockMvc.perform(get("/api/devises?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devise.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeisoDvise").value(hasItem(DEFAULT_CODEISO_DVISE)))
            .andExpect(jsonPath("$.[*].libelleDevise").value(hasItem(DEFAULT_LIBELLE_DEVISE)))
            .andExpect(jsonPath("$.[*].descriptionDevise").value(hasItem(DEFAULT_DESCRIPTION_DEVISE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getDevise() throws Exception {
        // Initialize the database
        deviseRepository.saveAndFlush(devise);

        // Get the devise
        restDeviseMockMvc.perform(get("/api/devises/{id}", devise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(devise.getId().intValue()))
            .andExpect(jsonPath("$.codeisoDvise").value(DEFAULT_CODEISO_DVISE))
            .andExpect(jsonPath("$.libelleDevise").value(DEFAULT_LIBELLE_DEVISE))
            .andExpect(jsonPath("$.descriptionDevise").value(DEFAULT_DESCRIPTION_DEVISE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDevise() throws Exception {
        // Get the devise
        restDeviseMockMvc.perform(get("/api/devises/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDevise() throws Exception {
        // Initialize the database
        deviseService.save(devise);

        int databaseSizeBeforeUpdate = deviseRepository.findAll().size();

        // Update the devise
        Devise updatedDevise = deviseRepository.findById(devise.getId()).get();
        // Disconnect from session so that the updates on updatedDevise are not directly saved in db
        em.detach(updatedDevise);
        updatedDevise
            .codeisoDvise(UPDATED_CODEISO_DVISE)
            .libelleDevise(UPDATED_LIBELLE_DEVISE)
            .descriptionDevise(UPDATED_DESCRIPTION_DEVISE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restDeviseMockMvc.perform(put("/api/devises")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDevise)))
            .andExpect(status().isOk());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeUpdate);
        Devise testDevise = deviseList.get(deviseList.size() - 1);
        assertThat(testDevise.getCodeisoDvise()).isEqualTo(UPDATED_CODEISO_DVISE);
        assertThat(testDevise.getLibelleDevise()).isEqualTo(UPDATED_LIBELLE_DEVISE);
        assertThat(testDevise.getDescriptionDevise()).isEqualTo(UPDATED_DESCRIPTION_DEVISE);
        assertThat(testDevise.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testDevise.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testDevise.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testDevise.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testDevise.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingDevise() throws Exception {
        int databaseSizeBeforeUpdate = deviseRepository.findAll().size();

        // Create the Devise

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviseMockMvc.perform(put("/api/devises")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(devise)))
            .andExpect(status().isBadRequest());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDevise() throws Exception {
        // Initialize the database
        deviseService.save(devise);

        int databaseSizeBeforeDelete = deviseRepository.findAll().size();

        // Delete the devise
        restDeviseMockMvc.perform(delete("/api/devises/{id}", devise.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
