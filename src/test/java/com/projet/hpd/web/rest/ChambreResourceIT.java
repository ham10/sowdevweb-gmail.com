package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Chambre;
import com.projet.hpd.repository.ChambreRepository;
import com.projet.hpd.service.ChambreService;
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
 * Integration tests for the {@link ChambreResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class ChambreResourceIT {

    private static final Integer DEFAULT_NUMERO_CHAMBRE = 1;
    private static final Integer UPDATED_NUMERO_CHAMBRE = 2;

    private static final Integer DEFAULT_POST_TEL_CHAMBRE = 1;
    private static final Integer UPDATED_POST_TEL_CHAMBRE = 2;

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
    private ChambreRepository chambreRepository;

    @Autowired
    private ChambreService chambreService;

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

    private MockMvc restChambreMockMvc;

    private Chambre chambre;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChambreResource chambreResource = new ChambreResource(chambreService);
        this.restChambreMockMvc = MockMvcBuilders.standaloneSetup(chambreResource)
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
    public static Chambre createEntity(EntityManager em) {
        Chambre chambre = new Chambre()
            .numeroChambre(DEFAULT_NUMERO_CHAMBRE)
            .postTelChambre(DEFAULT_POST_TEL_CHAMBRE)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return chambre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chambre createUpdatedEntity(EntityManager em) {
        Chambre chambre = new Chambre()
            .numeroChambre(UPDATED_NUMERO_CHAMBRE)
            .postTelChambre(UPDATED_POST_TEL_CHAMBRE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return chambre;
    }

    @BeforeEach
    public void initTest() {
        chambre = createEntity(em);
    }

    @Test
    @Transactional
    public void createChambre() throws Exception {
        int databaseSizeBeforeCreate = chambreRepository.findAll().size();

        // Create the Chambre
        restChambreMockMvc.perform(post("/api/chambres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chambre)))
            .andExpect(status().isCreated());

        // Validate the Chambre in the database
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeCreate + 1);
        Chambre testChambre = chambreList.get(chambreList.size() - 1);
        assertThat(testChambre.getNumeroChambre()).isEqualTo(DEFAULT_NUMERO_CHAMBRE);
        assertThat(testChambre.getPostTelChambre()).isEqualTo(DEFAULT_POST_TEL_CHAMBRE);
        assertThat(testChambre.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testChambre.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testChambre.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testChambre.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testChambre.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createChambreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chambreRepository.findAll().size();

        // Create the Chambre with an existing ID
        chambre.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChambreMockMvc.perform(post("/api/chambres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chambre)))
            .andExpect(status().isBadRequest());

        // Validate the Chambre in the database
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllChambres() throws Exception {
        // Initialize the database
        chambreRepository.saveAndFlush(chambre);

        // Get all the chambreList
        restChambreMockMvc.perform(get("/api/chambres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chambre.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroChambre").value(hasItem(DEFAULT_NUMERO_CHAMBRE)))
            .andExpect(jsonPath("$.[*].postTelChambre").value(hasItem(DEFAULT_POST_TEL_CHAMBRE)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getChambre() throws Exception {
        // Initialize the database
        chambreRepository.saveAndFlush(chambre);

        // Get the chambre
        restChambreMockMvc.perform(get("/api/chambres/{id}", chambre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(chambre.getId().intValue()))
            .andExpect(jsonPath("$.numeroChambre").value(DEFAULT_NUMERO_CHAMBRE))
            .andExpect(jsonPath("$.postTelChambre").value(DEFAULT_POST_TEL_CHAMBRE))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingChambre() throws Exception {
        // Get the chambre
        restChambreMockMvc.perform(get("/api/chambres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChambre() throws Exception {
        // Initialize the database
        chambreService.save(chambre);

        int databaseSizeBeforeUpdate = chambreRepository.findAll().size();

        // Update the chambre
        Chambre updatedChambre = chambreRepository.findById(chambre.getId()).get();
        // Disconnect from session so that the updates on updatedChambre are not directly saved in db
        em.detach(updatedChambre);
        updatedChambre
            .numeroChambre(UPDATED_NUMERO_CHAMBRE)
            .postTelChambre(UPDATED_POST_TEL_CHAMBRE)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restChambreMockMvc.perform(put("/api/chambres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedChambre)))
            .andExpect(status().isOk());

        // Validate the Chambre in the database
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeUpdate);
        Chambre testChambre = chambreList.get(chambreList.size() - 1);
        assertThat(testChambre.getNumeroChambre()).isEqualTo(UPDATED_NUMERO_CHAMBRE);
        assertThat(testChambre.getPostTelChambre()).isEqualTo(UPDATED_POST_TEL_CHAMBRE);
        assertThat(testChambre.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testChambre.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testChambre.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testChambre.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testChambre.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingChambre() throws Exception {
        int databaseSizeBeforeUpdate = chambreRepository.findAll().size();

        // Create the Chambre

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChambreMockMvc.perform(put("/api/chambres")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(chambre)))
            .andExpect(status().isBadRequest());

        // Validate the Chambre in the database
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChambre() throws Exception {
        // Initialize the database
        chambreService.save(chambre);

        int databaseSizeBeforeDelete = chambreRepository.findAll().size();

        // Delete the chambre
        restChambreMockMvc.perform(delete("/api/chambres/{id}", chambre.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chambre> chambreList = chambreRepository.findAll();
        assertThat(chambreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
