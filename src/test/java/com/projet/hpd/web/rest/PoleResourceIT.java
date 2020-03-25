package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Pole;
import com.projet.hpd.repository.PoleRepository;
import com.projet.hpd.service.PoleService;
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
 * Integration tests for the {@link PoleResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class PoleResourceIT {

    private static final String DEFAULT_CODE_POLE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POLE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_POLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_POLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_POLE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_POLE = "BBBBBBBBBB";

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
    private PoleRepository poleRepository;

    @Autowired
    private PoleService poleService;

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

    private MockMvc restPoleMockMvc;

    private Pole pole;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoleResource poleResource = new PoleResource(poleService);
        this.restPoleMockMvc = MockMvcBuilders.standaloneSetup(poleResource)
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
    public static Pole createEntity(EntityManager em) {
        Pole pole = new Pole()
            .codePole(DEFAULT_CODE_POLE)
            .libellePole(DEFAULT_LIBELLE_POLE)
            .descriptionPole(DEFAULT_DESCRIPTION_POLE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return pole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pole createUpdatedEntity(EntityManager em) {
        Pole pole = new Pole()
            .codePole(UPDATED_CODE_POLE)
            .libellePole(UPDATED_LIBELLE_POLE)
            .descriptionPole(UPDATED_DESCRIPTION_POLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return pole;
    }

    @BeforeEach
    public void initTest() {
        pole = createEntity(em);
    }

    @Test
    @Transactional
    public void createPole() throws Exception {
        int databaseSizeBeforeCreate = poleRepository.findAll().size();

        // Create the Pole
        restPoleMockMvc.perform(post("/api/poles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pole)))
            .andExpect(status().isCreated());

        // Validate the Pole in the database
        List<Pole> poleList = poleRepository.findAll();
        assertThat(poleList).hasSize(databaseSizeBeforeCreate + 1);
        Pole testPole = poleList.get(poleList.size() - 1);
        assertThat(testPole.getCodePole()).isEqualTo(DEFAULT_CODE_POLE);
        assertThat(testPole.getLibellePole()).isEqualTo(DEFAULT_LIBELLE_POLE);
        assertThat(testPole.getDescriptionPole()).isEqualTo(DEFAULT_DESCRIPTION_POLE);
        assertThat(testPole.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testPole.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testPole.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testPole.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testPole.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createPoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poleRepository.findAll().size();

        // Create the Pole with an existing ID
        pole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoleMockMvc.perform(post("/api/poles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pole)))
            .andExpect(status().isBadRequest());

        // Validate the Pole in the database
        List<Pole> poleList = poleRepository.findAll();
        assertThat(poleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPoles() throws Exception {
        // Initialize the database
        poleRepository.saveAndFlush(pole);

        // Get all the poleList
        restPoleMockMvc.perform(get("/api/poles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pole.getId().intValue())))
            .andExpect(jsonPath("$.[*].codePole").value(hasItem(DEFAULT_CODE_POLE)))
            .andExpect(jsonPath("$.[*].libellePole").value(hasItem(DEFAULT_LIBELLE_POLE)))
            .andExpect(jsonPath("$.[*].descriptionPole").value(hasItem(DEFAULT_DESCRIPTION_POLE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getPole() throws Exception {
        // Initialize the database
        poleRepository.saveAndFlush(pole);

        // Get the pole
        restPoleMockMvc.perform(get("/api/poles/{id}", pole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pole.getId().intValue()))
            .andExpect(jsonPath("$.codePole").value(DEFAULT_CODE_POLE))
            .andExpect(jsonPath("$.libellePole").value(DEFAULT_LIBELLE_POLE))
            .andExpect(jsonPath("$.descriptionPole").value(DEFAULT_DESCRIPTION_POLE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPole() throws Exception {
        // Get the pole
        restPoleMockMvc.perform(get("/api/poles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePole() throws Exception {
        // Initialize the database
        poleService.save(pole);

        int databaseSizeBeforeUpdate = poleRepository.findAll().size();

        // Update the pole
        Pole updatedPole = poleRepository.findById(pole.getId()).get();
        // Disconnect from session so that the updates on updatedPole are not directly saved in db
        em.detach(updatedPole);
        updatedPole
            .codePole(UPDATED_CODE_POLE)
            .libellePole(UPDATED_LIBELLE_POLE)
            .descriptionPole(UPDATED_DESCRIPTION_POLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restPoleMockMvc.perform(put("/api/poles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPole)))
            .andExpect(status().isOk());

        // Validate the Pole in the database
        List<Pole> poleList = poleRepository.findAll();
        assertThat(poleList).hasSize(databaseSizeBeforeUpdate);
        Pole testPole = poleList.get(poleList.size() - 1);
        assertThat(testPole.getCodePole()).isEqualTo(UPDATED_CODE_POLE);
        assertThat(testPole.getLibellePole()).isEqualTo(UPDATED_LIBELLE_POLE);
        assertThat(testPole.getDescriptionPole()).isEqualTo(UPDATED_DESCRIPTION_POLE);
        assertThat(testPole.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testPole.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testPole.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testPole.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testPole.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingPole() throws Exception {
        int databaseSizeBeforeUpdate = poleRepository.findAll().size();

        // Create the Pole

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoleMockMvc.perform(put("/api/poles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pole)))
            .andExpect(status().isBadRequest());

        // Validate the Pole in the database
        List<Pole> poleList = poleRepository.findAll();
        assertThat(poleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePole() throws Exception {
        // Initialize the database
        poleService.save(pole);

        int databaseSizeBeforeDelete = poleRepository.findAll().size();

        // Delete the pole
        restPoleMockMvc.perform(delete("/api/poles/{id}", pole.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pole> poleList = poleRepository.findAll();
        assertThat(poleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
