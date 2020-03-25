package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Activite;
import com.projet.hpd.repository.ActiviteRepository;
import com.projet.hpd.service.ActiviteService;
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
 * Integration tests for the {@link ActiviteResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class ActiviteResourceIT {

    private static final LocalDate DEFAULT_DATE_ACTIVITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ACTIVITE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HEURE_CONNEXION_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_CONNEXION_ACTIVITE = "BBBBBBBBBB";

    private static final String DEFAULT_HEURE_DE_CONNEXION_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_HEURE_DE_CONNEXION_ACTIVITE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE_IP_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_IP_ACTIVITE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final String DEFAULT_ADRESSE_MAC = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_MAC = "BBBBBBBBBB";

    @Autowired
    private ActiviteRepository activiteRepository;

    @Autowired
    private ActiviteService activiteService;

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

    private MockMvc restActiviteMockMvc;

    private Activite activite;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActiviteResource activiteResource = new ActiviteResource(activiteService);
        this.restActiviteMockMvc = MockMvcBuilders.standaloneSetup(activiteResource)
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
    public static Activite createEntity(EntityManager em) {
        Activite activite = new Activite()
            .dateActivite(DEFAULT_DATE_ACTIVITE)
            .heureConnexionActivite(DEFAULT_HEURE_CONNEXION_ACTIVITE)
            .heureDeConnexionActivite(DEFAULT_HEURE_DE_CONNEXION_ACTIVITE)
            .adresseIpActivite(DEFAULT_ADRESSE_IP_ACTIVITE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .userCreated(DEFAULT_USER_CREATED)
            .adresseMac(DEFAULT_ADRESSE_MAC);
        return activite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Activite createUpdatedEntity(EntityManager em) {
        Activite activite = new Activite()
            .dateActivite(UPDATED_DATE_ACTIVITE)
            .heureConnexionActivite(UPDATED_HEURE_CONNEXION_ACTIVITE)
            .heureDeConnexionActivite(UPDATED_HEURE_DE_CONNEXION_ACTIVITE)
            .adresseIpActivite(UPDATED_ADRESSE_IP_ACTIVITE)
            .dateCreated(UPDATED_DATE_CREATED)
            .userCreated(UPDATED_USER_CREATED)
            .adresseMac(UPDATED_ADRESSE_MAC);
        return activite;
    }

    @BeforeEach
    public void initTest() {
        activite = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivite() throws Exception {
        int databaseSizeBeforeCreate = activiteRepository.findAll().size();

        // Create the Activite
        restActiviteMockMvc.perform(post("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activite)))
            .andExpect(status().isCreated());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeCreate + 1);
        Activite testActivite = activiteList.get(activiteList.size() - 1);
        assertThat(testActivite.getDateActivite()).isEqualTo(DEFAULT_DATE_ACTIVITE);
        assertThat(testActivite.getHeureConnexionActivite()).isEqualTo(DEFAULT_HEURE_CONNEXION_ACTIVITE);
        assertThat(testActivite.getHeureDeConnexionActivite()).isEqualTo(DEFAULT_HEURE_DE_CONNEXION_ACTIVITE);
        assertThat(testActivite.getAdresseIpActivite()).isEqualTo(DEFAULT_ADRESSE_IP_ACTIVITE);
        assertThat(testActivite.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testActivite.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testActivite.getAdresseMac()).isEqualTo(DEFAULT_ADRESSE_MAC);
    }

    @Test
    @Transactional
    public void createActiviteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activiteRepository.findAll().size();

        // Create the Activite with an existing ID
        activite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActiviteMockMvc.perform(post("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activite)))
            .andExpect(status().isBadRequest());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllActivites() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);

        // Get all the activiteList
        restActiviteMockMvc.perform(get("/api/activites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activite.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateActivite").value(hasItem(DEFAULT_DATE_ACTIVITE.toString())))
            .andExpect(jsonPath("$.[*].heureConnexionActivite").value(hasItem(DEFAULT_HEURE_CONNEXION_ACTIVITE)))
            .andExpect(jsonPath("$.[*].heureDeConnexionActivite").value(hasItem(DEFAULT_HEURE_DE_CONNEXION_ACTIVITE)))
            .andExpect(jsonPath("$.[*].adresseIpActivite").value(hasItem(DEFAULT_ADRESSE_IP_ACTIVITE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].adresseMac").value(hasItem(DEFAULT_ADRESSE_MAC)));
    }
    
    @Test
    @Transactional
    public void getActivite() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);

        // Get the activite
        restActiviteMockMvc.perform(get("/api/activites/{id}", activite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(activite.getId().intValue()))
            .andExpect(jsonPath("$.dateActivite").value(DEFAULT_DATE_ACTIVITE.toString()))
            .andExpect(jsonPath("$.heureConnexionActivite").value(DEFAULT_HEURE_CONNEXION_ACTIVITE))
            .andExpect(jsonPath("$.heureDeConnexionActivite").value(DEFAULT_HEURE_DE_CONNEXION_ACTIVITE))
            .andExpect(jsonPath("$.adresseIpActivite").value(DEFAULT_ADRESSE_IP_ACTIVITE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.adresseMac").value(DEFAULT_ADRESSE_MAC));
    }

    @Test
    @Transactional
    public void getNonExistingActivite() throws Exception {
        // Get the activite
        restActiviteMockMvc.perform(get("/api/activites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivite() throws Exception {
        // Initialize the database
        activiteService.save(activite);

        int databaseSizeBeforeUpdate = activiteRepository.findAll().size();

        // Update the activite
        Activite updatedActivite = activiteRepository.findById(activite.getId()).get();
        // Disconnect from session so that the updates on updatedActivite are not directly saved in db
        em.detach(updatedActivite);
        updatedActivite
            .dateActivite(UPDATED_DATE_ACTIVITE)
            .heureConnexionActivite(UPDATED_HEURE_CONNEXION_ACTIVITE)
            .heureDeConnexionActivite(UPDATED_HEURE_DE_CONNEXION_ACTIVITE)
            .adresseIpActivite(UPDATED_ADRESSE_IP_ACTIVITE)
            .dateCreated(UPDATED_DATE_CREATED)
            .userCreated(UPDATED_USER_CREATED)
            .adresseMac(UPDATED_ADRESSE_MAC);

        restActiviteMockMvc.perform(put("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedActivite)))
            .andExpect(status().isOk());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeUpdate);
        Activite testActivite = activiteList.get(activiteList.size() - 1);
        assertThat(testActivite.getDateActivite()).isEqualTo(UPDATED_DATE_ACTIVITE);
        assertThat(testActivite.getHeureConnexionActivite()).isEqualTo(UPDATED_HEURE_CONNEXION_ACTIVITE);
        assertThat(testActivite.getHeureDeConnexionActivite()).isEqualTo(UPDATED_HEURE_DE_CONNEXION_ACTIVITE);
        assertThat(testActivite.getAdresseIpActivite()).isEqualTo(UPDATED_ADRESSE_IP_ACTIVITE);
        assertThat(testActivite.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testActivite.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testActivite.getAdresseMac()).isEqualTo(UPDATED_ADRESSE_MAC);
    }

    @Test
    @Transactional
    public void updateNonExistingActivite() throws Exception {
        int databaseSizeBeforeUpdate = activiteRepository.findAll().size();

        // Create the Activite

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restActiviteMockMvc.perform(put("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(activite)))
            .andExpect(status().isBadRequest());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActivite() throws Exception {
        // Initialize the database
        activiteService.save(activite);

        int databaseSizeBeforeDelete = activiteRepository.findAll().size();

        // Delete the activite
        restActiviteMockMvc.perform(delete("/api/activites/{id}", activite.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
