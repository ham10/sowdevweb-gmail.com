package com.projet.hpd.web.rest;

import com.projet.hpd.HpdApp;
import com.projet.hpd.domain.Produit;
import com.projet.hpd.repository.ProduitRepository;
import com.projet.hpd.service.ProduitService;
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
import java.util.List;

import static com.projet.hpd.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProduitResource} REST controller.
 */
@SpringBootTest(classes = HpdApp.class)
public class ProduitResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITE_STOCK = 1;
    private static final Integer UPDATED_QUANTITE_STOCK = 2;

    private static final Integer DEFAULT_STOCK_PROVISOIRE = 1;
    private static final Integer UPDATED_STOCK_PROVISOIRE = 2;

    private static final Boolean DEFAULT_TVA = false;
    private static final Boolean UPDATED_TVA = true;

    private static final Integer DEFAULT_PRIX_VENTE_UNITAIRE = 1;
    private static final Integer UPDATED_PRIX_VENTE_UNITAIRE = 2;

    private static final String DEFAULT_CODE_BARRE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_BARRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEUIL = 1;
    private static final Integer UPDATED_SEUIL = 2;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitService produitService;

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

    private MockMvc restProduitMockMvc;

    private Produit produit;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProduitResource produitResource = new ProduitResource(produitService);
        this.restProduitMockMvc = MockMvcBuilders.standaloneSetup(produitResource)
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
    public static Produit createEntity(EntityManager em) {
        Produit produit = new Produit()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE)
            .quantiteStock(DEFAULT_QUANTITE_STOCK)
            .stockProvisoire(DEFAULT_STOCK_PROVISOIRE)
            .tva(DEFAULT_TVA)
            .prixVenteUnitaire(DEFAULT_PRIX_VENTE_UNITAIRE)
            .codeBarre(DEFAULT_CODE_BARRE)
            .seuil(DEFAULT_SEUIL);
        return produit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createUpdatedEntity(EntityManager em) {
        Produit produit = new Produit()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .quantiteStock(UPDATED_QUANTITE_STOCK)
            .stockProvisoire(UPDATED_STOCK_PROVISOIRE)
            .tva(UPDATED_TVA)
            .prixVenteUnitaire(UPDATED_PRIX_VENTE_UNITAIRE)
            .codeBarre(UPDATED_CODE_BARRE)
            .seuil(UPDATED_SEUIL);
        return produit;
    }

    @BeforeEach
    public void initTest() {
        produit = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduit() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();

        // Create the Produit
        restProduitMockMvc.perform(post("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isCreated());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate + 1);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProduit.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testProduit.getQuantiteStock()).isEqualTo(DEFAULT_QUANTITE_STOCK);
        assertThat(testProduit.getStockProvisoire()).isEqualTo(DEFAULT_STOCK_PROVISOIRE);
        assertThat(testProduit.isTva()).isEqualTo(DEFAULT_TVA);
        assertThat(testProduit.getPrixVenteUnitaire()).isEqualTo(DEFAULT_PRIX_VENTE_UNITAIRE);
        assertThat(testProduit.getCodeBarre()).isEqualTo(DEFAULT_CODE_BARRE);
        assertThat(testProduit.getSeuil()).isEqualTo(DEFAULT_SEUIL);
    }

    @Test
    @Transactional
    public void createProduitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();

        // Create the Produit with an existing ID
        produit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduitMockMvc.perform(post("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProduits() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList
        restProduitMockMvc.perform(get("/api/produits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produit.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].quantiteStock").value(hasItem(DEFAULT_QUANTITE_STOCK)))
            .andExpect(jsonPath("$.[*].stockProvisoire").value(hasItem(DEFAULT_STOCK_PROVISOIRE)))
            .andExpect(jsonPath("$.[*].tva").value(hasItem(DEFAULT_TVA.booleanValue())))
            .andExpect(jsonPath("$.[*].prixVenteUnitaire").value(hasItem(DEFAULT_PRIX_VENTE_UNITAIRE)))
            .andExpect(jsonPath("$.[*].codeBarre").value(hasItem(DEFAULT_CODE_BARRE)))
            .andExpect(jsonPath("$.[*].seuil").value(hasItem(DEFAULT_SEUIL)));
    }
    
    @Test
    @Transactional
    public void getProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get the produit
        restProduitMockMvc.perform(get("/api/produits/{id}", produit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(produit.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.quantiteStock").value(DEFAULT_QUANTITE_STOCK))
            .andExpect(jsonPath("$.stockProvisoire").value(DEFAULT_STOCK_PROVISOIRE))
            .andExpect(jsonPath("$.tva").value(DEFAULT_TVA.booleanValue()))
            .andExpect(jsonPath("$.prixVenteUnitaire").value(DEFAULT_PRIX_VENTE_UNITAIRE))
            .andExpect(jsonPath("$.codeBarre").value(DEFAULT_CODE_BARRE))
            .andExpect(jsonPath("$.seuil").value(DEFAULT_SEUIL));
    }

    @Test
    @Transactional
    public void getNonExistingProduit() throws Exception {
        // Get the produit
        restProduitMockMvc.perform(get("/api/produits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduit() throws Exception {
        // Initialize the database
        produitService.save(produit);

        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit
        Produit updatedProduit = produitRepository.findById(produit.getId()).get();
        // Disconnect from session so that the updates on updatedProduit are not directly saved in db
        em.detach(updatedProduit);
        updatedProduit
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE)
            .quantiteStock(UPDATED_QUANTITE_STOCK)
            .stockProvisoire(UPDATED_STOCK_PROVISOIRE)
            .tva(UPDATED_TVA)
            .prixVenteUnitaire(UPDATED_PRIX_VENTE_UNITAIRE)
            .codeBarre(UPDATED_CODE_BARRE)
            .seuil(UPDATED_SEUIL);

        restProduitMockMvc.perform(put("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduit)))
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProduit.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testProduit.getQuantiteStock()).isEqualTo(UPDATED_QUANTITE_STOCK);
        assertThat(testProduit.getStockProvisoire()).isEqualTo(UPDATED_STOCK_PROVISOIRE);
        assertThat(testProduit.isTva()).isEqualTo(UPDATED_TVA);
        assertThat(testProduit.getPrixVenteUnitaire()).isEqualTo(UPDATED_PRIX_VENTE_UNITAIRE);
        assertThat(testProduit.getCodeBarre()).isEqualTo(UPDATED_CODE_BARRE);
        assertThat(testProduit.getSeuil()).isEqualTo(UPDATED_SEUIL);
    }

    @Test
    @Transactional
    public void updateNonExistingProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Create the Produit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitMockMvc.perform(put("/api/produits")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(produit)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduit() throws Exception {
        // Initialize the database
        produitService.save(produit);

        int databaseSizeBeforeDelete = produitRepository.findAll().size();

        // Delete the produit
        restProduitMockMvc.perform(delete("/api/produits/{id}", produit.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
