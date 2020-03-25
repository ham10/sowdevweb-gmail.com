package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.EtatRdv;
import com.projet.hpd.repository.EtatRdvRepository;
import com.projet.hpd.service.EtatRdvService;
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
import java.util.List;

import static com.projet.hpd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EtatRdvResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class EtatRdvResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private EtatRdvRepository etatRdvRepository;

    @Autowired
    private EtatRdvService etatRdvService;

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

    private MockMvc restEtatRdvMockMvc;

    private EtatRdv etatRdv;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtatRdvResource etatRdvResource = new EtatRdvResource(etatRdvService);
        this.restEtatRdvMockMvc = MockMvcBuilders.standaloneSetup(etatRdvResource)
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
    public static EtatRdv createEntity(EntityManager em) {
        EtatRdv etatRdv = new EtatRdv()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return etatRdv;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatRdv createUpdatedEntity(EntityManager em) {
        EtatRdv etatRdv = new EtatRdv()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return etatRdv;
    }

    @BeforeEach
    public void initTest() {
        etatRdv = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtatRdv() throws Exception {
        int databaseSizeBeforeCreate = etatRdvRepository.findAll().size();

        // Create the EtatRdv
        restEtatRdvMockMvc.perform(post("/api/etat-rdvs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatRdv)))
            .andExpect(status().isCreated());

        // Validate the EtatRdv in the database
        List<EtatRdv> etatRdvList = etatRdvRepository.findAll();
        assertThat(etatRdvList).hasSize(databaseSizeBeforeCreate + 1);
        EtatRdv testEtatRdv = etatRdvList.get(etatRdvList.size() - 1);
        assertThat(testEtatRdv.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEtatRdv.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createEtatRdvWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etatRdvRepository.findAll().size();

        // Create the EtatRdv with an existing ID
        etatRdv.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatRdvMockMvc.perform(post("/api/etat-rdvs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatRdv)))
            .andExpect(status().isBadRequest());

        // Validate the EtatRdv in the database
        List<EtatRdv> etatRdvList = etatRdvRepository.findAll();
        assertThat(etatRdvList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEtatRdvs() throws Exception {
        // Initialize the database
        etatRdvRepository.saveAndFlush(etatRdv);

        // Get all the etatRdvList
        restEtatRdvMockMvc.perform(get("/api/etat-rdvs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatRdv.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getEtatRdv() throws Exception {
        // Initialize the database
        etatRdvRepository.saveAndFlush(etatRdv);

        // Get the etatRdv
        restEtatRdvMockMvc.perform(get("/api/etat-rdvs/{id}", etatRdv.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etatRdv.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    public void getNonExistingEtatRdv() throws Exception {
        // Get the etatRdv
        restEtatRdvMockMvc.perform(get("/api/etat-rdvs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtatRdv() throws Exception {
        // Initialize the database
        etatRdvService.save(etatRdv);

        int databaseSizeBeforeUpdate = etatRdvRepository.findAll().size();

        // Update the etatRdv
        EtatRdv updatedEtatRdv = etatRdvRepository.findById(etatRdv.getId()).get();
        // Disconnect from session so that the updates on updatedEtatRdv are not directly saved in db
        em.detach(updatedEtatRdv);
        updatedEtatRdv
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restEtatRdvMockMvc.perform(put("/api/etat-rdvs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtatRdv)))
            .andExpect(status().isOk());

        // Validate the EtatRdv in the database
        List<EtatRdv> etatRdvList = etatRdvRepository.findAll();
        assertThat(etatRdvList).hasSize(databaseSizeBeforeUpdate);
        EtatRdv testEtatRdv = etatRdvList.get(etatRdvList.size() - 1);
        assertThat(testEtatRdv.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEtatRdv.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingEtatRdv() throws Exception {
        int databaseSizeBeforeUpdate = etatRdvRepository.findAll().size();

        // Create the EtatRdv

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatRdvMockMvc.perform(put("/api/etat-rdvs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatRdv)))
            .andExpect(status().isBadRequest());

        // Validate the EtatRdv in the database
        List<EtatRdv> etatRdvList = etatRdvRepository.findAll();
        assertThat(etatRdvList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtatRdv() throws Exception {
        // Initialize the database
        etatRdvService.save(etatRdv);

        int databaseSizeBeforeDelete = etatRdvRepository.findAll().size();

        // Delete the etatRdv
        restEtatRdvMockMvc.perform(delete("/api/etat-rdvs/{id}", etatRdv.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtatRdv> etatRdvList = etatRdvRepository.findAll();
        assertThat(etatRdvList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
