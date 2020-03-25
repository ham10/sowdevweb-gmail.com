package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.EtatBonCom;
import com.projet.hpd.repository.EtatBonComRepository;
import com.projet.hpd.service.EtatBonComService;
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
 * Integration tests for the {@link EtatBonComResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class EtatBonComResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private EtatBonComRepository etatBonComRepository;

    @Autowired
    private EtatBonComService etatBonComService;

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

    private MockMvc restEtatBonComMockMvc;

    private EtatBonCom etatBonCom;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtatBonComResource etatBonComResource = new EtatBonComResource(etatBonComService);
        this.restEtatBonComMockMvc = MockMvcBuilders.standaloneSetup(etatBonComResource)
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
    public static EtatBonCom createEntity(EntityManager em) {
        EtatBonCom etatBonCom = new EtatBonCom()
            .libelle(DEFAULT_LIBELLE)
            .code(DEFAULT_CODE);
        return etatBonCom;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtatBonCom createUpdatedEntity(EntityManager em) {
        EtatBonCom etatBonCom = new EtatBonCom()
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);
        return etatBonCom;
    }

    @BeforeEach
    public void initTest() {
        etatBonCom = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtatBonCom() throws Exception {
        int databaseSizeBeforeCreate = etatBonComRepository.findAll().size();

        // Create the EtatBonCom
        restEtatBonComMockMvc.perform(post("/api/etat-bon-coms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatBonCom)))
            .andExpect(status().isCreated());

        // Validate the EtatBonCom in the database
        List<EtatBonCom> etatBonComList = etatBonComRepository.findAll();
        assertThat(etatBonComList).hasSize(databaseSizeBeforeCreate + 1);
        EtatBonCom testEtatBonCom = etatBonComList.get(etatBonComList.size() - 1);
        assertThat(testEtatBonCom.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testEtatBonCom.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createEtatBonComWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etatBonComRepository.findAll().size();

        // Create the EtatBonCom with an existing ID
        etatBonCom.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtatBonComMockMvc.perform(post("/api/etat-bon-coms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatBonCom)))
            .andExpect(status().isBadRequest());

        // Validate the EtatBonCom in the database
        List<EtatBonCom> etatBonComList = etatBonComRepository.findAll();
        assertThat(etatBonComList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEtatBonComs() throws Exception {
        // Initialize the database
        etatBonComRepository.saveAndFlush(etatBonCom);

        // Get all the etatBonComList
        restEtatBonComMockMvc.perform(get("/api/etat-bon-coms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etatBonCom.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getEtatBonCom() throws Exception {
        // Initialize the database
        etatBonComRepository.saveAndFlush(etatBonCom);

        // Get the etatBonCom
        restEtatBonComMockMvc.perform(get("/api/etat-bon-coms/{id}", etatBonCom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etatBonCom.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    public void getNonExistingEtatBonCom() throws Exception {
        // Get the etatBonCom
        restEtatBonComMockMvc.perform(get("/api/etat-bon-coms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtatBonCom() throws Exception {
        // Initialize the database
        etatBonComService.save(etatBonCom);

        int databaseSizeBeforeUpdate = etatBonComRepository.findAll().size();

        // Update the etatBonCom
        EtatBonCom updatedEtatBonCom = etatBonComRepository.findById(etatBonCom.getId()).get();
        // Disconnect from session so that the updates on updatedEtatBonCom are not directly saved in db
        em.detach(updatedEtatBonCom);
        updatedEtatBonCom
            .libelle(UPDATED_LIBELLE)
            .code(UPDATED_CODE);

        restEtatBonComMockMvc.perform(put("/api/etat-bon-coms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtatBonCom)))
            .andExpect(status().isOk());

        // Validate the EtatBonCom in the database
        List<EtatBonCom> etatBonComList = etatBonComRepository.findAll();
        assertThat(etatBonComList).hasSize(databaseSizeBeforeUpdate);
        EtatBonCom testEtatBonCom = etatBonComList.get(etatBonComList.size() - 1);
        assertThat(testEtatBonCom.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testEtatBonCom.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingEtatBonCom() throws Exception {
        int databaseSizeBeforeUpdate = etatBonComRepository.findAll().size();

        // Create the EtatBonCom

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtatBonComMockMvc.perform(put("/api/etat-bon-coms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etatBonCom)))
            .andExpect(status().isBadRequest());

        // Validate the EtatBonCom in the database
        List<EtatBonCom> etatBonComList = etatBonComRepository.findAll();
        assertThat(etatBonComList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtatBonCom() throws Exception {
        // Initialize the database
        etatBonComService.save(etatBonCom);

        int databaseSizeBeforeDelete = etatBonComRepository.findAll().size();

        // Delete the etatBonCom
        restEtatBonComMockMvc.perform(delete("/api/etat-bon-coms/{id}", etatBonCom.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EtatBonCom> etatBonComList = etatBonComRepository.findAll();
        assertThat(etatBonComList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
