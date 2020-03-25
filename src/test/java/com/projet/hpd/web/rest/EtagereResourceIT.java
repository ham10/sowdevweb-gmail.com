package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Etagere;
import com.projet.hpd.repository.EtagereRepository;
import com.projet.hpd.service.EtagereService;
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
 * Integration tests for the {@link EtagereResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class EtagereResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private EtagereRepository etagereRepository;

    @Autowired
    private EtagereService etagereService;

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

    private MockMvc restEtagereMockMvc;

    private Etagere etagere;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtagereResource etagereResource = new EtagereResource(etagereService);
        this.restEtagereMockMvc = MockMvcBuilders.standaloneSetup(etagereResource)
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
    public static Etagere createEntity(EntityManager em) {
        Etagere etagere = new Etagere()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return etagere;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etagere createUpdatedEntity(EntityManager em) {
        Etagere etagere = new Etagere()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return etagere;
    }

    @BeforeEach
    public void initTest() {
        etagere = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtagere() throws Exception {
        int databaseSizeBeforeCreate = etagereRepository.findAll().size();

        // Create the Etagere
        restEtagereMockMvc.perform(post("/api/etageres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etagere)))
            .andExpect(status().isCreated());

        // Validate the Etagere in the database
        List<Etagere> etagereList = etagereRepository.findAll();
        assertThat(etagereList).hasSize(databaseSizeBeforeCreate + 1);
        Etagere testEtagere = etagereList.get(etagereList.size() - 1);
        assertThat(testEtagere.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEtagere.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createEtagereWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etagereRepository.findAll().size();

        // Create the Etagere with an existing ID
        etagere.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtagereMockMvc.perform(post("/api/etageres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etagere)))
            .andExpect(status().isBadRequest());

        // Validate the Etagere in the database
        List<Etagere> etagereList = etagereRepository.findAll();
        assertThat(etagereList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEtageres() throws Exception {
        // Initialize the database
        etagereRepository.saveAndFlush(etagere);

        // Get all the etagereList
        restEtagereMockMvc.perform(get("/api/etageres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etagere.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getEtagere() throws Exception {
        // Initialize the database
        etagereRepository.saveAndFlush(etagere);

        // Get the etagere
        restEtagereMockMvc.perform(get("/api/etageres/{id}", etagere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(etagere.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }

    @Test
    @Transactional
    public void getNonExistingEtagere() throws Exception {
        // Get the etagere
        restEtagereMockMvc.perform(get("/api/etageres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtagere() throws Exception {
        // Initialize the database
        etagereService.save(etagere);

        int databaseSizeBeforeUpdate = etagereRepository.findAll().size();

        // Update the etagere
        Etagere updatedEtagere = etagereRepository.findById(etagere.getId()).get();
        // Disconnect from session so that the updates on updatedEtagere are not directly saved in db
        em.detach(updatedEtagere);
        updatedEtagere
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restEtagereMockMvc.perform(put("/api/etageres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtagere)))
            .andExpect(status().isOk());

        // Validate the Etagere in the database
        List<Etagere> etagereList = etagereRepository.findAll();
        assertThat(etagereList).hasSize(databaseSizeBeforeUpdate);
        Etagere testEtagere = etagereList.get(etagereList.size() - 1);
        assertThat(testEtagere.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEtagere.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingEtagere() throws Exception {
        int databaseSizeBeforeUpdate = etagereRepository.findAll().size();

        // Create the Etagere

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEtagereMockMvc.perform(put("/api/etageres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(etagere)))
            .andExpect(status().isBadRequest());

        // Validate the Etagere in the database
        List<Etagere> etagereList = etagereRepository.findAll();
        assertThat(etagereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEtagere() throws Exception {
        // Initialize the database
        etagereService.save(etagere);

        int databaseSizeBeforeDelete = etagereRepository.findAll().size();

        // Delete the etagere
        restEtagereMockMvc.perform(delete("/api/etageres/{id}", etagere.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Etagere> etagereList = etagereRepository.findAll();
        assertThat(etagereList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
