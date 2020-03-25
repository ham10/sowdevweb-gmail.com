package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeBonCom;
import com.projet.hpd.repository.TypeBonComRepository;
import com.projet.hpd.service.TypeBonComService;
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
 * Integration tests for the {@link TypeBonComResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeBonComResourceIT {

    private static final String DEFAULT_LIBELLE_TYPE_BON_COM = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_BON_COM = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_TYPE_BON_COM = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_BON_COM = "BBBBBBBBBB";

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
    private TypeBonComRepository typeBonComRepository;

    @Autowired
    private TypeBonComService typeBonComService;

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

    private MockMvc restTypeBonComMockMvc;

    private TypeBonCom typeBonCom;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeBonComResource typeBonComResource = new TypeBonComResource(typeBonComService);
        this.restTypeBonComMockMvc = MockMvcBuilders.standaloneSetup(typeBonComResource)
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
    public static TypeBonCom createEntity(EntityManager em) {
        TypeBonCom typeBonCom = new TypeBonCom()
            .libelleTypeBonCom(DEFAULT_LIBELLE_TYPE_BON_COM)
            .codeTypeBonCom(DEFAULT_CODE_TYPE_BON_COM)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeBonCom;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeBonCom createUpdatedEntity(EntityManager em) {
        TypeBonCom typeBonCom = new TypeBonCom()
            .libelleTypeBonCom(UPDATED_LIBELLE_TYPE_BON_COM)
            .codeTypeBonCom(UPDATED_CODE_TYPE_BON_COM)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeBonCom;
    }

    @BeforeEach
    public void initTest() {
        typeBonCom = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeBonCom() throws Exception {
        int databaseSizeBeforeCreate = typeBonComRepository.findAll().size();

        // Create the TypeBonCom
        restTypeBonComMockMvc.perform(post("/api/type-bon-coms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeBonCom)))
            .andExpect(status().isCreated());

        // Validate the TypeBonCom in the database
        List<TypeBonCom> typeBonComList = typeBonComRepository.findAll();
        assertThat(typeBonComList).hasSize(databaseSizeBeforeCreate + 1);
        TypeBonCom testTypeBonCom = typeBonComList.get(typeBonComList.size() - 1);
        assertThat(testTypeBonCom.getLibelleTypeBonCom()).isEqualTo(DEFAULT_LIBELLE_TYPE_BON_COM);
        assertThat(testTypeBonCom.getCodeTypeBonCom()).isEqualTo(DEFAULT_CODE_TYPE_BON_COM);
        assertThat(testTypeBonCom.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeBonCom.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeBonCom.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeBonCom.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeBonCom.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeBonComWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeBonComRepository.findAll().size();

        // Create the TypeBonCom with an existing ID
        typeBonCom.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeBonComMockMvc.perform(post("/api/type-bon-coms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeBonCom)))
            .andExpect(status().isBadRequest());

        // Validate the TypeBonCom in the database
        List<TypeBonCom> typeBonComList = typeBonComRepository.findAll();
        assertThat(typeBonComList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeBonComs() throws Exception {
        // Initialize the database
        typeBonComRepository.saveAndFlush(typeBonCom);

        // Get all the typeBonComList
        restTypeBonComMockMvc.perform(get("/api/type-bon-coms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeBonCom.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleTypeBonCom").value(hasItem(DEFAULT_LIBELLE_TYPE_BON_COM)))
            .andExpect(jsonPath("$.[*].codeTypeBonCom").value(hasItem(DEFAULT_CODE_TYPE_BON_COM)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeBonCom() throws Exception {
        // Initialize the database
        typeBonComRepository.saveAndFlush(typeBonCom);

        // Get the typeBonCom
        restTypeBonComMockMvc.perform(get("/api/type-bon-coms/{id}", typeBonCom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeBonCom.getId().intValue()))
            .andExpect(jsonPath("$.libelleTypeBonCom").value(DEFAULT_LIBELLE_TYPE_BON_COM))
            .andExpect(jsonPath("$.codeTypeBonCom").value(DEFAULT_CODE_TYPE_BON_COM))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeBonCom() throws Exception {
        // Get the typeBonCom
        restTypeBonComMockMvc.perform(get("/api/type-bon-coms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeBonCom() throws Exception {
        // Initialize the database
        typeBonComService.save(typeBonCom);

        int databaseSizeBeforeUpdate = typeBonComRepository.findAll().size();

        // Update the typeBonCom
        TypeBonCom updatedTypeBonCom = typeBonComRepository.findById(typeBonCom.getId()).get();
        // Disconnect from session so that the updates on updatedTypeBonCom are not directly saved in db
        em.detach(updatedTypeBonCom);
        updatedTypeBonCom
            .libelleTypeBonCom(UPDATED_LIBELLE_TYPE_BON_COM)
            .codeTypeBonCom(UPDATED_CODE_TYPE_BON_COM)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeBonComMockMvc.perform(put("/api/type-bon-coms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeBonCom)))
            .andExpect(status().isOk());

        // Validate the TypeBonCom in the database
        List<TypeBonCom> typeBonComList = typeBonComRepository.findAll();
        assertThat(typeBonComList).hasSize(databaseSizeBeforeUpdate);
        TypeBonCom testTypeBonCom = typeBonComList.get(typeBonComList.size() - 1);
        assertThat(testTypeBonCom.getLibelleTypeBonCom()).isEqualTo(UPDATED_LIBELLE_TYPE_BON_COM);
        assertThat(testTypeBonCom.getCodeTypeBonCom()).isEqualTo(UPDATED_CODE_TYPE_BON_COM);
        assertThat(testTypeBonCom.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeBonCom.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeBonCom.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeBonCom.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeBonCom.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeBonCom() throws Exception {
        int databaseSizeBeforeUpdate = typeBonComRepository.findAll().size();

        // Create the TypeBonCom

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeBonComMockMvc.perform(put("/api/type-bon-coms")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeBonCom)))
            .andExpect(status().isBadRequest());

        // Validate the TypeBonCom in the database
        List<TypeBonCom> typeBonComList = typeBonComRepository.findAll();
        assertThat(typeBonComList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeBonCom() throws Exception {
        // Initialize the database
        typeBonComService.save(typeBonCom);

        int databaseSizeBeforeDelete = typeBonComRepository.findAll().size();

        // Delete the typeBonCom
        restTypeBonComMockMvc.perform(delete("/api/type-bon-coms/{id}", typeBonCom.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeBonCom> typeBonComList = typeBonComRepository.findAll();
        assertThat(typeBonComList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
