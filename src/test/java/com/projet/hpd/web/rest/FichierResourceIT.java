package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Fichier;
import com.projet.hpd.repository.FichierRepository;
import com.projet.hpd.service.FichierService;
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
 * Integration tests for the {@link FichierResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class FichierResourceIT {

    private static final String DEFAULT_CHEMIN_FICHIER = "AAAAAAAAAA";
    private static final String UPDATED_CHEMIN_FICHIER = "BBBBBBBBBB";

    private static final String DEFAULT_FORMAT_FICHIER = "AAAAAAAAAA";
    private static final String UPDATED_FORMAT_FICHIER = "BBBBBBBBBB";

    private static final String DEFAULT_SEPARATEUR_FICHIER = "AAAAAAAAAA";
    private static final String UPDATED_SEPARATEUR_FICHIER = "BBBBBBBBBB";

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
    private FichierRepository fichierRepository;

    @Autowired
    private FichierService fichierService;

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

    private MockMvc restFichierMockMvc;

    private Fichier fichier;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FichierResource fichierResource = new FichierResource(fichierService);
        this.restFichierMockMvc = MockMvcBuilders.standaloneSetup(fichierResource)
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
    public static Fichier createEntity(EntityManager em) {
        Fichier fichier = new Fichier()
            .cheminFichier(DEFAULT_CHEMIN_FICHIER)
            .formatFichier(DEFAULT_FORMAT_FICHIER)
            .separateurFichier(DEFAULT_SEPARATEUR_FICHIER)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return fichier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fichier createUpdatedEntity(EntityManager em) {
        Fichier fichier = new Fichier()
            .cheminFichier(UPDATED_CHEMIN_FICHIER)
            .formatFichier(UPDATED_FORMAT_FICHIER)
            .separateurFichier(UPDATED_SEPARATEUR_FICHIER)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return fichier;
    }

    @BeforeEach
    public void initTest() {
        fichier = createEntity(em);
    }

    @Test
    @Transactional
    public void createFichier() throws Exception {
        int databaseSizeBeforeCreate = fichierRepository.findAll().size();

        // Create the Fichier
        restFichierMockMvc.perform(post("/api/fichiers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fichier)))
            .andExpect(status().isCreated());

        // Validate the Fichier in the database
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeCreate + 1);
        Fichier testFichier = fichierList.get(fichierList.size() - 1);
        assertThat(testFichier.getCheminFichier()).isEqualTo(DEFAULT_CHEMIN_FICHIER);
        assertThat(testFichier.getFormatFichier()).isEqualTo(DEFAULT_FORMAT_FICHIER);
        assertThat(testFichier.getSeparateurFichier()).isEqualTo(DEFAULT_SEPARATEUR_FICHIER);
        assertThat(testFichier.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testFichier.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testFichier.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testFichier.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testFichier.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testFichier.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createFichierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fichierRepository.findAll().size();

        // Create the Fichier with an existing ID
        fichier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFichierMockMvc.perform(post("/api/fichiers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fichier)))
            .andExpect(status().isBadRequest());

        // Validate the Fichier in the database
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFichiers() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);

        // Get all the fichierList
        restFichierMockMvc.perform(get("/api/fichiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichier.getId().intValue())))
            .andExpect(jsonPath("$.[*].cheminFichier").value(hasItem(DEFAULT_CHEMIN_FICHIER)))
            .andExpect(jsonPath("$.[*].formatFichier").value(hasItem(DEFAULT_FORMAT_FICHIER)))
            .andExpect(jsonPath("$.[*].separateurFichier").value(hasItem(DEFAULT_SEPARATEUR_FICHIER)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getFichier() throws Exception {
        // Initialize the database
        fichierRepository.saveAndFlush(fichier);

        // Get the fichier
        restFichierMockMvc.perform(get("/api/fichiers/{id}", fichier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fichier.getId().intValue()))
            .andExpect(jsonPath("$.cheminFichier").value(DEFAULT_CHEMIN_FICHIER))
            .andExpect(jsonPath("$.formatFichier").value(DEFAULT_FORMAT_FICHIER))
            .andExpect(jsonPath("$.separateurFichier").value(DEFAULT_SEPARATEUR_FICHIER))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFichier() throws Exception {
        // Get the fichier
        restFichierMockMvc.perform(get("/api/fichiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFichier() throws Exception {
        // Initialize the database
        fichierService.save(fichier);

        int databaseSizeBeforeUpdate = fichierRepository.findAll().size();

        // Update the fichier
        Fichier updatedFichier = fichierRepository.findById(fichier.getId()).get();
        // Disconnect from session so that the updates on updatedFichier are not directly saved in db
        em.detach(updatedFichier);
        updatedFichier
            .cheminFichier(UPDATED_CHEMIN_FICHIER)
            .formatFichier(UPDATED_FORMAT_FICHIER)
            .separateurFichier(UPDATED_SEPARATEUR_FICHIER)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restFichierMockMvc.perform(put("/api/fichiers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFichier)))
            .andExpect(status().isOk());

        // Validate the Fichier in the database
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeUpdate);
        Fichier testFichier = fichierList.get(fichierList.size() - 1);
        assertThat(testFichier.getCheminFichier()).isEqualTo(UPDATED_CHEMIN_FICHIER);
        assertThat(testFichier.getFormatFichier()).isEqualTo(UPDATED_FORMAT_FICHIER);
        assertThat(testFichier.getSeparateurFichier()).isEqualTo(UPDATED_SEPARATEUR_FICHIER);
        assertThat(testFichier.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testFichier.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testFichier.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testFichier.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testFichier.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testFichier.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingFichier() throws Exception {
        int databaseSizeBeforeUpdate = fichierRepository.findAll().size();

        // Create the Fichier

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFichierMockMvc.perform(put("/api/fichiers")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fichier)))
            .andExpect(status().isBadRequest());

        // Validate the Fichier in the database
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFichier() throws Exception {
        // Initialize the database
        fichierService.save(fichier);

        int databaseSizeBeforeDelete = fichierRepository.findAll().size();

        // Delete the fichier
        restFichierMockMvc.perform(delete("/api/fichiers/{id}", fichier.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fichier> fichierList = fichierRepository.findAll();
        assertThat(fichierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
