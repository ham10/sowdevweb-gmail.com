package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Mouvement;
import com.projet.hpd.repository.MouvementRepository;
import com.projet.hpd.service.MouvementService;
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
 * Integration tests for the {@link MouvementResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class MouvementResourceIT {

    private static final String DEFAULT_CODE_MVT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_MVT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_MVT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_MVT = "BBBBBBBBBB";

    private static final Integer DEFAULT_STOCK_ENTRANT = 1;
    private static final Integer UPDATED_STOCK_ENTRANT = 2;

    private static final Integer DEFAULT_STOCK_SORTANT = 1;
    private static final Integer UPDATED_STOCK_SORTANT = 2;

    @Autowired
    private MouvementRepository mouvementRepository;

    @Autowired
    private MouvementService mouvementService;

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

    private MockMvc restMouvementMockMvc;

    private Mouvement mouvement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MouvementResource mouvementResource = new MouvementResource(mouvementService);
        this.restMouvementMockMvc = MockMvcBuilders.standaloneSetup(mouvementResource)
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
    public static Mouvement createEntity(EntityManager em) {
        Mouvement mouvement = new Mouvement()
            .codeMvt(DEFAULT_CODE_MVT)
            .libelleMvt(DEFAULT_LIBELLE_MVT)
            .stockEntrant(DEFAULT_STOCK_ENTRANT)
            .stockSortant(DEFAULT_STOCK_SORTANT);
        return mouvement;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mouvement createUpdatedEntity(EntityManager em) {
        Mouvement mouvement = new Mouvement()
            .codeMvt(UPDATED_CODE_MVT)
            .libelleMvt(UPDATED_LIBELLE_MVT)
            .stockEntrant(UPDATED_STOCK_ENTRANT)
            .stockSortant(UPDATED_STOCK_SORTANT);
        return mouvement;
    }

    @BeforeEach
    public void initTest() {
        mouvement = createEntity(em);
    }

    @Test
    @Transactional
    public void createMouvement() throws Exception {
        int databaseSizeBeforeCreate = mouvementRepository.findAll().size();

        // Create the Mouvement
        restMouvementMockMvc.perform(post("/api/mouvements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvement)))
            .andExpect(status().isCreated());

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeCreate + 1);
        Mouvement testMouvement = mouvementList.get(mouvementList.size() - 1);
        assertThat(testMouvement.getCodeMvt()).isEqualTo(DEFAULT_CODE_MVT);
        assertThat(testMouvement.getLibelleMvt()).isEqualTo(DEFAULT_LIBELLE_MVT);
        assertThat(testMouvement.getStockEntrant()).isEqualTo(DEFAULT_STOCK_ENTRANT);
        assertThat(testMouvement.getStockSortant()).isEqualTo(DEFAULT_STOCK_SORTANT);
    }

    @Test
    @Transactional
    public void createMouvementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mouvementRepository.findAll().size();

        // Create the Mouvement with an existing ID
        mouvement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMouvementMockMvc.perform(post("/api/mouvements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvement)))
            .andExpect(status().isBadRequest());

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMouvements() throws Exception {
        // Initialize the database
        mouvementRepository.saveAndFlush(mouvement);

        // Get all the mouvementList
        restMouvementMockMvc.perform(get("/api/mouvements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mouvement.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeMvt").value(hasItem(DEFAULT_CODE_MVT)))
            .andExpect(jsonPath("$.[*].libelleMvt").value(hasItem(DEFAULT_LIBELLE_MVT)))
            .andExpect(jsonPath("$.[*].stockEntrant").value(hasItem(DEFAULT_STOCK_ENTRANT)))
            .andExpect(jsonPath("$.[*].stockSortant").value(hasItem(DEFAULT_STOCK_SORTANT)));
    }
    
    @Test
    @Transactional
    public void getMouvement() throws Exception {
        // Initialize the database
        mouvementRepository.saveAndFlush(mouvement);

        // Get the mouvement
        restMouvementMockMvc.perform(get("/api/mouvements/{id}", mouvement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mouvement.getId().intValue()))
            .andExpect(jsonPath("$.codeMvt").value(DEFAULT_CODE_MVT))
            .andExpect(jsonPath("$.libelleMvt").value(DEFAULT_LIBELLE_MVT))
            .andExpect(jsonPath("$.stockEntrant").value(DEFAULT_STOCK_ENTRANT))
            .andExpect(jsonPath("$.stockSortant").value(DEFAULT_STOCK_SORTANT));
    }

    @Test
    @Transactional
    public void getNonExistingMouvement() throws Exception {
        // Get the mouvement
        restMouvementMockMvc.perform(get("/api/mouvements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMouvement() throws Exception {
        // Initialize the database
        mouvementService.save(mouvement);

        int databaseSizeBeforeUpdate = mouvementRepository.findAll().size();

        // Update the mouvement
        Mouvement updatedMouvement = mouvementRepository.findById(mouvement.getId()).get();
        // Disconnect from session so that the updates on updatedMouvement are not directly saved in db
        em.detach(updatedMouvement);
        updatedMouvement
            .codeMvt(UPDATED_CODE_MVT)
            .libelleMvt(UPDATED_LIBELLE_MVT)
            .stockEntrant(UPDATED_STOCK_ENTRANT)
            .stockSortant(UPDATED_STOCK_SORTANT);

        restMouvementMockMvc.perform(put("/api/mouvements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMouvement)))
            .andExpect(status().isOk());

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeUpdate);
        Mouvement testMouvement = mouvementList.get(mouvementList.size() - 1);
        assertThat(testMouvement.getCodeMvt()).isEqualTo(UPDATED_CODE_MVT);
        assertThat(testMouvement.getLibelleMvt()).isEqualTo(UPDATED_LIBELLE_MVT);
        assertThat(testMouvement.getStockEntrant()).isEqualTo(UPDATED_STOCK_ENTRANT);
        assertThat(testMouvement.getStockSortant()).isEqualTo(UPDATED_STOCK_SORTANT);
    }

    @Test
    @Transactional
    public void updateNonExistingMouvement() throws Exception {
        int databaseSizeBeforeUpdate = mouvementRepository.findAll().size();

        // Create the Mouvement

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMouvementMockMvc.perform(put("/api/mouvements")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(mouvement)))
            .andExpect(status().isBadRequest());

        // Validate the Mouvement in the database
        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMouvement() throws Exception {
        // Initialize the database
        mouvementService.save(mouvement);

        int databaseSizeBeforeDelete = mouvementRepository.findAll().size();

        // Delete the mouvement
        restMouvementMockMvc.perform(delete("/api/mouvements/{id}", mouvement.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mouvement> mouvementList = mouvementRepository.findAll();
        assertThat(mouvementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
