package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.ParamSys;
import com.projet.hpd.repository.ParamSysRepository;
import com.projet.hpd.service.ParamSysService;
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
 * Integration tests for the {@link ParamSysResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class ParamSysResourceIT {

    private static final String DEFAULT_CODE_PARAM_SYS = "AAAAAAAAAA";
    private static final String UPDATED_CODE_PARAM_SYS = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_PARAM_SYS = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_PARAM_SYS = "BBBBBBBBBB";

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
    private ParamSysRepository paramSysRepository;

    @Autowired
    private ParamSysService paramSysService;

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

    private MockMvc restParamSysMockMvc;

    private ParamSys paramSys;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParamSysResource paramSysResource = new ParamSysResource(paramSysService);
        this.restParamSysMockMvc = MockMvcBuilders.standaloneSetup(paramSysResource)
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
    public static ParamSys createEntity(EntityManager em) {
        ParamSys paramSys = new ParamSys()
            .codeParamSys(DEFAULT_CODE_PARAM_SYS)
            .libelleParamSys(DEFAULT_LIBELLE_PARAM_SYS)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return paramSys;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParamSys createUpdatedEntity(EntityManager em) {
        ParamSys paramSys = new ParamSys()
            .codeParamSys(UPDATED_CODE_PARAM_SYS)
            .libelleParamSys(UPDATED_LIBELLE_PARAM_SYS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return paramSys;
    }

    @BeforeEach
    public void initTest() {
        paramSys = createEntity(em);
    }

    @Test
    @Transactional
    public void createParamSys() throws Exception {
        int databaseSizeBeforeCreate = paramSysRepository.findAll().size();

        // Create the ParamSys
        restParamSysMockMvc.perform(post("/api/param-sys")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramSys)))
            .andExpect(status().isCreated());

        // Validate the ParamSys in the database
        List<ParamSys> paramSysList = paramSysRepository.findAll();
        assertThat(paramSysList).hasSize(databaseSizeBeforeCreate + 1);
        ParamSys testParamSys = paramSysList.get(paramSysList.size() - 1);
        assertThat(testParamSys.getCodeParamSys()).isEqualTo(DEFAULT_CODE_PARAM_SYS);
        assertThat(testParamSys.getLibelleParamSys()).isEqualTo(DEFAULT_LIBELLE_PARAM_SYS);
        assertThat(testParamSys.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testParamSys.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testParamSys.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testParamSys.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testParamSys.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createParamSysWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paramSysRepository.findAll().size();

        // Create the ParamSys with an existing ID
        paramSys.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParamSysMockMvc.perform(post("/api/param-sys")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramSys)))
            .andExpect(status().isBadRequest());

        // Validate the ParamSys in the database
        List<ParamSys> paramSysList = paramSysRepository.findAll();
        assertThat(paramSysList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllParamSys() throws Exception {
        // Initialize the database
        paramSysRepository.saveAndFlush(paramSys);

        // Get all the paramSysList
        restParamSysMockMvc.perform(get("/api/param-sys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paramSys.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeParamSys").value(hasItem(DEFAULT_CODE_PARAM_SYS)))
            .andExpect(jsonPath("$.[*].libelleParamSys").value(hasItem(DEFAULT_LIBELLE_PARAM_SYS)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getParamSys() throws Exception {
        // Initialize the database
        paramSysRepository.saveAndFlush(paramSys);

        // Get the paramSys
        restParamSysMockMvc.perform(get("/api/param-sys/{id}", paramSys.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paramSys.getId().intValue()))
            .andExpect(jsonPath("$.codeParamSys").value(DEFAULT_CODE_PARAM_SYS))
            .andExpect(jsonPath("$.libelleParamSys").value(DEFAULT_LIBELLE_PARAM_SYS))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingParamSys() throws Exception {
        // Get the paramSys
        restParamSysMockMvc.perform(get("/api/param-sys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParamSys() throws Exception {
        // Initialize the database
        paramSysService.save(paramSys);

        int databaseSizeBeforeUpdate = paramSysRepository.findAll().size();

        // Update the paramSys
        ParamSys updatedParamSys = paramSysRepository.findById(paramSys.getId()).get();
        // Disconnect from session so that the updates on updatedParamSys are not directly saved in db
        em.detach(updatedParamSys);
        updatedParamSys
            .codeParamSys(UPDATED_CODE_PARAM_SYS)
            .libelleParamSys(UPDATED_LIBELLE_PARAM_SYS)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restParamSysMockMvc.perform(put("/api/param-sys")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedParamSys)))
            .andExpect(status().isOk());

        // Validate the ParamSys in the database
        List<ParamSys> paramSysList = paramSysRepository.findAll();
        assertThat(paramSysList).hasSize(databaseSizeBeforeUpdate);
        ParamSys testParamSys = paramSysList.get(paramSysList.size() - 1);
        assertThat(testParamSys.getCodeParamSys()).isEqualTo(UPDATED_CODE_PARAM_SYS);
        assertThat(testParamSys.getLibelleParamSys()).isEqualTo(UPDATED_LIBELLE_PARAM_SYS);
        assertThat(testParamSys.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testParamSys.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testParamSys.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testParamSys.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testParamSys.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingParamSys() throws Exception {
        int databaseSizeBeforeUpdate = paramSysRepository.findAll().size();

        // Create the ParamSys

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParamSysMockMvc.perform(put("/api/param-sys")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramSys)))
            .andExpect(status().isBadRequest());

        // Validate the ParamSys in the database
        List<ParamSys> paramSysList = paramSysRepository.findAll();
        assertThat(paramSysList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParamSys() throws Exception {
        // Initialize the database
        paramSysService.save(paramSys);

        int databaseSizeBeforeDelete = paramSysRepository.findAll().size();

        // Delete the paramSys
        restParamSysMockMvc.perform(delete("/api/param-sys/{id}", paramSys.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParamSys> paramSysList = paramSysRepository.findAll();
        assertThat(paramSysList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
