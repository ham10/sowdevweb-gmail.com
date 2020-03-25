package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.CatChambre;
import com.projet.hpd.repository.CatChambreRepository;
import com.projet.hpd.service.CatChambreService;
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
 * Integration tests for the {@link CatChambreResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class CatChambreResourceIT {

    private static final String DEFAULT_LIBELLE_CAT_CHAMBRE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CAT_CHAMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_CAT_CHAMBRE = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_CAT_CHAMBRE = "BBBBBBBBBB";

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
    private CatChambreRepository catChambreRepository;

    @Autowired
    private CatChambreService catChambreService;

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

    private MockMvc restCatChambreMockMvc;

    private CatChambre catChambre;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CatChambreResource catChambreResource = new CatChambreResource(catChambreService);
        this.restCatChambreMockMvc = MockMvcBuilders.standaloneSetup(catChambreResource)
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
    public static CatChambre createEntity(EntityManager em) {
        CatChambre catChambre = new CatChambre()
            .libelleCatChambre(DEFAULT_LIBELLE_CAT_CHAMBRE)
            .descriptionCatChambre(DEFAULT_DESCRIPTION_CAT_CHAMBRE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return catChambre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatChambre createUpdatedEntity(EntityManager em) {
        CatChambre catChambre = new CatChambre()
            .libelleCatChambre(UPDATED_LIBELLE_CAT_CHAMBRE)
            .descriptionCatChambre(UPDATED_DESCRIPTION_CAT_CHAMBRE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return catChambre;
    }

    @BeforeEach
    public void initTest() {
        catChambre = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatChambre() throws Exception {
        int databaseSizeBeforeCreate = catChambreRepository.findAll().size();

        // Create the CatChambre
        restCatChambreMockMvc.perform(post("/api/cat-chambres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catChambre)))
            .andExpect(status().isCreated());

        // Validate the CatChambre in the database
        List<CatChambre> catChambreList = catChambreRepository.findAll();
        assertThat(catChambreList).hasSize(databaseSizeBeforeCreate + 1);
        CatChambre testCatChambre = catChambreList.get(catChambreList.size() - 1);
        assertThat(testCatChambre.getLibelleCatChambre()).isEqualTo(DEFAULT_LIBELLE_CAT_CHAMBRE);
        assertThat(testCatChambre.getDescriptionCatChambre()).isEqualTo(DEFAULT_DESCRIPTION_CAT_CHAMBRE);
        assertThat(testCatChambre.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testCatChambre.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testCatChambre.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testCatChambre.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testCatChambre.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createCatChambreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catChambreRepository.findAll().size();

        // Create the CatChambre with an existing ID
        catChambre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatChambreMockMvc.perform(post("/api/cat-chambres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catChambre)))
            .andExpect(status().isBadRequest());

        // Validate the CatChambre in the database
        List<CatChambre> catChambreList = catChambreRepository.findAll();
        assertThat(catChambreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCatChambres() throws Exception {
        // Initialize the database
        catChambreRepository.saveAndFlush(catChambre);

        // Get all the catChambreList
        restCatChambreMockMvc.perform(get("/api/cat-chambres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catChambre.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleCatChambre").value(hasItem(DEFAULT_LIBELLE_CAT_CHAMBRE)))
            .andExpect(jsonPath("$.[*].descriptionCatChambre").value(hasItem(DEFAULT_DESCRIPTION_CAT_CHAMBRE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getCatChambre() throws Exception {
        // Initialize the database
        catChambreRepository.saveAndFlush(catChambre);

        // Get the catChambre
        restCatChambreMockMvc.perform(get("/api/cat-chambres/{id}", catChambre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catChambre.getId().intValue()))
            .andExpect(jsonPath("$.libelleCatChambre").value(DEFAULT_LIBELLE_CAT_CHAMBRE))
            .andExpect(jsonPath("$.descriptionCatChambre").value(DEFAULT_DESCRIPTION_CAT_CHAMBRE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCatChambre() throws Exception {
        // Get the catChambre
        restCatChambreMockMvc.perform(get("/api/cat-chambres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatChambre() throws Exception {
        // Initialize the database
        catChambreService.save(catChambre);

        int databaseSizeBeforeUpdate = catChambreRepository.findAll().size();

        // Update the catChambre
        CatChambre updatedCatChambre = catChambreRepository.findById(catChambre.getId()).get();
        // Disconnect from session so that the updates on updatedCatChambre are not directly saved in db
        em.detach(updatedCatChambre);
        updatedCatChambre
            .libelleCatChambre(UPDATED_LIBELLE_CAT_CHAMBRE)
            .descriptionCatChambre(UPDATED_DESCRIPTION_CAT_CHAMBRE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restCatChambreMockMvc.perform(put("/api/cat-chambres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatChambre)))
            .andExpect(status().isOk());

        // Validate the CatChambre in the database
        List<CatChambre> catChambreList = catChambreRepository.findAll();
        assertThat(catChambreList).hasSize(databaseSizeBeforeUpdate);
        CatChambre testCatChambre = catChambreList.get(catChambreList.size() - 1);
        assertThat(testCatChambre.getLibelleCatChambre()).isEqualTo(UPDATED_LIBELLE_CAT_CHAMBRE);
        assertThat(testCatChambre.getDescriptionCatChambre()).isEqualTo(UPDATED_DESCRIPTION_CAT_CHAMBRE);
        assertThat(testCatChambre.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testCatChambre.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testCatChambre.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testCatChambre.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testCatChambre.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingCatChambre() throws Exception {
        int databaseSizeBeforeUpdate = catChambreRepository.findAll().size();

        // Create the CatChambre

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatChambreMockMvc.perform(put("/api/cat-chambres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catChambre)))
            .andExpect(status().isBadRequest());

        // Validate the CatChambre in the database
        List<CatChambre> catChambreList = catChambreRepository.findAll();
        assertThat(catChambreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatChambre() throws Exception {
        // Initialize the database
        catChambreService.save(catChambre);

        int databaseSizeBeforeDelete = catChambreRepository.findAll().size();

        // Delete the catChambre
        restCatChambreMockMvc.perform(delete("/api/cat-chambres/{id}", catChambre.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatChambre> catChambreList = catChambreRepository.findAll();
        assertThat(catChambreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
