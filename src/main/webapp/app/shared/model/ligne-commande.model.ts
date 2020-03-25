import { Moment } from 'moment';
import { IProdFournis } from 'app/shared/model/prod-fournis.model';
import { IBonDeCommande } from 'app/shared/model/bon-de-commande.model';

export interface ILigneCommande {
  id?: number;
  produit?: string;
  quantite?: number;
  prixUnitaire?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  produitFournisseur?: IProdFournis;
  bonDeCommande?: IBonDeCommande;
}

export class LigneCommande implements ILigneCommande {
  constructor(
    public id?: number,
    public produit?: string,
    public quantite?: number,
    public prixUnitaire?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public produitFournisseur?: IProdFournis,
    public bonDeCommande?: IBonDeCommande
  ) {}
}
