package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Cible;
import com.projet.hpd.repository.CibleRepository;
import com.projet.hpd.service.CibleService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.projet.hpd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CibleResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class CibleResourceIT {

    private static final String DEFAULT_LIBELLE_CIBLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CIBLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_USER = 1;
    private static final Integer UPDATED_ID_USER = 2;

    private static final Instant DEFAULT_DATE_DELETED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DELETED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_CREATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CREATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_UPDATE = 1L;
    private static final Long UPDATED_USER_UPDATE = 2L;

    private static final Long DEFAULT_USER_DELETE = 1L;
    private static final Long UPDATED_USER_DELETE = 2L;

    @Autowired
    private CibleRepository cibleRepository;

    @Autowired
    private CibleService cibleService;

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

    private MockMvc restCibleMockMvc;

    private Cible cible;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CibleResource cibleResource = new CibleResource(cibleService);
        this.restCibleMockMvc = MockMvcBuilders.standaloneSetup(cibleResource)
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
    public static Cible createEntity(EntityManager em) {
        Cible cible = new Cible()
            .libelleCible(DEFAULT_LIBELLE_CIBLE)
            .idUser(DEFAULT_ID_USER)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdate(DEFAULT_USER_UPDATE)
            .userDelete(DEFAULT_USER_DELETE);
        return cible;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cible createUpdatedEntity(EntityManager em) {
        Cible cible = new Cible()
            .libelleCible(UPDATED_LIBELLE_CIBLE)
            .idUser(UPDATED_ID_USER)
            .dateDeleted(UPDATED_DATE_DELETED)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdate(UPDATED_USER_UPDATE)
            .userDelete(UPDATED_USER_DELETE);
        return cible;
    }

    @BeforeEach
    public void initTest() {
        cible = createEntity(em);
    }

    @Test
    @Transactional
    public void createCible() throws Exception {
        int databaseSizeBeforeCreate = cibleRepository.findAll().size();

        // Create the Cible
        restCibleMockMvc.perform(post("/api/cibles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cible)))
            .andExpect(status().isCreated());

        // Validate the Cible in the database
        List<Cible> cibleList = cibleRepository.findAll();
        assertThat(cibleList).hasSize(databaseSizeBeforeCreate + 1);
        Cible testCible = cibleList.get(cibleList.size() - 1);
        assertThat(testCible.getLibelleCible()).isEqualTo(DEFAULT_LIBELLE_CIBLE);
        assertThat(testCible.getIdUser()).isEqualTo(DEFAULT_ID_USER);
        assertThat(testCible.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testCible.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCible.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testCible.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testCible.getUserUpdate()).isEqualTo(DEFAULT_USER_UPDATE);
        assertThat(testCible.getUserDelete()).isEqualTo(DEFAULT_USER_DELETE);
    }

    @Test
    @Transactional
    public void createCibleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cibleRepository.findAll().size();

        // Create the Cible with an existing ID
        cible.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCibleMockMvc.perform(post("/api/cibles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cible)))
            .andExpect(status().isBadRequest());

        // Validate the Cible in the database
        List<Cible> cibleList = cibleRepository.findAll();
        assertThat(cibleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCibles() throws Exception {
        // Initialize the database
        cibleRepository.saveAndFlush(cible);

        // Get all the cibleList
        restCibleMockMvc.perform(get("/api/cibles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cible.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleCible").value(hasItem(DEFAULT_LIBELLE_CIBLE)))
            .andExpect(jsonPath("$.[*].idUser").value(hasItem(DEFAULT_ID_USER)))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdate").value(hasItem(DEFAULT_USER_UPDATE.intValue())))
            .andExpect(jsonPath("$.[*].userDelete").value(hasItem(DEFAULT_USER_DELETE.intValue())));
    }
    
    @Test
    @Transactional
    public void getCible() throws Exception {
        // Initialize the database
        cibleRepository.saveAndFlush(cible);

        // Get the cible
        restCibleMockMvc.perform(get("/api/cibles/{id}", cible.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cible.getId().intValue()))
            .andExpect(jsonPath("$.libelleCible").value(DEFAULT_LIBELLE_CIBLE))
            .andExpect(jsonPath("$.idUser").value(DEFAULT_ID_USER))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdate").value(DEFAULT_USER_UPDATE.intValue()))
            .andExpect(jsonPath("$.userDelete").value(DEFAULT_USER_DELETE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCible() throws Exception {
        // Get the cible
        restCibleMockMvc.perform(get("/api/cibles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCible() throws Exception {
        // Initialize the database
        cibleService.save(cible);

        int databaseSizeBeforeUpdate = cibleRepository.findAll().size();

        // Update the cible
        Cible updatedCible = cibleRepository.findById(cible.getId()).get();
        // Disconnect from session so that the updates on updatedCible are not directly saved in db
        em.detach(updatedCible);
        updatedCible
            .libelleCible(UPDATED_LIBELLE_CIBLE)
            .idUser(UPDATED_ID_USER)
            .dateDeleted(UPDATED_DATE_DELETED)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdate(UPDATED_USER_UPDATE)
            .userDelete(UPDATED_USER_DELETE);

        restCibleMockMvc.perform(put("/api/cibles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCible)))
            .andExpect(status().isOk());

        // Validate the Cible in the database
        List<Cible> cibleList = cibleRepository.findAll();
        assertThat(cibleList).hasSize(databaseSizeBeforeUpdate);
        Cible testCible = cibleList.get(cibleList.size() - 1);
        assertThat(testCible.getLibelleCible()).isEqualTo(UPDATED_LIBELLE_CIBLE);
        assertThat(testCible.getIdUser()).isEqualTo(UPDATED_ID_USER);
        assertThat(testCible.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testCible.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCible.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testCible.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testCible.getUserUpdate()).isEqualTo(UPDATED_USER_UPDATE);
        assertThat(testCible.getUserDelete()).isEqualTo(UPDATED_USER_DELETE);
    }

    @Test
    @Transactional
    public void updateNonExistingCible() throws Exception {
        int databaseSizeBeforeUpdate = cibleRepository.findAll().size();

        // Create the Cible

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCibleMockMvc.perform(put("/api/cibles")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cible)))
            .andExpect(status().isBadRequest());

        // Validate the Cible in the database
        List<Cible> cibleList = cibleRepository.findAll();
        assertThat(cibleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCible() throws Exception {
        // Initialize the database
        cibleService.save(cible);

        int databaseSizeBeforeDelete = cibleRepository.findAll().size();

        // Delete the cible
        restCibleMockMvc.perform(delete("/api/cibles/{id}", cible.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cible> cibleList = cibleRepository.findAll();
        assertThat(cibleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
