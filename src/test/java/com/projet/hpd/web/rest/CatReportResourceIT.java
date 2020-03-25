package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.CatReport;
import com.projet.hpd.repository.CatReportRepository;
import com.projet.hpd.service.CatReportService;
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
 * Integration tests for the {@link CatReportResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class CatReportResourceIT {

    private static final String DEFAULT_CODE_CATEG_REPORT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_CATEG_REPORT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_CATEG_REPORT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CATEG_REPORT = "BBBBBBBBBB";

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
    private CatReportRepository catReportRepository;

    @Autowired
    private CatReportService catReportService;

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

    private MockMvc restCatReportMockMvc;

    private CatReport catReport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CatReportResource catReportResource = new CatReportResource(catReportService);
        this.restCatReportMockMvc = MockMvcBuilders.standaloneSetup(catReportResource)
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
    public static CatReport createEntity(EntityManager em) {
        CatReport catReport = new CatReport()
            .codeCategReport(DEFAULT_CODE_CATEG_REPORT)
            .libelleCategReport(DEFAULT_LIBELLE_CATEG_REPORT)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return catReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatReport createUpdatedEntity(EntityManager em) {
        CatReport catReport = new CatReport()
            .codeCategReport(UPDATED_CODE_CATEG_REPORT)
            .libelleCategReport(UPDATED_LIBELLE_CATEG_REPORT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return catReport;
    }

    @BeforeEach
    public void initTest() {
        catReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatReport() throws Exception {
        int databaseSizeBeforeCreate = catReportRepository.findAll().size();

        // Create the CatReport
        restCatReportMockMvc.perform(post("/api/cat-reports")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catReport)))
            .andExpect(status().isCreated());

        // Validate the CatReport in the database
        List<CatReport> catReportList = catReportRepository.findAll();
        assertThat(catReportList).hasSize(databaseSizeBeforeCreate + 1);
        CatReport testCatReport = catReportList.get(catReportList.size() - 1);
        assertThat(testCatReport.getCodeCategReport()).isEqualTo(DEFAULT_CODE_CATEG_REPORT);
        assertThat(testCatReport.getLibelleCategReport()).isEqualTo(DEFAULT_LIBELLE_CATEG_REPORT);
        assertThat(testCatReport.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCatReport.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testCatReport.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testCatReport.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testCatReport.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createCatReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catReportRepository.findAll().size();

        // Create the CatReport with an existing ID
        catReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatReportMockMvc.perform(post("/api/cat-reports")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catReport)))
            .andExpect(status().isBadRequest());

        // Validate the CatReport in the database
        List<CatReport> catReportList = catReportRepository.findAll();
        assertThat(catReportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCatReports() throws Exception {
        // Initialize the database
        catReportRepository.saveAndFlush(catReport);

        // Get all the catReportList
        restCatReportMockMvc.perform(get("/api/cat-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeCategReport").value(hasItem(DEFAULT_CODE_CATEG_REPORT)))
            .andExpect(jsonPath("$.[*].libelleCategReport").value(hasItem(DEFAULT_LIBELLE_CATEG_REPORT)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getCatReport() throws Exception {
        // Initialize the database
        catReportRepository.saveAndFlush(catReport);

        // Get the catReport
        restCatReportMockMvc.perform(get("/api/cat-reports/{id}", catReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catReport.getId().intValue()))
            .andExpect(jsonPath("$.codeCategReport").value(DEFAULT_CODE_CATEG_REPORT))
            .andExpect(jsonPath("$.libelleCategReport").value(DEFAULT_LIBELLE_CATEG_REPORT))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCatReport() throws Exception {
        // Get the catReport
        restCatReportMockMvc.perform(get("/api/cat-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatReport() throws Exception {
        // Initialize the database
        catReportService.save(catReport);

        int databaseSizeBeforeUpdate = catReportRepository.findAll().size();

        // Update the catReport
        CatReport updatedCatReport = catReportRepository.findById(catReport.getId()).get();
        // Disconnect from session so that the updates on updatedCatReport are not directly saved in db
        em.detach(updatedCatReport);
        updatedCatReport
            .codeCategReport(UPDATED_CODE_CATEG_REPORT)
            .libelleCategReport(UPDATED_LIBELLE_CATEG_REPORT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restCatReportMockMvc.perform(put("/api/cat-reports")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatReport)))
            .andExpect(status().isOk());

        // Validate the CatReport in the database
        List<CatReport> catReportList = catReportRepository.findAll();
        assertThat(catReportList).hasSize(databaseSizeBeforeUpdate);
        CatReport testCatReport = catReportList.get(catReportList.size() - 1);
        assertThat(testCatReport.getCodeCategReport()).isEqualTo(UPDATED_CODE_CATEG_REPORT);
        assertThat(testCatReport.getLibelleCategReport()).isEqualTo(UPDATED_LIBELLE_CATEG_REPORT);
        assertThat(testCatReport.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCatReport.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testCatReport.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testCatReport.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testCatReport.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingCatReport() throws Exception {
        int databaseSizeBeforeUpdate = catReportRepository.findAll().size();

        // Create the CatReport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatReportMockMvc.perform(put("/api/cat-reports")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catReport)))
            .andExpect(status().isBadRequest());

        // Validate the CatReport in the database
        List<CatReport> catReportList = catReportRepository.findAll();
        assertThat(catReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatReport() throws Exception {
        // Initialize the database
        catReportService.save(catReport);

        int databaseSizeBeforeDelete = catReportRepository.findAll().size();

        // Delete the catReport
        restCatReportMockMvc.perform(delete("/api/cat-reports/{id}", catReport.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatReport> catReportList = catReportRepository.findAll();
        assertThat(catReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
