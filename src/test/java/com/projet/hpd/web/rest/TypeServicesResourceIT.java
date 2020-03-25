package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeServices;
import com.projet.hpd.repository.TypeServicesRepository;
import com.projet.hpd.service.TypeServicesService;
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
 * Integration tests for the {@link TypeServicesResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeServicesResourceIT {

    private static final String DEFAULT_CODE_TYPE_SRV = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_SRV = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_SRV = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_SRV = "BBBBBBBBBB";

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
    private TypeServicesRepository typeServicesRepository;

    @Autowired
    private TypeServicesService typeServicesService;

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

    private MockMvc restTypeServicesMockMvc;

    private TypeServices typeServices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeServicesResource typeServicesResource = new TypeServicesResource(typeServicesService);
        this.restTypeServicesMockMvc = MockMvcBuilders.standaloneSetup(typeServicesResource)
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
    public static TypeServices createEntity(EntityManager em) {
        TypeServices typeServices = new TypeServices()
            .codeTypeSrv(DEFAULT_CODE_TYPE_SRV)
            .libelleTypeSrv(DEFAULT_LIBELLE_TYPE_SRV)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeServices;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeServices createUpdatedEntity(EntityManager em) {
        TypeServices typeServices = new TypeServices()
            .codeTypeSrv(UPDATED_CODE_TYPE_SRV)
            .libelleTypeSrv(UPDATED_LIBELLE_TYPE_SRV)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeServices;
    }

    @BeforeEach
    public void initTest() {
        typeServices = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeServices() throws Exception {
        int databaseSizeBeforeCreate = typeServicesRepository.findAll().size();

        // Create the TypeServices
        restTypeServicesMockMvc.perform(post("/api/type-services")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeServices)))
            .andExpect(status().isCreated());

        // Validate the TypeServices in the database
        List<TypeServices> typeServicesList = typeServicesRepository.findAll();
        assertThat(typeServicesList).hasSize(databaseSizeBeforeCreate + 1);
        TypeServices testTypeServices = typeServicesList.get(typeServicesList.size() - 1);
        assertThat(testTypeServices.getCodeTypeSrv()).isEqualTo(DEFAULT_CODE_TYPE_SRV);
        assertThat(testTypeServices.getLibelleTypeSrv()).isEqualTo(DEFAULT_LIBELLE_TYPE_SRV);
        assertThat(testTypeServices.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeServices.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeServices.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeServices.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeServices.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeServicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeServicesRepository.findAll().size();

        // Create the TypeServices with an existing ID
        typeServices.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeServicesMockMvc.perform(post("/api/type-services")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeServices)))
            .andExpect(status().isBadRequest());

        // Validate the TypeServices in the database
        List<TypeServices> typeServicesList = typeServicesRepository.findAll();
        assertThat(typeServicesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeServices() throws Exception {
        // Initialize the database
        typeServicesRepository.saveAndFlush(typeServices);

        // Get all the typeServicesList
        restTypeServicesMockMvc.perform(get("/api/type-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeServices.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeSrv").value(hasItem(DEFAULT_CODE_TYPE_SRV)))
            .andExpect(jsonPath("$.[*].libelleTypeSrv").value(hasItem(DEFAULT_LIBELLE_TYPE_SRV)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeServices() throws Exception {
        // Initialize the database
        typeServicesRepository.saveAndFlush(typeServices);

        // Get the typeServices
        restTypeServicesMockMvc.perform(get("/api/type-services/{id}", typeServices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeServices.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeSrv").value(DEFAULT_CODE_TYPE_SRV))
            .andExpect(jsonPath("$.libelleTypeSrv").value(DEFAULT_LIBELLE_TYPE_SRV))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeServices() throws Exception {
        // Get the typeServices
        restTypeServicesMockMvc.perform(get("/api/type-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeServices() throws Exception {
        // Initialize the database
        typeServicesService.save(typeServices);

        int databaseSizeBeforeUpdate = typeServicesRepository.findAll().size();

        // Update the typeServices
        TypeServices updatedTypeServices = typeServicesRepository.findById(typeServices.getId()).get();
        // Disconnect from session so that the updates on updatedTypeServices are not directly saved in db
        em.detach(updatedTypeServices);
        updatedTypeServices
            .codeTypeSrv(UPDATED_CODE_TYPE_SRV)
            .libelleTypeSrv(UPDATED_LIBELLE_TYPE_SRV)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeServicesMockMvc.perform(put("/api/type-services")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeServices)))
            .andExpect(status().isOk());

        // Validate the TypeServices in the database
        List<TypeServices> typeServicesList = typeServicesRepository.findAll();
        assertThat(typeServicesList).hasSize(databaseSizeBeforeUpdate);
        TypeServices testTypeServices = typeServicesList.get(typeServicesList.size() - 1);
        assertThat(testTypeServices.getCodeTypeSrv()).isEqualTo(UPDATED_CODE_TYPE_SRV);
        assertThat(testTypeServices.getLibelleTypeSrv()).isEqualTo(UPDATED_LIBELLE_TYPE_SRV);
        assertThat(testTypeServices.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeServices.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeServices.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeServices.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeServices.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeServices() throws Exception {
        int databaseSizeBeforeUpdate = typeServicesRepository.findAll().size();

        // Create the TypeServices

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeServicesMockMvc.perform(put("/api/type-services")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeServices)))
            .andExpect(status().isBadRequest());

        // Validate the TypeServices in the database
        List<TypeServices> typeServicesList = typeServicesRepository.findAll();
        assertThat(typeServicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeServices() throws Exception {
        // Initialize the database
        typeServicesService.save(typeServices);

        int databaseSizeBeforeDelete = typeServicesRepository.findAll().size();

        // Delete the typeServices
        restTypeServicesMockMvc.perform(delete("/api/type-services/{id}", typeServices.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeServices> typeServicesList = typeServicesRepository.findAll();
        assertThat(typeServicesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
