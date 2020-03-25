package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Plateau;
import com.projet.hpd.repository.PlateauRepository;
import com.projet.hpd.service.PlateauService;
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
 * Integration tests for the {@link PlateauResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class PlateauResourceIT {

    private static final String DEFAULT_LIBELLE_PLATEAU = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_PLATEAU = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_PLATEAU = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_PLATEAU = "BBBBBBBBBB";

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
    private PlateauRepository plateauRepository;

    @Autowired
    private PlateauService plateauService;

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

    private MockMvc restPlateauMockMvc;

    private Plateau plateau;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlateauResource plateauResource = new PlateauResource(plateauService);
        this.restPlateauMockMvc = MockMvcBuilders.standaloneSetup(plateauResource)
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
    public static Plateau createEntity(EntityManager em) {
        Plateau plateau = new Plateau()
            .libellePlateau(DEFAULT_LIBELLE_PLATEAU)
            .descriptionPlateau(DEFAULT_DESCRIPTION_PLATEAU)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return plateau;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plateau createUpdatedEntity(EntityManager em) {
        Plateau plateau = new Plateau()
            .libellePlateau(UPDATED_LIBELLE_PLATEAU)
            .descriptionPlateau(UPDATED_DESCRIPTION_PLATEAU)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return plateau;
    }

    @BeforeEach
    public void initTest() {
        plateau = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlateau() throws Exception {
        int databaseSizeBeforeCreate = plateauRepository.findAll().size();

        // Create the Plateau
        restPlateauMockMvc.perform(post("/api/plateaus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plateau)))
            .andExpect(status().isCreated());

        // Validate the Plateau in the database
        List<Plateau> plateauList = plateauRepository.findAll();
        assertThat(plateauList).hasSize(databaseSizeBeforeCreate + 1);
        Plateau testPlateau = plateauList.get(plateauList.size() - 1);
        assertThat(testPlateau.getLibellePlateau()).isEqualTo(DEFAULT_LIBELLE_PLATEAU);
        assertThat(testPlateau.getDescriptionPlateau()).isEqualTo(DEFAULT_DESCRIPTION_PLATEAU);
        assertThat(testPlateau.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testPlateau.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testPlateau.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testPlateau.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testPlateau.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createPlateauWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = plateauRepository.findAll().size();

        // Create the Plateau with an existing ID
        plateau.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlateauMockMvc.perform(post("/api/plateaus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plateau)))
            .andExpect(status().isBadRequest());

        // Validate the Plateau in the database
        List<Plateau> plateauList = plateauRepository.findAll();
        assertThat(plateauList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlateaus() throws Exception {
        // Initialize the database
        plateauRepository.saveAndFlush(plateau);

        // Get all the plateauList
        restPlateauMockMvc.perform(get("/api/plateaus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plateau.getId().intValue())))
            .andExpect(jsonPath("$.[*].libellePlateau").value(hasItem(DEFAULT_LIBELLE_PLATEAU)))
            .andExpect(jsonPath("$.[*].descriptionPlateau").value(hasItem(DEFAULT_DESCRIPTION_PLATEAU)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getPlateau() throws Exception {
        // Initialize the database
        plateauRepository.saveAndFlush(plateau);

        // Get the plateau
        restPlateauMockMvc.perform(get("/api/plateaus/{id}", plateau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plateau.getId().intValue()))
            .andExpect(jsonPath("$.libellePlateau").value(DEFAULT_LIBELLE_PLATEAU))
            .andExpect(jsonPath("$.descriptionPlateau").value(DEFAULT_DESCRIPTION_PLATEAU))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlateau() throws Exception {
        // Get the plateau
        restPlateauMockMvc.perform(get("/api/plateaus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlateau() throws Exception {
        // Initialize the database
        plateauService.save(plateau);

        int databaseSizeBeforeUpdate = plateauRepository.findAll().size();

        // Update the plateau
        Plateau updatedPlateau = plateauRepository.findById(plateau.getId()).get();
        // Disconnect from session so that the updates on updatedPlateau are not directly saved in db
        em.detach(updatedPlateau);
        updatedPlateau
            .libellePlateau(UPDATED_LIBELLE_PLATEAU)
            .descriptionPlateau(UPDATED_DESCRIPTION_PLATEAU)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restPlateauMockMvc.perform(put("/api/plateaus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlateau)))
            .andExpect(status().isOk());

        // Validate the Plateau in the database
        List<Plateau> plateauList = plateauRepository.findAll();
        assertThat(plateauList).hasSize(databaseSizeBeforeUpdate);
        Plateau testPlateau = plateauList.get(plateauList.size() - 1);
        assertThat(testPlateau.getLibellePlateau()).isEqualTo(UPDATED_LIBELLE_PLATEAU);
        assertThat(testPlateau.getDescriptionPlateau()).isEqualTo(UPDATED_DESCRIPTION_PLATEAU);
        assertThat(testPlateau.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testPlateau.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testPlateau.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testPlateau.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testPlateau.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingPlateau() throws Exception {
        int databaseSizeBeforeUpdate = plateauRepository.findAll().size();

        // Create the Plateau

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlateauMockMvc.perform(put("/api/plateaus")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(plateau)))
            .andExpect(status().isBadRequest());

        // Validate the Plateau in the database
        List<Plateau> plateauList = plateauRepository.findAll();
        assertThat(plateauList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlateau() throws Exception {
        // Initialize the database
        plateauService.save(plateau);

        int databaseSizeBeforeDelete = plateauRepository.findAll().size();

        // Delete the plateau
        restPlateauMockMvc.perform(delete("/api/plateaus/{id}", plateau.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plateau> plateauList = plateauRepository.findAll();
        assertThat(plateauList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
