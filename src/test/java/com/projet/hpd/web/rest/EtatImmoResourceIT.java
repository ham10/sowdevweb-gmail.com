package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.EtatImmo;
import com.projet.hpd.repository.EtatImmoRepository;
import com.projet.hpd.service.EtatImmoService;
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
 * Integration tests for the {@link EtatImmoResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class EtatImmoResourceIT {

    private static final String DEFAULT_CODE_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ETAT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_ETAT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_ETAT = "BBBBBBBBBB";

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
    private EtatImmoRepository etatImmoRepository;

    @Autowired
    private EtatImmoService etatImmoService;

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

    private MockMvc restEtatImmoMockMvc;

    private EtatImmo etatImmo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtatImmoResource etatImmoResource = new EtatImmoResource(etatImmoService);
        this.restEtatImmoMockMvc = MockMvcBuilders.standaloneSetup(etatImmoResource)
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
    public static EtatImmo createEntity(EntityManager em) {
        EtatImmo etatImmo = new EtatImmo()
            .codeEtat(DEFAULT_CODE_ETAT)
            .libelleEtat(DEFAULT_LIBELLE_ETAT)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return etatImmo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatImmo createUpdatedEntity(EntityManager em) {
        EtatImmo etatImmo = new EtatImmo()
            .codeEtat(UPDATED_CODE_ETAT)
            .libelleEtat(UPDATED_LIBELLE_ETAT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return etatImmo;
    }

    @BeforeEach
    public void initTest() {
        etatImmo = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtatImmo() throws Exception {
        int databaseSizeBeforeCreate = etatImmoRepository.findAll().size();

        // Create the EtatImmo
        restEtatImmoMockMvc.perform(post("/api/etat-immos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatImmo)))
            .andExpect(status().isCreated());

        // Validate the EtatImmo in the database
        List<EtatImmo> etatImmoList = etatImmoRepository.findAll();
        assertThat(etatImmoList).hasSize(databaseSizeBeforeCreate + 1);
        EtatImmo testEtatImmo = etatImmoList.get(etatImmoList.size() - 1);
        assertThat(testEtatImmo.getCodeEtat()).isEqualTo(DEFAULT_CODE_ETAT);
        assertThat(testEtatImmo.getLibelleEtat()).isEqualTo(DEFAULT_LIBELLE_ETAT);
        assertThat(testEtatImmo.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testEtatImmo.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testEtatImmo.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testEtatImmo.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testEtatImmo.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testEtatImmo.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createEtatImmoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etatImmoRepository.findAll().size();

        // Create the EtatImmo with an existing ID
        etatImmo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatImmoMockMvc.perform(post("/api/etat-immos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatImmo)))
            .andExpect(status().isBadRequest());

        // Validate the EtatImmo in the database
        List<EtatImmo> etatImmoList = etatImmoRepository.findAll();
        assertThat(etatImmoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEtatImmos() throws Exception {
        // Initialize the database
        etatImmoRepository.saveAndFlush(etatImmo);

        // Get all the etatImmoList
        restEtatImmoMockMvc.perform(get("/api/etat-immos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatImmo.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeEtat").value(hasItem(DEFAULT_CODE_ETAT)))
            .andExpect(jsonPath("$.[*].libelleEtat").value(hasItem(DEFAULT_LIBELLE_ETAT)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getEtatImmo() throws Exception {
        // Initialize the database
        etatImmoRepository.saveAndFlush(etatImmo);

        // Get the etatImmo
        restEtatImmoMockMvc.perform(get("/api/etat-immos/{id}", etatImmo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etatImmo.getId().intValue()))
            .andExpect(jsonPath("$.codeEtat").value(DEFAULT_CODE_ETAT))
            .andExpect(jsonPath("$.libelleEtat").value(DEFAULT_LIBELLE_ETAT))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEtatImmo() throws Exception {
        // Get the etatImmo
        restEtatImmoMockMvc.perform(get("/api/etat-immos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtatImmo() throws Exception {
        // Initialize the database
        etatImmoService.save(etatImmo);

        int databaseSizeBeforeUpdate = etatImmoRepository.findAll().size();

        // Update the etatImmo
        EtatImmo updatedEtatImmo = etatImmoRepository.findById(etatImmo.getId()).get();
        // Disconnect from session so that the updates on updatedEtatImmo are not directly saved in db
        em.detach(updatedEtatImmo);
        updatedEtatImmo
            .codeEtat(UPDATED_CODE_ETAT)
            .libelleEtat(UPDATED_LIBELLE_ETAT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restEtatImmoMockMvc.perform(put("/api/etat-immos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtatImmo)))
            .andExpect(status().isOk());

        // Validate the EtatImmo in the database
        List<EtatImmo> etatImmoList = etatImmoRepository.findAll();
        assertThat(etatImmoList).hasSize(databaseSizeBeforeUpdate);
        EtatImmo testEtatImmo = etatImmoList.get(etatImmoList.size() - 1);
        assertThat(testEtatImmo.getCodeEtat()).isEqualTo(UPDATED_CODE_ETAT);
        assertThat(testEtatImmo.getLibelleEtat()).isEqualTo(UPDATED_LIBELLE_ETAT);
        assertThat(testEtatImmo.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testEtatImmo.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testEtatImmo.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testEtatImmo.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testEtatImmo.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testEtatImmo.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingEtatImmo() throws Exception {
        int databaseSizeBeforeUpdate = etatImmoRepository.findAll().size();

        // Create the EtatImmo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatImmoMockMvc.perform(put("/api/etat-immos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatImmo)))
            .andExpect(status().isBadRequest());

        // Validate the EtatImmo in the database
        List<EtatImmo> etatImmoList = etatImmoRepository.findAll();
        assertThat(etatImmoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtatImmo() throws Exception {
        // Initialize the database
        etatImmoService.save(etatImmo);

        int databaseSizeBeforeDelete = etatImmoRepository.findAll().size();

        // Delete the etatImmo
        restEtatImmoMockMvc.perform(delete("/api/etat-immos/{id}", etatImmo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtatImmo> etatImmoList = etatImmoRepository.findAll();
        assertThat(etatImmoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
