package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeReport;
import com.projet.hpd.repository.TypeReportRepository;
import com.projet.hpd.service.TypeReportService;
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
 * Integration tests for the {@link TypeReportResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeReportResourceIT {

    private static final String DEFAULT_CODE_TYPE_REPORT = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_REPORT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_REPORT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_REPORT = "BBBBBBBBBB";

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
    private TypeReportRepository typeReportRepository;

    @Autowired
    private TypeReportService typeReportService;

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

    private MockMvc restTypeReportMockMvc;

    private TypeReport typeReport;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeReportResource typeReportResource = new TypeReportResource(typeReportService);
        this.restTypeReportMockMvc = MockMvcBuilders.standaloneSetup(typeReportResource)
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
    public static TypeReport createEntity(EntityManager em) {
        TypeReport typeReport = new TypeReport()
            .codeTypeReport(DEFAULT_CODE_TYPE_REPORT)
            .libelleTypeReport(DEFAULT_LIBELLE_TYPE_REPORT)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeReport;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeReport createUpdatedEntity(EntityManager em) {
        TypeReport typeReport = new TypeReport()
            .codeTypeReport(UPDATED_CODE_TYPE_REPORT)
            .libelleTypeReport(UPDATED_LIBELLE_TYPE_REPORT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeReport;
    }

    @BeforeEach
    public void initTest() {
        typeReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeReport() throws Exception {
        int databaseSizeBeforeCreate = typeReportRepository.findAll().size();

        // Create the TypeReport
        restTypeReportMockMvc.perform(post("/api/type-reports")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeReport)))
            .andExpect(status().isCreated());

        // Validate the TypeReport in the database
        List<TypeReport> typeReportList = typeReportRepository.findAll();
        assertThat(typeReportList).hasSize(databaseSizeBeforeCreate + 1);
        TypeReport testTypeReport = typeReportList.get(typeReportList.size() - 1);
        assertThat(testTypeReport.getCodeTypeReport()).isEqualTo(DEFAULT_CODE_TYPE_REPORT);
        assertThat(testTypeReport.getLibelleTypeReport()).isEqualTo(DEFAULT_LIBELLE_TYPE_REPORT);
        assertThat(testTypeReport.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeReport.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeReport.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeReport.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeReport.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeReportRepository.findAll().size();

        // Create the TypeReport with an existing ID
        typeReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeReportMockMvc.perform(post("/api/type-reports")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeReport)))
            .andExpect(status().isBadRequest());

        // Validate the TypeReport in the database
        List<TypeReport> typeReportList = typeReportRepository.findAll();
        assertThat(typeReportList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeReports() throws Exception {
        // Initialize the database
        typeReportRepository.saveAndFlush(typeReport);

        // Get all the typeReportList
        restTypeReportMockMvc.perform(get("/api/type-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeReport").value(hasItem(DEFAULT_CODE_TYPE_REPORT)))
            .andExpect(jsonPath("$.[*].libelleTypeReport").value(hasItem(DEFAULT_LIBELLE_TYPE_REPORT)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeReport() throws Exception {
        // Initialize the database
        typeReportRepository.saveAndFlush(typeReport);

        // Get the typeReport
        restTypeReportMockMvc.perform(get("/api/type-reports/{id}", typeReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeReport.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeReport").value(DEFAULT_CODE_TYPE_REPORT))
            .andExpect(jsonPath("$.libelleTypeReport").value(DEFAULT_LIBELLE_TYPE_REPORT))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeReport() throws Exception {
        // Get the typeReport
        restTypeReportMockMvc.perform(get("/api/type-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeReport() throws Exception {
        // Initialize the database
        typeReportService.save(typeReport);

        int databaseSizeBeforeUpdate = typeReportRepository.findAll().size();

        // Update the typeReport
        TypeReport updatedTypeReport = typeReportRepository.findById(typeReport.getId()).get();
        // Disconnect from session so that the updates on updatedTypeReport are not directly saved in db
        em.detach(updatedTypeReport);
        updatedTypeReport
            .codeTypeReport(UPDATED_CODE_TYPE_REPORT)
            .libelleTypeReport(UPDATED_LIBELLE_TYPE_REPORT)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeReportMockMvc.perform(put("/api/type-reports")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeReport)))
            .andExpect(status().isOk());

        // Validate the TypeReport in the database
        List<TypeReport> typeReportList = typeReportRepository.findAll();
        assertThat(typeReportList).hasSize(databaseSizeBeforeUpdate);
        TypeReport testTypeReport = typeReportList.get(typeReportList.size() - 1);
        assertThat(testTypeReport.getCodeTypeReport()).isEqualTo(UPDATED_CODE_TYPE_REPORT);
        assertThat(testTypeReport.getLibelleTypeReport()).isEqualTo(UPDATED_LIBELLE_TYPE_REPORT);
        assertThat(testTypeReport.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeReport.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeReport.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeReport.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeReport.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeReport() throws Exception {
        int databaseSizeBeforeUpdate = typeReportRepository.findAll().size();

        // Create the TypeReport

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeReportMockMvc.perform(put("/api/type-reports")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeReport)))
            .andExpect(status().isBadRequest());

        // Validate the TypeReport in the database
        List<TypeReport> typeReportList = typeReportRepository.findAll();
        assertThat(typeReportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeReport() throws Exception {
        // Initialize the database
        typeReportService.save(typeReport);

        int databaseSizeBeforeDelete = typeReportRepository.findAll().size();

        // Delete the typeReport
        restTypeReportMockMvc.perform(delete("/api/type-reports/{id}", typeReport.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeReport> typeReportList = typeReportRepository.findAll();
        assertThat(typeReportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
