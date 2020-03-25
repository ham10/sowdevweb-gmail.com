package com.projet.hpd.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.projet.hpd.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.projet.hpd.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.projet.hpd.domain.User.class.getName());
            createCache(cm, com.projet.hpd.domain.Authority.class.getName());
            createCache(cm, com.projet.hpd.domain.User.class.getName() + ".authorities");
            createCache(cm, com.projet.hpd.domain.Pays.class.getName());
            createCache(cm, com.projet.hpd.domain.Pays.class.getName() + ".regions");
            createCache(cm, com.projet.hpd.domain.Region.class.getName());
            createCache(cm, com.projet.hpd.domain.Region.class.getName() + ".departements");
            createCache(cm, com.projet.hpd.domain.Departement.class.getName());
            createCache(cm, com.projet.hpd.domain.Departement.class.getName() + ".etablis");
            createCache(cm, com.projet.hpd.domain.Etablis.class.getName());
            createCache(cm, com.projet.hpd.domain.TypePole.class.getName());
            createCache(cm, com.projet.hpd.domain.TypePole.class.getName() + ".poles");
            createCache(cm, com.projet.hpd.domain.TypeUnite.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeUnite.class.getName() + ".unites");
            createCache(cm, com.projet.hpd.domain.Pole.class.getName());
            createCache(cm, com.projet.hpd.domain.Unite.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeServices.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeServices.class.getName() + ".services");
            createCache(cm, com.projet.hpd.domain.Devise.class.getName());
            createCache(cm, com.projet.hpd.domain.Devise.class.getName() + ".tauxDevises");
            createCache(cm, com.projet.hpd.domain.Devise.class.getName() + ".monnaies");
            createCache(cm, com.projet.hpd.domain.TauxDevise.class.getName());
            createCache(cm, com.projet.hpd.domain.Plateau.class.getName());
            createCache(cm, com.projet.hpd.domain.DeptServices.class.getName());
            createCache(cm, com.projet.hpd.domain.DeptServices.class.getName() + ".services");
            createCache(cm, com.projet.hpd.domain.Specialite.class.getName());
            createCache(cm, com.projet.hpd.domain.Specialite.class.getName() + ".services");
            createCache(cm, com.projet.hpd.domain.Monnaie.class.getName());
            createCache(cm, com.projet.hpd.domain.TypePlateau.class.getName());
            createCache(cm, com.projet.hpd.domain.TypePlateau.class.getName() + ".plateaus");
            createCache(cm, com.projet.hpd.domain.Boxe.class.getName());
            createCache(cm, com.projet.hpd.domain.Boxe.class.getName() + ".lits");
            createCache(cm, com.projet.hpd.domain.TypeLit.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeLit.class.getName() + ".lits");
            createCache(cm, com.projet.hpd.domain.CatChambre.class.getName());
            createCache(cm, com.projet.hpd.domain.CatChambre.class.getName() + ".chambres");
            createCache(cm, com.projet.hpd.domain.CatChambre.class.getName() + ".tarifs");
            createCache(cm, com.projet.hpd.domain.Chambre.class.getName());
            createCache(cm, com.projet.hpd.domain.Chambre.class.getName() + ".boxes");
            createCache(cm, com.projet.hpd.domain.JourFerie.class.getName());
            createCache(cm, com.projet.hpd.domain.Lit.class.getName());
            createCache(cm, com.projet.hpd.domain.Jour.class.getName());
            createCache(cm, com.projet.hpd.domain.Jour.class.getName() + ".horaireCons");
            createCache(cm, com.projet.hpd.domain.ParamDivers.class.getName());
            createCache(cm, com.projet.hpd.domain.GroupeSan.class.getName());
            createCache(cm, com.projet.hpd.domain.HoraireCon.class.getName());
            createCache(cm, com.projet.hpd.domain.CodeBudget.class.getName());
            createCache(cm, com.projet.hpd.domain.ChapCompta.class.getName());
            createCache(cm, com.projet.hpd.domain.ChapCompta.class.getName() + ".compteGenes");
            createCache(cm, com.projet.hpd.domain.CompteGene.class.getName());
            createCache(cm, com.projet.hpd.domain.CompteGene.class.getName() + ".codeBudgets");
            createCache(cm, com.projet.hpd.domain.CompteGene.class.getName() + ".banques");
            createCache(cm, com.projet.hpd.domain.CompteGene.class.getName() + ".services");
            createCache(cm, com.projet.hpd.domain.CompteGene.class.getName() + ".comptes");
            createCache(cm, com.projet.hpd.domain.TypeCaisse.class.getName());
            createCache(cm, com.projet.hpd.domain.TypePrCharge.class.getName());
            createCache(cm, com.projet.hpd.domain.TypePrCharge.class.getName() + ".ayantDroits");
            createCache(cm, com.projet.hpd.domain.TypeSoins.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeSoins.class.getName() + ".tarifs");
            createCache(cm, com.projet.hpd.domain.TypeNotif.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeAntecedent.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeMvtStock.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeMvtStock.class.getName() + ".mouvements");
            createCache(cm, com.projet.hpd.domain.TypeFacture.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeDoc.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeFournis.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeSortie.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeSortie.class.getName() + ".typeMvtStocks");
            createCache(cm, com.projet.hpd.domain.TypeChamps.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeMagasin.class.getName());
            createCache(cm, com.projet.hpd.domain.TypePatient.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeConstante.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeConstante.class.getName() + ".ficheMedicals");
            createCache(cm, com.projet.hpd.domain.TypePlat.class.getName());
            createCache(cm, com.projet.hpd.domain.TypePlat.class.getName() + ".plats");
            createCache(cm, com.projet.hpd.domain.TypeFact.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeReport.class.getName());
            createCache(cm, com.projet.hpd.domain.ParamSys.class.getName());
            createCache(cm, com.projet.hpd.domain.ModeRegle.class.getName());
            createCache(cm, com.projet.hpd.domain.CatReport.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeProd.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeProd.class.getName() + ".produits");
            createCache(cm, com.projet.hpd.domain.Calendrier.class.getName());
            createCache(cm, com.projet.hpd.domain.FormeProd.class.getName());
            createCache(cm, com.projet.hpd.domain.ParamCode.class.getName());
            createCache(cm, com.projet.hpd.domain.SitMat.class.getName());
            createCache(cm, com.projet.hpd.domain.Civilite.class.getName());
            createCache(cm, com.projet.hpd.domain.ClasseProd.class.getName());
            createCache(cm, com.projet.hpd.domain.CatMagasin.class.getName());
            createCache(cm, com.projet.hpd.domain.TypePiece.class.getName());
            createCache(cm, com.projet.hpd.domain.Banque.class.getName());
            createCache(cm, com.projet.hpd.domain.Module.class.getName());
            createCache(cm, com.projet.hpd.domain.Module.class.getName() + ".items");
            createCache(cm, com.projet.hpd.domain.Item.class.getName());
            createCache(cm, com.projet.hpd.domain.Item.class.getName() + ".fonctionnalites");
            createCache(cm, com.projet.hpd.domain.Fonctionnalite.class.getName());
            createCache(cm, com.projet.hpd.domain.Activite.class.getName());
            createCache(cm, com.projet.hpd.domain.Activite.class.getName() + ".evenements");
            createCache(cm, com.projet.hpd.domain.Evenement.class.getName());
            createCache(cm, com.projet.hpd.domain.MachAutorise.class.getName());
            createCache(cm, com.projet.hpd.domain.Patient.class.getName());
            createCache(cm, com.projet.hpd.domain.Patient.class.getName() + ".dosMedicals");
            createCache(cm, com.projet.hpd.domain.Patient.class.getName() + ".hospitalisations");
            createCache(cm, com.projet.hpd.domain.Patient.class.getName() + ".comptes");
            createCache(cm, com.projet.hpd.domain.FicheMedical.class.getName());
            createCache(cm, com.projet.hpd.domain.FicheMedical.class.getName() + ".dossierMedicals");
            createCache(cm, com.projet.hpd.domain.FicheMedical.class.getName() + ".resultatActes");
            createCache(cm, com.projet.hpd.domain.FicheMedical.class.getName() + ".ordonnances");
            createCache(cm, com.projet.hpd.domain.FicheMedical.class.getName() + ".vaccins");
            createCache(cm, com.projet.hpd.domain.FicheMedical.class.getName() + ".typeConstantes");
            createCache(cm, com.projet.hpd.domain.DosMedical.class.getName());
            createCache(cm, com.projet.hpd.domain.DosMedical.class.getName() + ".antecedents");
            createCache(cm, com.projet.hpd.domain.Medecin.class.getName());
            createCache(cm, com.projet.hpd.domain.Ordonnance.class.getName());
            createCache(cm, com.projet.hpd.domain.Vaccin.class.getName());
            createCache(cm, com.projet.hpd.domain.Hospitalisation.class.getName());
            createCache(cm, com.projet.hpd.domain.Antecedent.class.getName());
            createCache(cm, com.projet.hpd.domain.Antecedent.class.getName() + ".dosMedicals");
            createCache(cm, com.projet.hpd.domain.ResultatActe.class.getName());
            createCache(cm, com.projet.hpd.domain.ResultatActe.class.getName() + ".acteMedicals");
            createCache(cm, com.projet.hpd.domain.ActeMedical.class.getName());
            createCache(cm, com.projet.hpd.domain.ActeMedical.class.getName() + ".tarifs");
            createCache(cm, com.projet.hpd.domain.ActeMedical.class.getName() + ".resultatActes");
            createCache(cm, com.projet.hpd.domain.Fournisseur.class.getName());
            createCache(cm, com.projet.hpd.domain.Fournisseur.class.getName() + ".offres");
            createCache(cm, com.projet.hpd.domain.Fournisseur.class.getName() + ".prodFournis");
            createCache(cm, com.projet.hpd.domain.TypeBonCom.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeBonCom.class.getName() + ".bonDeCommandes");
            createCache(cm, com.projet.hpd.domain.Services.class.getName());
            createCache(cm, com.projet.hpd.domain.Services.class.getName() + ".catChambres");
            createCache(cm, com.projet.hpd.domain.Services.class.getName() + ".dosMedicals");
            createCache(cm, com.projet.hpd.domain.Services.class.getName() + ".bonDeCommandes");
            createCache(cm, com.projet.hpd.domain.Services.class.getName() + ".plats");
            createCache(cm, com.projet.hpd.domain.LigneCommande.class.getName());
            createCache(cm, com.projet.hpd.domain.BonDeCommande.class.getName());
            createCache(cm, com.projet.hpd.domain.BonDeCommande.class.getName() + ".ligneCommandes");
            createCache(cm, com.projet.hpd.domain.EtatBonCom.class.getName());
            createCache(cm, com.projet.hpd.domain.EtatBonCom.class.getName() + ".bonDeCommandes");
            createCache(cm, com.projet.hpd.domain.Facture.class.getName());
            createCache(cm, com.projet.hpd.domain.Facture.class.getName() + ".operations");
            createCache(cm, com.projet.hpd.domain.Facture.class.getName() + ".echeanciers");
            createCache(cm, com.projet.hpd.domain.Offre.class.getName());
            createCache(cm, com.projet.hpd.domain.BonLivraison.class.getName());
            createCache(cm, com.projet.hpd.domain.BonLivraison.class.getName() + ".factures");
            createCache(cm, com.projet.hpd.domain.BonLivraison.class.getName() + ".ligneLivraisons");
            createCache(cm, com.projet.hpd.domain.LigneLivraison.class.getName());
            createCache(cm, com.projet.hpd.domain.Fichier.class.getName());
            createCache(cm, com.projet.hpd.domain.Compte.class.getName());
            createCache(cm, com.projet.hpd.domain.Compte.class.getName() + ".ecritures");
            createCache(cm, com.projet.hpd.domain.NatureOp.class.getName());
            createCache(cm, com.projet.hpd.domain.NatureOp.class.getName() + ".schemaComptas");
            createCache(cm, com.projet.hpd.domain.SchemaCompta.class.getName());
            createCache(cm, com.projet.hpd.domain.Operation.class.getName());
            createCache(cm, com.projet.hpd.domain.Operation.class.getName() + ".immos");
            createCache(cm, com.projet.hpd.domain.Operation.class.getName() + ".ecritures");
            createCache(cm, com.projet.hpd.domain.EtatOperation.class.getName());
            createCache(cm, com.projet.hpd.domain.EtatOperation.class.getName() + ".operations");
            createCache(cm, com.projet.hpd.domain.EtatCaisse.class.getName());
            createCache(cm, com.projet.hpd.domain.Ecriture.class.getName());
            createCache(cm, com.projet.hpd.domain.Caisse.class.getName());
            createCache(cm, com.projet.hpd.domain.Caisse.class.getName() + ".operations");
            createCache(cm, com.projet.hpd.domain.TabAmortis.class.getName());
            createCache(cm, com.projet.hpd.domain.TabAmortis.class.getName() + ".immos");
            createCache(cm, com.projet.hpd.domain.TypeImmo.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeImmo.class.getName() + ".immos");
            createCache(cm, com.projet.hpd.domain.Immo.class.getName());
            createCache(cm, com.projet.hpd.domain.EtatImmo.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeTarif.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeTarif.class.getName() + ".familles");
            createCache(cm, com.projet.hpd.domain.AyantDroit.class.getName());
            createCache(cm, com.projet.hpd.domain.Famille.class.getName());
            createCache(cm, com.projet.hpd.domain.Famille.class.getName() + ".sousFamilles");
            createCache(cm, com.projet.hpd.domain.SousFamille.class.getName());
            createCache(cm, com.projet.hpd.domain.SousFamille.class.getName() + ".tarifs");
            createCache(cm, com.projet.hpd.domain.Echeancier.class.getName());
            createCache(cm, com.projet.hpd.domain.Tarif.class.getName());
            createCache(cm, com.projet.hpd.domain.EtatFacture.class.getName());
            createCache(cm, com.projet.hpd.domain.Produit.class.getName());
            createCache(cm, com.projet.hpd.domain.Produit.class.getName() + ".typeConds");
            createCache(cm, com.projet.hpd.domain.Produit.class.getName() + ".mouvements");
            createCache(cm, com.projet.hpd.domain.Produit.class.getName() + ".inventaires");
            createCache(cm, com.projet.hpd.domain.Produit.class.getName() + ".prodFournis");
            createCache(cm, com.projet.hpd.domain.Plat.class.getName());
            createCache(cm, com.projet.hpd.domain.ProdFournis.class.getName());
            createCache(cm, com.projet.hpd.domain.ProdFournis.class.getName() + ".ligneLivraisons");
            createCache(cm, com.projet.hpd.domain.ProdFournis.class.getName() + ".ligneCommandes");
            createCache(cm, com.projet.hpd.domain.Depot.class.getName());
            createCache(cm, com.projet.hpd.domain.Depot.class.getName() + ".rayons");
            createCache(cm, com.projet.hpd.domain.Rayon.class.getName());
            createCache(cm, com.projet.hpd.domain.Rayon.class.getName() + ".etageres");
            createCache(cm, com.projet.hpd.domain.Etagere.class.getName());
            createCache(cm, com.projet.hpd.domain.Etagere.class.getName() + ".produits");
            createCache(cm, com.projet.hpd.domain.Mouvement.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeCond.class.getName());
            createCache(cm, com.projet.hpd.domain.Inventaire.class.getName());
            createCache(cm, com.projet.hpd.domain.TypePlanning.class.getName());
            createCache(cm, com.projet.hpd.domain.RDV.class.getName());
            createCache(cm, com.projet.hpd.domain.Planning.class.getName());
            createCache(cm, com.projet.hpd.domain.DetailPlanning.class.getName());
            createCache(cm, com.projet.hpd.domain.EtatRdv.class.getName());
            createCache(cm, com.projet.hpd.domain.EtatPlanning.class.getName());
            createCache(cm, com.projet.hpd.domain.Cible.class.getName());
            createCache(cm, com.projet.hpd.domain.Questionnaire.class.getName());
            createCache(cm, com.projet.hpd.domain.Annotation.class.getName());
            createCache(cm, com.projet.hpd.domain.Question.class.getName());
            createCache(cm, com.projet.hpd.domain.Reponse.class.getName());
            createCache(cm, com.projet.hpd.domain.TypeQuestion.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
