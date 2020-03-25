package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.ProdFournis;
import com.projet.hpd.repository.ProdFournisRepository;
import com.projet.hpd.service.ProdFournisService;
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
 * Integration tests for the {@link ProdFournisResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class ProdFournisResourceIT {

    private static final String DEFAULT_STOCK = "AAAAAAAAAA";
    private static final String UPDATED_STOCK = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private ProdFournisRepository prodFournisRepository;

    @Autowired
    private ProdFournisService prodFournisService;

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

    private MockMvc restProdFournisMockMvc;

    private ProdFournis prodFournis;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProdFournisResource prodFournisResource = new ProdFournisResource(prodFournisService);
        this.restProdFournisMockMvc = MockMvcBuilders.standaloneSetup(prodFournisResource)
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
    public static ProdFournis createEntity(EntityManager em) {
        ProdFournis prodFournis = new ProdFournis()
            .stock(DEFAULT_STOCK)
            .nom(DEFAULT_NOM);
        return prodFournis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdFournis createUpdatedEntity(EntityManager em) {
        ProdFournis prodFournis = new ProdFournis()
            .stock(UPDATED_STOCK)
            .nom(UPDATED_NOM);
        return prodFournis;
    }

    @BeforeEach
    public void initTest() {
        prodFournis = createEntity(em);
    }

    @Test
    @Transactional
    public void createProdFournis() throws Exception {
        int databaseSizeBeforeCreate = prodFournisRepository.findAll().size();

        // Create the ProdFournis
        restProdFournisMockMvc.perform(post("/api/prod-fournis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prodFournis)))
            .andExpect(status().isCreated());

        // Validate the ProdFournis in the database
        List<ProdFournis> prodFournisList = prodFournisRepository.findAll();
        assertThat(prodFournisList).hasSize(databaseSizeBeforeCreate + 1);
        ProdFournis testProdFournis = prodFournisList.get(prodFournisList.size() - 1);
        assertThat(testProdFournis.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testProdFournis.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    public void createProdFournisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prodFournisRepository.findAll().size();

        // Create the ProdFournis with an existing ID
        prodFournis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdFournisMockMvc.perform(post("/api/prod-fournis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prodFournis)))
            .andExpect(status().isBadRequest());

        // Validate the ProdFournis in the database
        List<ProdFournis> prodFournisList = prodFournisRepository.findAll();
        assertThat(prodFournisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProdFournis() throws Exception {
        // Initialize the database
        prodFournisRepository.saveAndFlush(prodFournis);

        // Get all the prodFournisList
        restProdFournisMockMvc.perform(get("/api/prod-fournis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prodFournis.getId().intValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }
    
    @Test
    @Transactional
    public void getProdFournis() throws Exception {
        // Initialize the database
        prodFournisRepository.saveAndFlush(prodFournis);

        // Get the prodFournis
        restProdFournisMockMvc.perform(get("/api/prod-fournis/{id}", prodFournis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prodFournis.getId().intValue()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }

    @Test
    @Transactional
    public void getNonExistingProdFournis() throws Exception {
        // Get the prodFournis
        restProdFournisMockMvc.perform(get("/api/prod-fournis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProdFournis() throws Exception {
        // Initialize the database
        prodFournisService.save(prodFournis);

        int databaseSizeBeforeUpdate = prodFournisRepository.findAll().size();

        // Update the prodFournis
        ProdFournis updatedProdFournis = prodFournisRepository.findById(prodFournis.getId()).get();
        // Disconnect from session so that the updates on updatedProdFournis are not directly saved in db
        em.detach(updatedProdFournis);
        updatedProdFournis
            .stock(UPDATED_STOCK)
            .nom(UPDATED_NOM);

        restProdFournisMockMvc.perform(put("/api/prod-fournis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProdFournis)))
            .andExpect(status().isOk());

        // Validate the ProdFournis in the database
        List<ProdFournis> prodFournisList = prodFournisRepository.findAll();
        assertThat(prodFournisList).hasSize(databaseSizeBeforeUpdate);
        ProdFournis testProdFournis = prodFournisList.get(prodFournisList.size() - 1);
        assertThat(testProdFournis.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testProdFournis.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    public void updateNonExistingProdFournis() throws Exception {
        int databaseSizeBeforeUpdate = prodFournisRepository.findAll().size();

        // Create the ProdFournis

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdFournisMockMvc.perform(put("/api/prod-fournis")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prodFournis)))
            .andExpect(status().isBadRequest());

        // Validate the ProdFournis in the database
        List<ProdFournis> prodFournisList = prodFournisRepository.findAll();
        assertThat(prodFournisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProdFournis() throws Exception {
        // Initialize the database
        prodFournisService.save(prodFournis);

        int databaseSizeBeforeDelete = prodFournisRepository.findAll().size();

        // Delete the prodFournis
        restProdFournisMockMvc.perform(delete("/api/prod-fournis/{id}", prodFournis.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProdFournis> prodFournisList = prodFournisRepository.findAll();
        assertThat(prodFournisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
