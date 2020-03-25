package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.BonLivraison;
import com.projet.hpd.repository.BonLivraisonRepository;
import com.projet.hpd.service.BonLivraisonService;
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
 * Integration tests for the {@link BonLivraisonResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class BonLivraisonResourceIT {

    private static final String DEFAULT_DESIGNATION_BON_LIVRAISON = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION_BON_LIVRAISON = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX_TOTAL_BON_LIVRAISON = 1D;
    private static final Double UPDATED_PRIX_TOTAL_BON_LIVRAISON = 2D;

    private static final LocalDate DEFAULT_DATE_BON_LIVRAISON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_BON_LIVRAISON = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_USER_CERTIFIED = 1;
    private static final Integer UPDATED_USER_CERTIFIED = 2;

    private static final LocalDate DEFAULT_DATE_CERTIF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CERTIF = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUM_CERTIF = "AAAAAAAAAA";
    private static final String UPDATED_NUM_CERTIF = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_UPDATED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_DELETED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DELETED = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_UPDATED = 1L;
    private static final Long UPDATED_USER_UPDATED = 2L;

    private static final Long DEFAULT_USER_DELETED = 1L;
    private static final Long UPDATED_USER_DELETED = 2L;

    @Autowired
    private BonLivraisonRepository bonLivraisonRepository;

    @Autowired
    private BonLivraisonService bonLivraisonService;

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

    private MockMvc restBonLivraisonMockMvc;

    private BonLivraison bonLivraison;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BonLivraisonResource bonLivraisonResource = new BonLivraisonResource(bonLivraisonService);
        this.restBonLivraisonMockMvc = MockMvcBuilders.standaloneSetup(bonLivraisonResource)
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
    public static BonLivraison createEntity(EntityManager em) {
        BonLivraison bonLivraison = new BonLivraison()
            .designationBonLivraison(DEFAULT_DESIGNATION_BON_LIVRAISON)
            .prixTotalBonLivraison(DEFAULT_PRIX_TOTAL_BON_LIVRAISON)
            .dateBonLivraison(DEFAULT_DATE_BON_LIVRAISON)
            .userCertified(DEFAULT_USER_CERTIFIED)
            .dateCertif(DEFAULT_DATE_CERTIF)
            .numCertif(DEFAULT_NUM_CERTIF)
            .dateCreated(DEFAULT_DATE_CREATED)
            .dateUpdated(DEFAULT_DATE_UPDATED)
            .dateDeleted(DEFAULT_DATE_DELETED)
            .userCreated(DEFAULT_USER_CREATED)
            .userUpdated(DEFAULT_USER_UPDATED)
            .userDeleted(DEFAULT_USER_DELETED);
        return bonLivraison;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BonLivraison createUpdatedEntity(EntityManager em) {
        BonLivraison bonLivraison = new BonLivraison()
            .designationBonLivraison(UPDATED_DESIGNATION_BON_LIVRAISON)
            .prixTotalBonLivraison(UPDATED_PRIX_TOTAL_BON_LIVRAISON)
            .dateBonLivraison(UPDATED_DATE_BON_LIVRAISON)
            .userCertified(UPDATED_USER_CERTIFIED)
            .dateCertif(UPDATED_DATE_CERTIF)
            .numCertif(UPDATED_NUM_CERTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);
        return bonLivraison;
    }

    @BeforeEach
    public void initTest() {
        bonLivraison = createEntity(em);
    }

    @Test
    @Transactional
    public void createBonLivraison() throws Exception {
        int databaseSizeBeforeCreate = bonLivraisonRepository.findAll().size();

        // Create the BonLivraison
        restBonLivraisonMockMvc.perform(post("/api/bon-livraisons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonLivraison)))
            .andExpect(status().isCreated());

        // Validate the BonLivraison in the database
        List<BonLivraison> bonLivraisonList = bonLivraisonRepository.findAll();
        assertThat(bonLivraisonList).hasSize(databaseSizeBeforeCreate + 1);
        BonLivraison testBonLivraison = bonLivraisonList.get(bonLivraisonList.size() - 1);
        assertThat(testBonLivraison.getDesignationBonLivraison()).isEqualTo(DEFAULT_DESIGNATION_BON_LIVRAISON);
        assertThat(testBonLivraison.getPrixTotalBonLivraison()).isEqualTo(DEFAULT_PRIX_TOTAL_BON_LIVRAISON);
        assertThat(testBonLivraison.getDateBonLivraison()).isEqualTo(DEFAULT_DATE_BON_LIVRAISON);
        assertThat(testBonLivraison.getUserCertified()).isEqualTo(DEFAULT_USER_CERTIFIED);
        assertThat(testBonLivraison.getDateCertif()).isEqualTo(DEFAULT_DATE_CERTIF);
        assertThat(testBonLivraison.getNumCertif()).isEqualTo(DEFAULT_NUM_CERTIF);
        assertThat(testBonLivraison.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testBonLivraison.getDateUpdated()).isEqualTo(DEFAULT_DATE_UPDATED);
        assertThat(testBonLivraison.getDateDeleted()).isEqualTo(DEFAULT_DATE_DELETED);
        assertThat(testBonLivraison.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testBonLivraison.getUserUpdated()).isEqualTo(DEFAULT_USER_UPDATED);
        assertThat(testBonLivraison.getUserDeleted()).isEqualTo(DEFAULT_USER_DELETED);
    }

    @Test
    @Transactional
    public void createBonLivraisonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bonLivraisonRepository.findAll().size();

        // Create the BonLivraison with an existing ID
        bonLivraison.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonLivraisonMockMvc.perform(post("/api/bon-livraisons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonLivraison)))
            .andExpect(status().isBadRequest());

        // Validate the BonLivraison in the database
        List<BonLivraison> bonLivraisonList = bonLivraisonRepository.findAll();
        assertThat(bonLivraisonList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBonLivraisons() throws Exception {
        // Initialize the database
        bonLivraisonRepository.saveAndFlush(bonLivraison);

        // Get all the bonLivraisonList
        restBonLivraisonMockMvc.perform(get("/api/bon-livraisons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonLivraison.getId().intValue())))
            .andExpect(jsonPath("$.[*].designationBonLivraison").value(hasItem(DEFAULT_DESIGNATION_BON_LIVRAISON)))
            .andExpect(jsonPath("$.[*].prixTotalBonLivraison").value(hasItem(DEFAULT_PRIX_TOTAL_BON_LIVRAISON.doubleValue())))
            .andExpect(jsonPath("$.[*].dateBonLivraison").value(hasItem(DEFAULT_DATE_BON_LIVRAISON.toString())))
            .andExpect(jsonPath("$.[*].userCertified").value(hasItem(DEFAULT_USER_CERTIFIED)))
            .andExpect(jsonPath("$.[*].dateCertif").value(hasItem(DEFAULT_DATE_CERTIF.toString())))
            .andExpect(jsonPath("$.[*].numCertif").value(hasItem(DEFAULT_NUM_CERTIF)))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED.toString())))
            .andExpect(jsonPath("$.[*].dateUpdated").value(hasItem(DEFAULT_DATE_UPDATED.toString())))
            .andExpect(jsonPath("$.[*].dateDeleted").value(hasItem(DEFAULT_DATE_DELETED.toString())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userUpdated").value(hasItem(DEFAULT_USER_UPDATED.intValue())))
            .andExpect(jsonPath("$.[*].userDeleted").value(hasItem(DEFAULT_USER_DELETED.intValue())));
    }
    
    @Test
    @Transactional
    public void getBonLivraison() throws Exception {
        // Initialize the database
        bonLivraisonRepository.saveAndFlush(bonLivraison);

        // Get the bonLivraison
        restBonLivraisonMockMvc.perform(get("/api/bon-livraisons/{id}", bonLivraison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bonLivraison.getId().intValue()))
            .andExpect(jsonPath("$.designationBonLivraison").value(DEFAULT_DESIGNATION_BON_LIVRAISON))
            .andExpect(jsonPath("$.prixTotalBonLivraison").value(DEFAULT_PRIX_TOTAL_BON_LIVRAISON.doubleValue()))
            .andExpect(jsonPath("$.dateBonLivraison").value(DEFAULT_DATE_BON_LIVRAISON.toString()))
            .andExpect(jsonPath("$.userCertified").value(DEFAULT_USER_CERTIFIED))
            .andExpect(jsonPath("$.dateCertif").value(DEFAULT_DATE_CERTIF.toString()))
            .andExpect(jsonPath("$.numCertif").value(DEFAULT_NUM_CERTIF))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED.toString()))
            .andExpect(jsonPath("$.dateUpdated").value(DEFAULT_DATE_UPDATED.toString()))
            .andExpect(jsonPath("$.dateDeleted").value(DEFAULT_DATE_DELETED.toString()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userUpdated").value(DEFAULT_USER_UPDATED.intValue()))
            .andExpect(jsonPath("$.userDeleted").value(DEFAULT_USER_DELETED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBonLivraison() throws Exception {
        // Get the bonLivraison
        restBonLivraisonMockMvc.perform(get("/api/bon-livraisons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBonLivraison() throws Exception {
        // Initialize the database
        bonLivraisonService.save(bonLivraison);

        int databaseSizeBeforeUpdate = bonLivraisonRepository.findAll().size();

        // Update the bonLivraison
        BonLivraison updatedBonLivraison = bonLivraisonRepository.findById(bonLivraison.getId()).get();
        // Disconnect from session so that the updates on updatedBonLivraison are not directly saved in db
        em.detach(updatedBonLivraison);
        updatedBonLivraison
            .designationBonLivraison(UPDATED_DESIGNATION_BON_LIVRAISON)
            .prixTotalBonLivraison(UPDATED_PRIX_TOTAL_BON_LIVRAISON)
            .dateBonLivraison(UPDATED_DATE_BON_LIVRAISON)
            .userCertified(UPDATED_USER_CERTIFIED)
            .dateCertif(UPDATED_DATE_CERTIF)
            .numCertif(UPDATED_NUM_CERTIF)
            .dateCreated(UPDATED_DATE_CREATED)
            .dateUpdated(UPDATED_DATE_UPDATED)
            .dateDeleted(UPDATED_DATE_DELETED)
            .userCreated(UPDATED_USER_CREATED)
            .userUpdated(UPDATED_USER_UPDATED)
            .userDeleted(UPDATED_USER_DELETED);

        restBonLivraisonMockMvc.perform(put("/api/bon-livraisons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBonLivraison)))
            .andExpect(status().isOk());

        // Validate the BonLivraison in the database
        List<BonLivraison> bonLivraisonList = bonLivraisonRepository.findAll();
        assertThat(bonLivraisonList).hasSize(databaseSizeBeforeUpdate);
        BonLivraison testBonLivraison = bonLivraisonList.get(bonLivraisonList.size() - 1);
        assertThat(testBonLivraison.getDesignationBonLivraison()).isEqualTo(UPDATED_DESIGNATION_BON_LIVRAISON);
        assertThat(testBonLivraison.getPrixTotalBonLivraison()).isEqualTo(UPDATED_PRIX_TOTAL_BON_LIVRAISON);
        assertThat(testBonLivraison.getDateBonLivraison()).isEqualTo(UPDATED_DATE_BON_LIVRAISON);
        assertThat(testBonLivraison.getUserCertified()).isEqualTo(UPDATED_USER_CERTIFIED);
        assertThat(testBonLivraison.getDateCertif()).isEqualTo(UPDATED_DATE_CERTIF);
        assertThat(testBonLivraison.getNumCertif()).isEqualTo(UPDATED_NUM_CERTIF);
        assertThat(testBonLivraison.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testBonLivraison.getDateUpdated()).isEqualTo(UPDATED_DATE_UPDATED);
        assertThat(testBonLivraison.getDateDeleted()).isEqualTo(UPDATED_DATE_DELETED);
        assertThat(testBonLivraison.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testBonLivraison.getUserUpdated()).isEqualTo(UPDATED_USER_UPDATED);
        assertThat(testBonLivraison.getUserDeleted()).isEqualTo(UPDATED_USER_DELETED);
    }

    @Test
    @Transactional
    public void updateNonExistingBonLivraison() throws Exception {
        int databaseSizeBeforeUpdate = bonLivraisonRepository.findAll().size();

        // Create the BonLivraison

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBonLivraisonMockMvc.perform(put("/api/bon-livraisons")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonLivraison)))
            .andExpect(status().isBadRequest());

        // Validate the BonLivraison in the database
        List<BonLivraison> bonLivraisonList = bonLivraisonRepository.findAll();
        assertThat(bonLivraisonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBonLivraison() throws Exception {
        // Initialize the database
        bonLivraisonService.save(bonLivraison);

        int databaseSizeBeforeDelete = bonLivraisonRepository.findAll().size();

        // Delete the bonLivraison
        restBonLivraisonMockMvc.perform(delete("/api/bon-livraisons/{id}", bonLivraison.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BonLivraison> bonLivraisonList = bonLivraisonRepository.findAll();
        assertThat(bonLivraisonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
