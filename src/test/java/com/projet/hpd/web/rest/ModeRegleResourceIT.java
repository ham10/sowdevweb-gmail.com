package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.ModeRegle;
import com.projet.hpd.repository.ModeRegleRepository;
import com.projet.hpd.service.ModeRegleService;
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
 * Integration tests for the {@link ModeRegleResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class ModeRegleResourceIT {

    private static final String DEFAULT_CODE_MODE_REGLE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_MODE_REGLE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_MODE_REGLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_MODE_REGLE = "BBBBBBBBBB";

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
    private ModeRegleRepository modeRegleRepository;

    @Autowired
    private ModeRegleService modeRegleService;

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

    private MockMvc restModeRegleMockMvc;

    private ModeRegle modeRegle;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModeRegleResource modeRegleResource = new ModeRegleResource(modeRegleService);
        this.restModeRegleMockMvc = MockMvcBuilders.standaloneSetup(modeRegleResource)
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
    public static ModeRegle createEntity(EntityManager em) {
        ModeRegle modeRegle = new ModeRegle()
            .codeModeRegle(DEFAULT_CODE_MODE_REGLE)
            .libelleModeRegle(DEFAULT_LIBELLE_MODE_REGLE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return modeRegle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModeRegle createUpdatedEntity(EntityManager em) {
        ModeRegle modeRegle = new ModeRegle()
            .codeModeRegle(UPDATED_CODE_MODE_REGLE)
            .libelleModeRegle(UPDATED_LIBELLE_MODE_REGLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return modeRegle;
    }

    @BeforeEach
    public void initTest() {
        modeRegle = createEntity(em);
    }

    @Test
    @Transactional
    public void createModeRegle() throws Exception {
        int databaseSizeBeforeCreate = modeRegleRepository.findAll().size();

        // Create the ModeRegle
        restModeRegleMockMvc.perform(post("/api/mode-regles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modeRegle)))
            .andExpect(status().isCreated());

        // Validate the ModeRegle in the database
        List<ModeRegle> modeRegleList = modeRegleRepository.findAll();
        assertThat(modeRegleList).hasSize(databaseSizeBeforeCreate + 1);
        ModeRegle testModeRegle = modeRegleList.get(modeRegleList.size() - 1);
        assertThat(testModeRegle.getCodeModeRegle()).isEqualTo(DEFAULT_CODE_MODE_REGLE);
        assertThat(testModeRegle.getLibelleModeRegle()).isEqualTo(DEFAULT_LIBELLE_MODE_REGLE);
        assertThat(testModeRegle.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testModeRegle.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testModeRegle.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testModeRegle.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testModeRegle.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createModeRegleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modeRegleRepository.findAll().size();

        // Create the ModeRegle with an existing ID
        modeRegle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModeRegleMockMvc.perform(post("/api/mode-regles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modeRegle)))
            .andExpect(status().isBadRequest());

        // Validate the ModeRegle in the database
        List<ModeRegle> modeRegleList = modeRegleRepository.findAll();
        assertThat(modeRegleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllModeRegles() throws Exception {
        // Initialize the database
        modeRegleRepository.saveAndFlush(modeRegle);

        // Get all the modeRegleList
        restModeRegleMockMvc.perform(get("/api/mode-regles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modeRegle.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeModeRegle").value(hasItem(DEFAULT_CODE_MODE_REGLE)))
            .andExpect(jsonPath("$.[*].libelleModeRegle").value(hasItem(DEFAULT_LIBELLE_MODE_REGLE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getModeRegle() throws Exception {
        // Initialize the database
        modeRegleRepository.saveAndFlush(modeRegle);

        // Get the modeRegle
        restModeRegleMockMvc.perform(get("/api/mode-regles/{id}", modeRegle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modeRegle.getId().intValue()))
            .andExpect(jsonPath("$.codeModeRegle").value(DEFAULT_CODE_MODE_REGLE))
            .andExpect(jsonPath("$.libelleModeRegle").value(DEFAULT_LIBELLE_MODE_REGLE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingModeRegle() throws Exception {
        // Get the modeRegle
        restModeRegleMockMvc.perform(get("/api/mode-regles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModeRegle() throws Exception {
        // Initialize the database
        modeRegleService.save(modeRegle);

        int databaseSizeBeforeUpdate = modeRegleRepository.findAll().size();

        // Update the modeRegle
        ModeRegle updatedModeRegle = modeRegleRepository.findById(modeRegle.getId()).get();
        // Disconnect from session so that the updates on updatedModeRegle are not directly saved in db
        em.detach(updatedModeRegle);
        updatedModeRegle
            .codeModeRegle(UPDATED_CODE_MODE_REGLE)
            .libelleModeRegle(UPDATED_LIBELLE_MODE_REGLE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restModeRegleMockMvc.perform(put("/api/mode-regles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedModeRegle)))
            .andExpect(status().isOk());

        // Validate the ModeRegle in the database
        List<ModeRegle> modeRegleList = modeRegleRepository.findAll();
        assertThat(modeRegleList).hasSize(databaseSizeBeforeUpdate);
        ModeRegle testModeRegle = modeRegleList.get(modeRegleList.size() - 1);
        assertThat(testModeRegle.getCodeModeRegle()).isEqualTo(UPDATED_CODE_MODE_REGLE);
        assertThat(testModeRegle.getLibelleModeRegle()).isEqualTo(UPDATED_LIBELLE_MODE_REGLE);
        assertThat(testModeRegle.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testModeRegle.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testModeRegle.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testModeRegle.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testModeRegle.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingModeRegle() throws Exception {
        int databaseSizeBeforeUpdate = modeRegleRepository.findAll().size();

        // Create the ModeRegle

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModeRegleMockMvc.perform(put("/api/mode-regles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(modeRegle)))
            .andExpect(status().isBadRequest());

        // Validate the ModeRegle in the database
        List<ModeRegle> modeRegleList = modeRegleRepository.findAll();
        assertThat(modeRegleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModeRegle() throws Exception {
        // Initialize the database
        modeRegleService.save(modeRegle);

        int databaseSizeBeforeDelete = modeRegleRepository.findAll().size();

        // Delete the modeRegle
        restModeRegleMockMvc.perform(delete("/api/mode-regles/{id}", modeRegle.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModeRegle> modeRegleList = modeRegleRepository.findAll();
        assertThat(modeRegleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
