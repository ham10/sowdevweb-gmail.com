package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Module;
import com.projet.hpd.repository.ModuleRepository;
import com.projet.hpd.service.ModuleService;
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
 * Integration tests for the {@link ModuleResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class ModuleResourceIT {

    private static final String DEFAULT_LIBELLE_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_MODULE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_MODULE = "BBBBBBBBBB";

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
    private ModuleRepository moduleRepository;

    @Autowired
    private ModuleService moduleService;

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

    private MockMvc restModuleMockMvc;

    private Module module;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModuleResource moduleResource = new ModuleResource(moduleService);
        this.restModuleMockMvc = MockMvcBuilders.standaloneSetup(moduleResource)
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
    public static Module createEntity(EntityManager em) {
        Module module = new Module()
            .libelleModule(DEFAULT_LIBELLE_MODULE)
            .descriptionModule(DEFAULT_DESCRIPTION_MODULE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return module;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Module createUpdatedEntity(EntityManager em) {
        Module module = new Module()
            .libelleModule(UPDATED_LIBELLE_MODULE)
            .descriptionModule(UPDATED_DESCRIPTION_MODULE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return module;
    }

    @BeforeEach
    public void initTest() {
        module = createEntity(em);
    }

    @Test
    @Transactional
    public void createModule() throws Exception {
        int databaseSizeBeforeCreate = moduleRepository.findAll().size();

        // Create the Module
        restModuleMockMvc.perform(post("/api/modules")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(module)))
            .andExpect(status().isCreated());

        // Validate the Module in the database
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeCreate + 1);
        Module testModule = moduleList.get(moduleList.size() - 1);
        assertThat(testModule.getLibelleModule()).isEqualTo(DEFAULT_LIBELLE_MODULE);
        assertThat(testModule.getDescriptionModule()).isEqualTo(DEFAULT_DESCRIPTION_MODULE);
        assertThat(testModule.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testModule.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testModule.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testModule.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testModule.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = moduleRepository.findAll().size();

        // Create the Module with an existing ID
        module.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModuleMockMvc.perform(post("/api/modules")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(module)))
            .andExpect(status().isBadRequest());

        // Validate the Module in the database
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllModules() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get all the moduleList
        restModuleMockMvc.perform(get("/api/modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(module.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleModule").value(hasItem(DEFAULT_LIBELLE_MODULE)))
            .andExpect(jsonPath("$.[*].descriptionModule").value(hasItem(DEFAULT_DESCRIPTION_MODULE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getModule() throws Exception {
        // Initialize the database
        moduleRepository.saveAndFlush(module);

        // Get the module
        restModuleMockMvc.perform(get("/api/modules/{id}", module.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(module.getId().intValue()))
            .andExpect(jsonPath("$.libelleModule").value(DEFAULT_LIBELLE_MODULE))
            .andExpect(jsonPath("$.descriptionModule").value(DEFAULT_DESCRIPTION_MODULE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingModule() throws Exception {
        // Get the module
        restModuleMockMvc.perform(get("/api/modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModule() throws Exception {
        // Initialize the database
        moduleService.save(module);

        int databaseSizeBeforeUpdate = moduleRepository.findAll().size();

        // Update the module
        Module updatedModule = moduleRepository.findById(module.getId()).get();
        // Disconnect from session so that the updates on updatedModule are not directly saved in db
        em.detach(updatedModule);
        updatedModule
            .libelleModule(UPDATED_LIBELLE_MODULE)
            .descriptionModule(UPDATED_DESCRIPTION_MODULE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restModuleMockMvc.perform(put("/api/modules")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedModule)))
            .andExpect(status().isOk());

        // Validate the Module in the database
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeUpdate);
        Module testModule = moduleList.get(moduleList.size() - 1);
        assertThat(testModule.getLibelleModule()).isEqualTo(UPDATED_LIBELLE_MODULE);
        assertThat(testModule.getDescriptionModule()).isEqualTo(UPDATED_DESCRIPTION_MODULE);
        assertThat(testModule.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testModule.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testModule.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testModule.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testModule.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingModule() throws Exception {
        int databaseSizeBeforeUpdate = moduleRepository.findAll().size();

        // Create the Module

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModuleMockMvc.perform(put("/api/modules")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(module)))
            .andExpect(status().isBadRequest());

        // Validate the Module in the database
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModule() throws Exception {
        // Initialize the database
        moduleService.save(module);

        int databaseSizeBeforeDelete = moduleRepository.findAll().size();

        // Delete the module
        restModuleMockMvc.perform(delete("/api/modules/{id}", module.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Module> moduleList = moduleRepository.findAll();
        assertThat(moduleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
