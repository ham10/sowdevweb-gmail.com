package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.DeptServices;
import com.projet.hpd.repository.DeptServicesRepository;
import com.projet.hpd.service.DeptServicesService;
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
 * Integration tests for the {@link DeptServicesResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class DeptServicesResourceIT {

    private static final String DEFAULT_CODE_DEPT_SRV = "AAAAAAAAAA";
    private static final String UPDATED_CODE_DEPT_SRV = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_DEPT_SRV = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_DEPT_SRV = "BBBBBBBBBB";

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
    private DeptServicesRepository deptServicesRepository;

    @Autowired
    private DeptServicesService deptServicesService;

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

    private MockMvc restDeptServicesMockMvc;

    private DeptServices deptServices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeptServicesResource deptServicesResource = new DeptServicesResource(deptServicesService);
        this.restDeptServicesMockMvc = MockMvcBuilders.standaloneSetup(deptServicesResource)
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
    public static DeptServices createEntity(EntityManager em) {
        DeptServices deptServices = new DeptServices()
            .codeDeptSrv(DEFAULT_CODE_DEPT_SRV)
            .libelleDeptSrv(DEFAULT_LIBELLE_DEPT_SRV)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return deptServices;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeptServices createUpdatedEntity(EntityManager em) {
        DeptServices deptServices = new DeptServices()
            .codeDeptSrv(UPDATED_CODE_DEPT_SRV)
            .libelleDeptSrv(UPDATED_LIBELLE_DEPT_SRV)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return deptServices;
    }

    @BeforeEach
    public void initTest() {
        deptServices = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeptServices() throws Exception {
        int databaseSizeBeforeCreate = deptServicesRepository.findAll().size();

        // Create the DeptServices
        restDeptServicesMockMvc.perform(post("/api/dept-services")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deptServices)))
            .andExpect(status().isCreated());

        // Validate the DeptServices in the database
        List<DeptServices> deptServicesList = deptServicesRepository.findAll();
        assertThat(deptServicesList).hasSize(databaseSizeBeforeCreate + 1);
        DeptServices testDeptServices = deptServicesList.get(deptServicesList.size() - 1);
        assertThat(testDeptServices.getCodeDeptSrv()).isEqualTo(DEFAULT_CODE_DEPT_SRV);
        assertThat(testDeptServices.getLibelleDeptSrv()).isEqualTo(DEFAULT_LIBELLE_DEPT_SRV);
        assertThat(testDeptServices.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testDeptServices.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testDeptServices.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testDeptServices.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testDeptServices.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createDeptServicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deptServicesRepository.findAll().size();

        // Create the DeptServices with an existing ID
        deptServices.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeptServicesMockMvc.perform(post("/api/dept-services")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deptServices)))
            .andExpect(status().isBadRequest());

        // Validate the DeptServices in the database
        List<DeptServices> deptServicesList = deptServicesRepository.findAll();
        assertThat(deptServicesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDeptServices() throws Exception {
        // Initialize the database
        deptServicesRepository.saveAndFlush(deptServices);

        // Get all the deptServicesList
        restDeptServicesMockMvc.perform(get("/api/dept-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deptServices.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeDeptSrv").value(hasItem(DEFAULT_CODE_DEPT_SRV)))
            .andExpect(jsonPath("$.[*].libelleDeptSrv").value(hasItem(DEFAULT_LIBELLE_DEPT_SRV)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getDeptServices() throws Exception {
        // Initialize the database
        deptServicesRepository.saveAndFlush(deptServices);

        // Get the deptServices
        restDeptServicesMockMvc.perform(get("/api/dept-services/{id}", deptServices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deptServices.getId().intValue()))
            .andExpect(jsonPath("$.codeDeptSrv").value(DEFAULT_CODE_DEPT_SRV))
            .andExpect(jsonPath("$.libelleDeptSrv").value(DEFAULT_LIBELLE_DEPT_SRV))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDeptServices() throws Exception {
        // Get the deptServices
        restDeptServicesMockMvc.perform(get("/api/dept-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeptServices() throws Exception {
        // Initialize the database
        deptServicesService.save(deptServices);

        int databaseSizeBeforeUpdate = deptServicesRepository.findAll().size();

        // Update the deptServices
        DeptServices updatedDeptServices = deptServicesRepository.findById(deptServices.getId()).get();
        // Disconnect from session so that the updates on updatedDeptServices are not directly saved in db
        em.detach(updatedDeptServices);
        updatedDeptServices
            .codeDeptSrv(UPDATED_CODE_DEPT_SRV)
            .libelleDeptSrv(UPDATED_LIBELLE_DEPT_SRV)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restDeptServicesMockMvc.perform(put("/api/dept-services")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeptServices)))
            .andExpect(status().isOk());

        // Validate the DeptServices in the database
        List<DeptServices> deptServicesList = deptServicesRepository.findAll();
        assertThat(deptServicesList).hasSize(databaseSizeBeforeUpdate);
        DeptServices testDeptServices = deptServicesList.get(deptServicesList.size() - 1);
        assertThat(testDeptServices.getCodeDeptSrv()).isEqualTo(UPDATED_CODE_DEPT_SRV);
        assertThat(testDeptServices.getLibelleDeptSrv()).isEqualTo(UPDATED_LIBELLE_DEPT_SRV);
        assertThat(testDeptServices.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testDeptServices.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testDeptServices.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testDeptServices.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testDeptServices.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingDeptServices() throws Exception {
        int databaseSizeBeforeUpdate = deptServicesRepository.findAll().size();

        // Create the DeptServices

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeptServicesMockMvc.perform(put("/api/dept-services")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deptServices)))
            .andExpect(status().isBadRequest());

        // Validate the DeptServices in the database
        List<DeptServices> deptServicesList = deptServicesRepository.findAll();
        assertThat(deptServicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeptServices() throws Exception {
        // Initialize the database
        deptServicesService.save(deptServices);

        int databaseSizeBeforeDelete = deptServicesRepository.findAll().size();

        // Delete the deptServices
        restDeptServicesMockMvc.perform(delete("/api/dept-services/{id}", deptServices.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeptServices> deptServicesList = deptServicesRepository.findAll();
        assertThat(deptServicesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
