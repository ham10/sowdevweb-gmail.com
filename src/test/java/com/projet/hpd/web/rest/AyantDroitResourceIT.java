package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.AyantDroit;
import com.projet.hpd.repository.AyantDroitRepository;
import com.projet.hpd.service.AyantDroitService;
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
 * Integration tests for the {@link AyantDroitResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class AyantDroitResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_PARENTE = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_PARENTE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAIS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAIS = LocalDate.now(ZoneId.systemDefault());

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
    private AyantDroitRepository ayantDroitRepository;

    @Autowired
    private AyantDroitService ayantDroitService;

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

    private MockMvc restAyantDroitMockMvc;

    private AyantDroit ayantDroit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AyantDroitResource ayantDroitResource = new AyantDroitResource(ayantDroitService);
        this.restAyantDroitMockMvc = MockMvcBuilders.standaloneSetup(ayantDroitResource)
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
    public static AyantDroit createEntity(EntityManager em) {
        AyantDroit ayantDroit = new AyantDroit()
            .code(DEFAULT_CODE)
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .lienParente(DEFAULT_LIEN_PARENTE)
            .dateNais(DEFAULT_DATE_NAIS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return ayantDroit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AyantDroit createUpdatedEntity(EntityManager em) {
        AyantDroit ayantDroit = new AyantDroit()
            .code(UPDATED_CODE)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .lienParente(UPDATED_LIEN_PARENTE)
            .dateNais(UPDATED_DATE_NAIS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return ayantDroit;
    }

    @BeforeEach
    public void initTest() {
        ayantDroit = createEntity(em);
    }

    @Test
    @Transactional
    public void createAyantDroit() throws Exception {
        int databaseSizeBeforeCreate = ayantDroitRepository.findAll().size();

        // Create the AyantDroit
        restAyantDroitMockMvc.perform(post("/api/ayant-droits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ayantDroit)))
            .andExpect(status().isCreated());

        // Validate the AyantDroit in the database
        List<AyantDroit> ayantDroitList = ayantDroitRepository.findAll();
        assertThat(ayantDroitList).hasSize(databaseSizeBeforeCreate + 1);
        AyantDroit testAyantDroit = ayantDroitList.get(ayantDroitList.size() - 1);
        assertThat(testAyantDroit.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAyantDroit.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testAyantDroit.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAyantDroit.getLienParente()).isEqualTo(DEFAULT_LIEN_PARENTE);
        assertThat(testAyantDroit.getDateNais()).isEqualTo(DEFAULT_DATE_NAIS);
        assertThat(testAyantDroit.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testAyantDroit.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testAyantDroit.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testAyantDroit.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testAyantDroit.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testAyantDroit.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createAyantDroitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ayantDroitRepository.findAll().size();

        // Create the AyantDroit with an existing ID
        ayantDroit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAyantDroitMockMvc.perform(post("/api/ayant-droits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ayantDroit)))
            .andExpect(status().isBadRequest());

        // Validate the AyantDroit in the database
        List<AyantDroit> ayantDroitList = ayantDroitRepository.findAll();
        assertThat(ayantDroitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAyantDroits() throws Exception {
        // Initialize the database
        ayantDroitRepository.saveAndFlush(ayantDroit);

        // Get all the ayantDroitList
        restAyantDroitMockMvc.perform(get("/api/ayant-droits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ayantDroit.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].lienParente").value(hasItem(DEFAULT_LIEN_PARENTE)))
            .andExpect(jsonPath("$.[*].dateNais").value(hasItem(DEFAULT_DATE_NAIS.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getAyantDroit() throws Exception {
        // Initialize the database
        ayantDroitRepository.saveAndFlush(ayantDroit);

        // Get the ayantDroit
        restAyantDroitMockMvc.perform(get("/api/ayant-droits/{id}", ayantDroit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ayantDroit.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.lienParente").value(DEFAULT_LIEN_PARENTE))
            .andExpect(jsonPath("$.dateNais").value(DEFAULT_DATE_NAIS.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAyantDroit() throws Exception {
        // Get the ayantDroit
        restAyantDroitMockMvc.perform(get("/api/ayant-droits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAyantDroit() throws Exception {
        // Initialize the database
        ayantDroitService.save(ayantDroit);

        int databaseSizeBeforeUpdate = ayantDroitRepository.findAll().size();

        // Update the ayantDroit
        AyantDroit updatedAyantDroit = ayantDroitRepository.findById(ayantDroit.getId()).get();
        // Disconnect from session so that the updates on updatedAyantDroit are not directly saved in db
        em.detach(updatedAyantDroit);
        updatedAyantDroit
            .code(UPDATED_CODE)
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .lienParente(UPDATED_LIEN_PARENTE)
            .dateNais(UPDATED_DATE_NAIS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restAyantDroitMockMvc.perform(put("/api/ayant-droits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAyantDroit)))
            .andExpect(status().isOk());

        // Validate the AyantDroit in the database
        List<AyantDroit> ayantDroitList = ayantDroitRepository.findAll();
        assertThat(ayantDroitList).hasSize(databaseSizeBeforeUpdate);
        AyantDroit testAyantDroit = ayantDroitList.get(ayantDroitList.size() - 1);
        assertThat(testAyantDroit.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAyantDroit.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAyantDroit.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAyantDroit.getLienParente()).isEqualTo(UPDATED_LIEN_PARENTE);
        assertThat(testAyantDroit.getDateNais()).isEqualTo(UPDATED_DATE_NAIS);
        assertThat(testAyantDroit.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testAyantDroit.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testAyantDroit.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testAyantDroit.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testAyantDroit.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testAyantDroit.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingAyantDroit() throws Exception {
        int databaseSizeBeforeUpdate = ayantDroitRepository.findAll().size();

        // Create the AyantDroit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAyantDroitMockMvc.perform(put("/api/ayant-droits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ayantDroit)))
            .andExpect(status().isBadRequest());

        // Validate the AyantDroit in the database
        List<AyantDroit> ayantDroitList = ayantDroitRepository.findAll();
        assertThat(ayantDroitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAyantDroit() throws Exception {
        // Initialize the database
        ayantDroitService.save(ayantDroit);

        int databaseSizeBeforeDelete = ayantDroitRepository.findAll().size();

        // Delete the ayantDroit
        restAyantDroitMockMvc.perform(delete("/api/ayant-droits/{id}", ayantDroit.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AyantDroit> ayantDroitList = ayantDroitRepository.findAll();
        assertThat(ayantDroitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
