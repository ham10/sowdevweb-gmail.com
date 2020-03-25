// import { NgModule } from '@angular/core';
// import { RouterModule } from '@angular/router';
//
//  @NgModule({
//   imports: [
//     RouterModule.forChild([
//       {
//         path: 'pays',
//         loadChildren: () => import('./pays/pays.module').then(m => m.HpdPaysModule)
//       },
//       {
//         path: 'region',
//         loadChildren: () => import('./region/region.module').then(m => m.HpdRegionModule)
//       },
//       {
//         path: 'departement',
//         loadChildren: () => import('./departement/departement.module').then(m => m.HpdDepartementModule)
//       },
//       {
//         path: 'etablis',
//         loadChildren: () => import('./etablis/etablis.module').then(m => m.HpdEtablisModule)
//       },
//       {
//         path: 'type-pole',
//         loadChildren: () => import('./type-pole/type-pole.module').then(m => m.HpdTypePoleModule)
//       },
//       {
//         path: 'type-unite',
//         loadChildren: () => import('./type-unite/type-unite.module').then(m => m.HpdTypeUniteModule)
//       },
//       {
//         path: 'pole',
//         loadChildren: () => import('./pole/pole.module').then(m => m.HpdPoleModule)
//       },
//       {
//         path: 'unite',
//         loadChildren: () => import('./unite/unite.module').then(m => m.HpdUniteModule)
//       },
//       {
//         path: 'type-services',
//         loadChildren: () => import('./type-services/type-services.module').then(m => m.HpdTypeServicesModule)
//       },
//       {
//         path: 'devise',
//         loadChildren: () => import('./devise/devise.module').then(m => m.HpdDeviseModule)
//       },
//       {
//         path: 'taux-devise',
//         loadChildren: () => import('./taux-devise/taux-devise.module').then(m => m.HpdTauxDeviseModule)
//       },
//       {
//         path: 'plateau',
//         loadChildren: () => import('./plateau/plateau.module').then(m => m.HpdPlateauModule)
//       },
//       {
//         path: 'dept-services',
//         loadChildren: () => import('./dept-services/dept-services.module').then(m => m.HpdDeptServicesModule)
//       },
//       {
//         path: 'specialite',
//         loadChildren: () => import('./specialite/specialite.module').then(m => m.HpdSpecialiteModule)
//       },
//       {
//         path: 'monnaie',
//         loadChildren: () => import('./monnaie/monnaie.module').then(m => m.HpdMonnaieModule)
//       },
//       {
//         path: 'type-plateau',
//         loadChildren: () => import('./type-plateau/type-plateau.module').then(m => m.HpdTypePlateauModule)
//       },
//       {
//         path: 'boxe',
//         loadChildren: () => import('./boxe/boxe.module').then(m => m.HpdBoxeModule)
//       },
//       {
//         path: 'type-lit',
//         loadChildren: () => import('./type-lit/type-lit.module').then(m => m.HpdTypeLitModule)
//       },
//       {
//         path: 'cat-chambre',
//         loadChildren: () => import('./cat-chambre/cat-chambre.module').then(m => m.HpdCatChambreModule)
//       },
//       {
//         path: 'chambre',
//         loadChildren: () => import('./chambre/chambre.module').then(m => m.HpdChambreModule)
//       },
//       {
//         path: 'jour-ferie',
//         loadChildren: () => import('./jour-ferie/jour-ferie.module').then(m => m.HpdJourFerieModule)
//       },
//       {
//         path: 'lit',
//         loadChildren: () => import('./lit/lit.module').then(m => m.HpdLitModule)
//       },
//       {
//         path: 'jour',
//         loadChildren: () => import('./jour/jour.module').then(m => m.HpdJourModule)
//       },
//       {
//         path: 'param-divers',
//         loadChildren: () => import('./param-divers/param-divers.module').then(m => m.HpdParamDiversModule)
//       },
//       {
//         path: 'groupe-san',
//         loadChildren: () => import('./groupe-san/groupe-san.module').then(m => m.HpdGroupeSanModule)
//       },
//       {
//         path: 'horaire-con',
//         loadChildren: () => import('./horaire-con/horaire-con.module').then(m => m.HpdHoraireConModule)
//       },
//       {
//         path: 'code-budget',
//         loadChildren: () => import('./code-budget/code-budget.module').then(m => m.HpdCodeBudgetModule)
//       },
//       {
//         path: 'chap-compta',
//         loadChildren: () => import('./chap-compta/chap-compta.module').then(m => m.HpdChapComptaModule)
//       },
//       {
//         path: 'compte-gene',
//         loadChildren: () => import('./compte-gene/compte-gene.module').then(m => m.HpdCompteGeneModule)
//       },
//       {
//         path: 'type-caisse',
//         loadChildren: () => import('./type-caisse/type-caisse.module').then(m => m.HpdTypeCaisseModule)
//       },
//       {
//         path: 'type-pr-charge',
//         loadChildren: () => import('./type-pr-charge/type-pr-charge.module').then(m => m.HpdTypePrChargeModule)
//       },
//       {
//         path: 'type-soins',
//         loadChildren: () => import('./type-soins/type-soins.module').then(m => m.HpdTypeSoinsModule)
//       },
//       {
//         path: 'type-notif',
//         loadChildren: () => import('./type-notif/type-notif.module').then(m => m.HpdTypeNotifModule)
//       },
//       {
//         path: 'type-antecedent',
//         loadChildren: () => import('./type-antecedent/type-antecedent.module').then(m => m.HpdTypeAntecedentModule)
//       },
//       {
//         path: 'type-mvt-stock',
//         loadChildren: () => import('./type-mvt-stock/type-mvt-stock.module').then(m => m.HpdTypeMvtStockModule)
//       },
//       {
//         path: 'type-facture',
//         loadChildren: () => import('./type-facture/type-facture.module').then(m => m.HpdTypeFactureModule)
//       },
//       {
//         path: 'type-doc',
//         loadChildren: () => import('./type-doc/type-doc.module').then(m => m.HpdTypeDocModule)
//       },
//       {
//         path: 'type-fournis',
//         loadChildren: () => import('./type-fournis/type-fournis.module').then(m => m.HpdTypeFournisModule)
//       },
//       {
//         path: 'type-sortie',
//         loadChildren: () => import('./type-sortie/type-sortie.module').then(m => m.HpdTypeSortieModule)
//       },
//       {
//         path: 'type-champs',
//         loadChildren: () => import('./type-champs/type-champs.module').then(m => m.HpdTypeChampsModule)
//       },
//       {
//         path: 'type-magasin',
//         loadChildren: () => import('./type-magasin/type-magasin.module').then(m => m.HpdTypeMagasinModule)
//       },
//       {
//         path: 'type-patient',
//         loadChildren: () => import('./type-patient/type-patient.module').then(m => m.HpdTypePatientModule)
//       },
//       {
//         path: 'type-constante',
//         loadChildren: () => import('./type-constante/type-constante.module').then(m => m.HpdTypeConstanteModule)
//       },
//       {
//         path: 'type-plat',
//         loadChildren: () => import('./type-plat/type-plat.module').then(m => m.HpdTypePlatModule)
//       },
//       {
//         path: 'type-fact',
//         loadChildren: () => import('./type-fact/type-fact.module').then(m => m.HpdTypeFactModule)
//       },
//       {
//         path: 'type-report',
//         loadChildren: () => import('./type-report/type-report.module').then(m => m.HpdTypeReportModule)
//       },
//       {
//         path: 'param-sys',
//         loadChildren: () => import('./param-sys/param-sys.module').then(m => m.HpdParamSysModule)
//       },
//       {
//         path: 'mode-regle',
//         loadChildren: () => import('./mode-regle/mode-regle.module').then(m => m.HpdModeRegleModule)
//       },
//       {
//         path: 'cat-report',
//         loadChildren: () => import('./cat-report/cat-report.module').then(m => m.HpdCatReportModule)
//       },
//       {
//         path: 'type-prod',
//         loadChildren: () => import('./type-prod/type-prod.module').then(m => m.HpdTypeProdModule)
//       },
//       {
//         path: 'calendrier',
//         loadChildren: () => import('./calendrier/calendrier.module').then(m => m.HpdCalendrierModule)
//       },
//       {
//         path: 'forme-prod',
//         loadChildren: () => import('./forme-prod/forme-prod.module').then(m => m.HpdFormeProdModule)
//       },
//       {
//         path: 'param-code',
//         loadChildren: () => import('./param-code/param-code.module').then(m => m.HpdParamCodeModule)
//       },
//       {
//         path: 'sit-mat',
//         loadChildren: () => import('./sit-mat/sit-mat.module').then(m => m.HpdSitMatModule)
//       },
//       {
//         path: 'civilite',
//         loadChildren: () => import('./civilite/civilite.module').then(m => m.HpdCiviliteModule)
//       },
//       {
//         path: 'classe-prod',
//         loadChildren: () => import('./classe-prod/classe-prod.module').then(m => m.HpdClasseProdModule)
//       },
//       {
//         path: 'cat-magasin',
//         loadChildren: () => import('./cat-magasin/cat-magasin.module').then(m => m.HpdCatMagasinModule)
//       },
//       {
//         path: 'type-piece',
//         loadChildren: () => import('./type-piece/type-piece.module').then(m => m.HpdTypePieceModule)
//       },
//       {
//         path: 'banque',
//         loadChildren: () => import('./banque/banque.module').then(m => m.HpdBanqueModule)
//       },
//       {
//         path: 'module',
//         loadChildren: () => import('./module/module.module').then(m => m.HpdModuleModule)
//       },
//       {
//         path: 'item',
//         loadChildren: () => import('./item/item.module').then(m => m.HpdItemModule)
//       },
//       {
//         path: 'fonctionnalite',
//         loadChildren: () => import('./fonctionnalite/fonctionnalite.module').then(m => m.HpdFonctionnaliteModule)
//       },
//       {
//         path: 'activite',
//         loadChildren: () => import('./activite/activite.module').then(m => m.HpdActiviteModule)
//       },
//       {
//         path: 'evenement',
//         loadChildren: () => import('./evenement/evenement.module').then(m => m.HpdEvenementModule)
//       },
//       {
//         path: 'mach-autorise',
//         loadChildren: () => import('./mach-autorise/mach-autorise.module').then(m => m.HpdMachAutoriseModule)
//       },
//       {
//         path: 'patient',
//         loadChildren: () => import('./patient/patient.module').then(m => m.HpdPatientModule)
//       },
//       {
//         path: 'fiche-medical',
//         loadChildren: () => import('./fiche-medical/fiche-medical.module').then(m => m.HpdFicheMedicalModule)
//       },
//       {
//         path: 'dos-medical',
//         loadChildren: () => import('./dos-medical/dos-medical.module').then(m => m.HpdDosMedicalModule)
//       },
//       {
//         path: 'medecin',
//         loadChildren: () => import('./medecin/medecin.module').then(m => m.HpdMedecinModule)
//       },
//       {
//         path: 'ordonnance',
//         loadChildren: () => import('./ordonnance/ordonnance.module').then(m => m.HpdOrdonnanceModule)
//       },
//       {
//         path: 'vaccin',
//         loadChildren: () => import('./vaccin/vaccin.module').then(m => m.HpdVaccinModule)
//       },
//       // {
//       //   path: 'hospitalisation',
//       //   loadChildren: () => import('./hospitalisation/hospitalisation.module').then(m => m.HpdHospitalisationModule)
//       // },
//       {
//         path: 'antecedent',
//         loadChildren: () => import('./antecedent/antecedent.module').then(m => m.HpdAntecedentModule)
//       },
//       {
//         path: 'resultat-acte',
//         loadChildren: () => import('./resultat-acte/resultat-acte.module').then(m => m.HpdResultatActeModule)
//       },
//       {
//         path: 'acte-medical',
//         loadChildren: () => import('./acte-medical/acte-medical.module').then(m => m.HpdActeMedicalModule)
//       },
//       {
//         path: 'fournisseur',
//         loadChildren: () => import('./fournisseur/fournisseur.module').then(m => m.HpdFournisseurModule)
//       },
//       {
//         path: 'type-bon-com',
//         loadChildren: () => import('./type-bon-com/type-bon-com.module').then(m => m.HpdTypeBonComModule)
//       },
//       {
//         path: 'services',
//         loadChildren: () => import('./services/services.module').then(m => m.HpdServicesModule)
//       },
//       {
//         path: 'ligne-commande',
//         loadChildren: () => import('./ligne-commande/ligne-commande.module').then(m => m.HpdLigneCommandeModule)
//       },
//       {
//         path: 'bon-de-commande',
//         loadChildren: () => import('./bon-de-commande/bon-de-commande.module').then(m => m.HpdBonDeCommandeModule)
//       },
//       {
//         path: 'etat-bon-com',
//         loadChildren: () => import('./etat-bon-com/etat-bon-com.module').then(m => m.HpdEtatBonComModule)
//       },
//       {
//         path: 'facture',
//         loadChildren: () => import('./facture/facture.module').then(m => m.HpdFactureModule)
//       },
//       {
//         path: 'offre',
//         loadChildren: () => import('./offre/offre.module').then(m => m.HpdOffreModule)
//       },
//       {
//         path: 'bon-livraison',
//         loadChildren: () => import('./bon-livraison/bon-livraison.module').then(m => m.HpdBonLivraisonModule)
//       },
//       {
//         path: 'ligne-livraison',
//         loadChildren: () => import('./ligne-livraison/ligne-livraison.module').then(m => m.HpdLigneLivraisonModule)
//       },
//       {
//         path: 'fichier',
//         loadChildren: () => import('./fichier/fichier.module').then(m => m.HpdFichierModule)
//       },
//       {
//         path: 'compte',
//         loadChildren: () => import('./compte/compte.module').then(m => m.HpdCompteModule)
//       },
//       {
//         path: 'nature-op',
//         loadChildren: () => import('./nature-op/nature-op.module').then(m => m.HpdNatureOpModule)
//       },
//       {
//         path: 'schema-compta',
//         loadChildren: () => import('./schema-compta/schema-compta.module').then(m => m.HpdSchemaComptaModule)
//       },
//       {
//         path: 'operation',
//         loadChildren: () => import('./operation/operation.module').then(m => m.HpdOperationModule)
//       },
//       {
//         path: 'etat-operation',
//         loadChildren: () => import('./etat-operation/etat-operation.module').then(m => m.HpdEtatOperationModule)
//       },
//       {
//         path: 'etat-caisse',
//         loadChildren: () => import('./etat-caisse/etat-caisse.module').then(m => m.HpdEtatCaisseModule)
//       },
//       {
//         path: 'ecriture',
//         loadChildren: () => import('./ecriture/ecriture.module').then(m => m.HpdEcritureModule)
//       },
//       {
//         path: 'caisse',
//         loadChildren: () => import('./caisse/caisse.module').then(m => m.HpdCaisseModule)
//       },
//       {
//         path: 'tab-amortis',
//         loadChildren: () => import('./tab-amortis/tab-amortis.module').then(m => m.HpdTabAmortisModule)
//       },
//       {
//         path: 'type-immo',
//         loadChildren: () => import('./type-immo/type-immo.module').then(m => m.HpdTypeImmoModule)
//       },
//       {
//         path: 'immo',
//         loadChildren: () => import('./immo/immo.module').then(m => m.HpdImmoModule)
//       },
//       {
//         path: 'etat-immo',
//         loadChildren: () => import('./etat-immo/etat-immo.module').then(m => m.HpdEtatImmoModule)
//       },
//       {
//         path: 'type-tarif',
//         loadChildren: () => import('./type-tarif/type-tarif.module').then(m => m.HpdTypeTarifModule)
//       },
//       {
//         path: 'ayant-droit',
//         loadChildren: () => import('./ayant-droit/ayant-droit.module').then(m => m.HpdAyantDroitModule)
//       },
//       {
//         path: 'famille',
//         loadChildren: () => import('./famille/famille.module').then(m => m.HpdFamilleModule)
//       },
//       {
//         path: 'sous-famille',
//         loadChildren: () => import('./sous-famille/sous-famille.module').then(m => m.HpdSousFamilleModule)
//       },
//       {
//         path: 'echeancier',
//         loadChildren: () => import('./echeancier/echeancier.module').then(m => m.HpdEcheancierModule)
//       },
//       {
//         path: 'tarif',
//         loadChildren: () => import('./tarif/tarif.module').then(m => m.HpdTarifModule)
//       },
//       {
//         path: 'etat-facture',
//         loadChildren: () => import('./etat-facture/etat-facture.module').then(m => m.HpdEtatFactureModule)
//       },
//       {
//         path: 'produit',
//         loadChildren: () => import('./produit/produit.module').then(m => m.HpdProduitModule)
//       },
//       {
//         path: 'plat',
//         loadChildren: () => import('./plat/plat.module').then(m => m.HpdPlatModule)
//       },
//       {
//         path: 'prod-fournis',
//         loadChildren: () => import('./prod-fournis/prod-fournis.module').then(m => m.HpdProdFournisModule)
//       },
//       {
//         path: 'depot',
//         loadChildren: () => import('./depot/depot.module').then(m => m.HpdDepotModule)
//       },
//       {
//         path: 'rayon',
//         loadChildren: () => import('./rayon/rayon.module').then(m => m.HpdRayonModule)
//       },
//       {
//         path: 'etagere',
//         loadChildren: () => import('./etagere/etagere.module').then(m => m.HpdEtagereModule)
//       },
//       {
//         path: 'mouvement',
//         loadChildren: () => import('./mouvement/mouvement.module').then(m => m.HpdMouvementModule)
//       },
//       {
//         path: 'type-cond',
//         loadChildren: () => import('./type-cond/type-cond.module').then(m => m.HpdTypeCondModule)
//       },
//       {
//         path: 'inventaire',
//         loadChildren: () => import('./inventaire/inventaire.module').then(m => m.HpdInventaireModule)
//       },
//       {
//         path: 'type-planning',
//         loadChildren: () => import('./type-planning/type-planning.module').then(m => m.HpdTypePlanningModule)
//       },
//       {
//         path: 'rdv',
//         loadChildren: () => import('./rdv/rdv.module').then(m => m.HpdRDVModule)
//       },
//       {
//         path: 'planning',
//         loadChildren: () => import('./planning/planning.module').then(m => m.HpdPlanningModule)
//       },
//       {
//         path: 'detail-planning',
//         loadChildren: () => import('./detail-planning/detail-planning.module').then(m => m.HpdDetailPlanningModule)
//       },
//       {
//         path: 'etat-rdv',
//         loadChildren: () => import('./etat-rdv/etat-rdv.module').then(m => m.HpdEtatRdvModule)
//       },
//       {
//         path: 'etat-planning',
//         loadChildren: () => import('./etat-planning/etat-planning.module').then(m => m.HpdEtatPlanningModule)
//       },
//       {
//         path: 'cible',
//         loadChildren: () => import('./cible/cible.module').then(m => m.HpdCibleModule)
//       },
//       {
//         path: 'questionnaire',
//         loadChildren: () => import('./questionnaire/questionnaire.module').then(m => m.HpdQuestionnaireModule)
//       },
//       {
//         path: 'annotation',
//         loadChildren: () => import('./annotation/annotation.module').then(m => m.HpdAnnotationModule)
//       },
//       {
//         path: 'question',
//         loadChildren: () => import('./question/question.module').then(m => m.HpdQuestionModule)
//       },
//       {
//         path: 'reponse',
//         loadChildren: () => import('./reponse/reponse.module').then(m => m.HpdReponseModule)
//       },
//       {
//         path: 'type-question',
//         loadChildren: () => import('./type-question/type-question.module').then(m => m.HpdTypeQuestionModule)
//       }
//       /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
//     ])
//   ]
// })
// export class HpdEntityModule {}
