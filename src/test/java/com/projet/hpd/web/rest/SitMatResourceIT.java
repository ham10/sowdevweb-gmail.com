package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.SitMat;
import com.projet.hpd.repository.SitMatRepository;
import com.projet.hpd.service.SitMatService;
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
 * Integration tests for the {@link SitMatResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class SitMatResourceIT {

    private static final String DEFAULT_CODE_SIT_MAT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_SIT_MAT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_SIT_MAT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_SIT_MAT = "BBBBBBBBBB";

    @Autowired
    private SitMatRepository sitMatRepository;

    @Autowired
    private SitMatService sitMatService;

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

    private MockMvc restSitMatMockMvc;

    private SitMat sitMat;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SitMatResource sitMatResource = new SitMatResource(sitMatService);
        this.restSitMatMockMvc = MockMvcBuilders.standaloneSetup(sitMatResource)
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
    public static SitMat createEntity(EntityManager em) {
        SitMat sitMat = new SitMat()
            .codeSitMat(DEFAULT_CODE_SIT_MAT)
            .libelleSitMat(DEFAULT_LIBELLE_SIT_MAT);
        return sitMat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SitMat createUpdatedEntity(EntityManager em) {
        SitMat sitMat = new SitMat()
            .codeSitMat(UPDATED_CODE_SIT_MAT)
            .libelleSitMat(UPDATED_LIBELLE_SIT_MAT);
        return sitMat;
    }

    @BeforeEach
    public void initTest() {
        sitMat = createEntity(em);
    }

    @Test
    @Transactional
    public void createSitMat() throws Exception {
        int databaseSizeBeforeCreate = sitMatRepository.findAll().size();

        // Create the SitMat
        restSitMatMockMvc.perform(post("/api/sit-mats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sitMat)))
            .andExpect(status().isCreated());

        // Validate the SitMat in the database
        List<SitMat> sitMatList = sitMatRepository.findAll();
        assertThat(sitMatList).hasSize(databaseSizeBeforeCreate + 1);
        SitMat testSitMat = sitMatList.get(sitMatList.size() - 1);
        assertThat(testSitMat.getCodeSitMat()).isEqualTo(DEFAULT_CODE_SIT_MAT);
        assertThat(testSitMat.getLibelleSitMat()).isEqualTo(DEFAULT_LIBELLE_SIT_MAT);
    }

    @Test
    @Transactional
    public void createSitMatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sitMatRepository.findAll().size();

        // Create the SitMat with an existing ID
        sitMat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSitMatMockMvc.perform(post("/api/sit-mats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sitMat)))
            .andExpect(status().isBadRequest());

        // Validate the SitMat in the database
        List<SitMat> sitMatList = sitMatRepository.findAll();
        assertThat(sitMatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSitMats() throws Exception {
        // Initialize the database
        sitMatRepository.saveAndFlush(sitMat);

        // Get all the sitMatList
        restSitMatMockMvc.perform(get("/api/sit-mats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sitMat.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeSitMat").value(hasItem(DEFAULT_CODE_SIT_MAT)))
            .andExpect(jsonPath("$.[*].libelleSitMat").value(hasItem(DEFAULT_LIBELLE_SIT_MAT)));
    }
    
    @Test
    @Transactional
    public void getSitMat() throws Exception {
        // Initialize the database
        sitMatRepository.saveAndFlush(sitMat);

        // Get the sitMat
        restSitMatMockMvc.perform(get("/api/sit-mats/{id}", sitMat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sitMat.getId().intValue()))
            .andExpect(jsonPath("$.codeSitMat").value(DEFAULT_CODE_SIT_MAT))
            .andExpect(jsonPath("$.libelleSitMat").value(DEFAULT_LIBELLE_SIT_MAT));
    }

    @Test
    @Transactional
    public void getNonExistingSitMat() throws Exception {
        // Get the sitMat
        restSitMatMockMvc.perform(get("/api/sit-mats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSitMat() throws Exception {
        // Initialize the database
        sitMatService.save(sitMat);

        int databaseSizeBeforeUpdate = sitMatRepository.findAll().size();

        // Update the sitMat
        SitMat updatedSitMat = sitMatRepository.findById(sitMat.getId()).get();
        // Disconnect from session so that the updates on updatedSitMat are not directly saved in db
        em.detach(updatedSitMat);
        updatedSitMat
            .codeSitMat(UPDATED_CODE_SIT_MAT)
            .libelleSitMat(UPDATED_LIBELLE_SIT_MAT);

        restSitMatMockMvc.perform(put("/api/sit-mats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSitMat)))
            .andExpect(status().isOk());

        // Validate the SitMat in the database
        List<SitMat> sitMatList = sitMatRepository.findAll();
        assertThat(sitMatList).hasSize(databaseSizeBeforeUpdate);
        SitMat testSitMat = sitMatList.get(sitMatList.size() - 1);
        assertThat(testSitMat.getCodeSitMat()).isEqualTo(UPDATED_CODE_SIT_MAT);
        assertThat(testSitMat.getLibelleSitMat()).isEqualTo(UPDATED_LIBELLE_SIT_MAT);
    }

    @Test
    @Transactional
    public void updateNonExistingSitMat() throws Exception {
        int databaseSizeBeforeUpdate = sitMatRepository.findAll().size();

        // Create the SitMat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSitMatMockMvc.perform(put("/api/sit-mats")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sitMat)))
            .andExpect(status().isBadRequest());

        // Validate the SitMat in the database
        List<SitMat> sitMatList = sitMatRepository.findAll();
        assertThat(sitMatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSitMat() throws Exception {
        // Initialize the database
        sitMatService.save(sitMat);

        int databaseSizeBeforeDelete = sitMatRepository.findAll().size();

        // Delete the sitMat
        restSitMatMockMvc.perform(delete("/api/sit-mats/{id}", sitMat.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SitMat> sitMatList = sitMatRepository.findAll();
        assertThat(sitMatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
