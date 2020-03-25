import { IBonDeCommande } from 'app/shared/model/bon-de-commande.model';

export interface IEtatBonCom {
  id?: number;
  libelle?: string;
  code?: string;
  bonDeCommandes?: IBonDeCommande[];
}

export class EtatBonCom implements IEtatBonCom {
  constructor(public id?: number, public libelle?: string, public code?: string, public bonDeCommandes?: IBonDeCommande[]) {}
}
