package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.TypeNotif;
import com.projet.hpd.repository.TypeNotifRepository;
import com.projet.hpd.service.TypeNotifService;
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
 * Integration tests for the {@link TypeNotifResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class TypeNotifResourceIT {

    private static final String DEFAULT_CODE_TYPE_NOTIF = "AAAAAAAAAA";
    private static final String UPDATED_CODE_TYPE_NOTIF = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_TYPE_NOTIF = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_TYPE_NOTIF = "BBBBBBBBBB";

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
    private TypeNotifRepository typeNotifRepository;

    @Autowired
    private TypeNotifService typeNotifService;

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

    private MockMvc restTypeNotifMockMvc;

    private TypeNotif typeNotif;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeNotifResource typeNotifResource = new TypeNotifResource(typeNotifService);
        this.restTypeNotifMockMvc = MockMvcBuilders.standaloneSetup(typeNotifResource)
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
    public static TypeNotif createEntity(EntityManager em) {
        TypeNotif typeNotif = new TypeNotif()
            .codeTypeNotif(DEFAULT_CODE_TYPE_NOTIF)
            .libelleTypeNotif(DEFAULT_LIBELLE_TYPE_NOTIF)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return typeNotif;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeNotif createUpdatedEntity(EntityManager em) {
        TypeNotif typeNotif = new TypeNotif()
            .codeTypeNotif(UPDATED_CODE_TYPE_NOTIF)
            .libelleTypeNotif(UPDATED_LIBELLE_TYPE_NOTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return typeNotif;
    }

    @BeforeEach
    public void initTest() {
        typeNotif = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeNotif() throws Exception {
        int databaseSizeBeforeCreate = typeNotifRepository.findAll().size();

        // Create the TypeNotif
        restTypeNotifMockMvc.perform(post("/api/type-notifs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeNotif)))
            .andExpect(status().isCreated());

        // Validate the TypeNotif in the database
        List<TypeNotif> typeNotifList = typeNotifRepository.findAll();
        assertThat(typeNotifList).hasSize(databaseSizeBeforeCreate + 1);
        TypeNotif testTypeNotif = typeNotifList.get(typeNotifList.size() - 1);
        assertThat(testTypeNotif.getCodeTypeNotif()).isEqualTo(DEFAULT_CODE_TYPE_NOTIF);
        assertThat(testTypeNotif.getLibelleTypeNotif()).isEqualTo(DEFAULT_LIBELLE_TYPE_NOTIF);
        assertThat(testTypeNotif.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testTypeNotif.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testTypeNotif.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testTypeNotif.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testTypeNotif.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createTypeNotifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeNotifRepository.findAll().size();

        // Create the TypeNotif with an existing ID
        typeNotif.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeNotifMockMvc.perform(post("/api/type-notifs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeNotif)))
            .andExpect(status().isBadRequest());

        // Validate the TypeNotif in the database
        List<TypeNotif> typeNotifList = typeNotifRepository.findAll();
        assertThat(typeNotifList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeNotifs() throws Exception {
        // Initialize the database
        typeNotifRepository.saveAndFlush(typeNotif);

        // Get all the typeNotifList
        restTypeNotifMockMvc.perform(get("/api/type-notifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeNotif.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeTypeNotif").value(hasItem(DEFAULT_CODE_TYPE_NOTIF)))
            .andExpect(jsonPath("$.[*].libelleTypeNotif").value(hasItem(DEFAULT_LIBELLE_TYPE_NOTIF)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getTypeNotif() throws Exception {
        // Initialize the database
        typeNotifRepository.saveAndFlush(typeNotif);

        // Get the typeNotif
        restTypeNotifMockMvc.perform(get("/api/type-notifs/{id}", typeNotif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeNotif.getId().intValue()))
            .andExpect(jsonPath("$.codeTypeNotif").value(DEFAULT_CODE_TYPE_NOTIF))
            .andExpect(jsonPath("$.libelleTypeNotif").value(DEFAULT_LIBELLE_TYPE_NOTIF))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeNotif() throws Exception {
        // Get the typeNotif
        restTypeNotifMockMvc.perform(get("/api/type-notifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeNotif() throws Exception {
        // Initialize the database
        typeNotifService.save(typeNotif);

        int databaseSizeBeforeUpdate = typeNotifRepository.findAll().size();

        // Update the typeNotif
        TypeNotif updatedTypeNotif = typeNotifRepository.findById(typeNotif.getId()).get();
        // Disconnect from session so that the updates on updatedTypeNotif are not directly saved in db
        em.detach(updatedTypeNotif);
        updatedTypeNotif
            .codeTypeNotif(UPDATED_CODE_TYPE_NOTIF)
            .libelleTypeNotif(UPDATED_LIBELLE_TYPE_NOTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restTypeNotifMockMvc.perform(put("/api/type-notifs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeNotif)))
            .andExpect(status().isOk());

        // Validate the TypeNotif in the database
        List<TypeNotif> typeNotifList = typeNotifRepository.findAll();
        assertThat(typeNotifList).hasSize(databaseSizeBeforeUpdate);
        TypeNotif testTypeNotif = typeNotifList.get(typeNotifList.size() - 1);
        assertThat(testTypeNotif.getCodeTypeNotif()).isEqualTo(UPDATED_CODE_TYPE_NOTIF);
        assertThat(testTypeNotif.getLibelleTypeNotif()).isEqualTo(UPDATED_LIBELLE_TYPE_NOTIF);
        assertThat(testTypeNotif.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testTypeNotif.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testTypeNotif.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testTypeNotif.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testTypeNotif.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeNotif() throws Exception {
        int databaseSizeBeforeUpdate = typeNotifRepository.findAll().size();

        // Create the TypeNotif

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeNotifMockMvc.perform(put("/api/type-notifs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeNotif)))
            .andExpect(status().isBadRequest());

        // Validate the TypeNotif in the database
        List<TypeNotif> typeNotifList = typeNotifRepository.findAll();
        assertThat(typeNotifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeNotif() throws Exception {
        // Initialize the database
        typeNotifService.save(typeNotif);

        int databaseSizeBeforeDelete = typeNotifRepository.findAll().size();

        // Delete the typeNotif
        restTypeNotifMockMvc.perform(delete("/api/type-notifs/{id}", typeNotif.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeNotif> typeNotifList = typeNotifRepository.findAll();
        assertThat(typeNotifList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
