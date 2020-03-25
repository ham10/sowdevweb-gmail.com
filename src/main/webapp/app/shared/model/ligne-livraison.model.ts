import { Moment } from 'moment';
import { IProdFournis } from 'app/shared/model/prod-fournis.model';
import { IBonLivraison } from 'app/shared/model/bon-livraison.model';

export interface ILigneLivraison {
  id?: number;
  designation?: string;
  quantite?: number;
  prixUnitaire?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  produitFournisseur?: IProdFournis;
  bonDeLivraison?: IBonLivraison;
}

export class LigneLivraison implements ILigneLivraison {
  constructor(
    public id?: number,
    public designation?: string,
    public quantite?: number,
    public prixUnitaire?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public produitFournisseur?: IProdFournis,
    public bonDeLivraison?: IBonLivraison
  ) {}
}
