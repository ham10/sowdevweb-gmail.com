import { Moment } from 'moment';
import { IBonDeCommande } from 'app/shared/model/bon-de-commande.model';
import { IFournisseur } from 'app/shared/model/fournisseur.model';

export interface IOffre {
  id?: number;
  libelle?: Moment;
  date?: Moment;
  montant?: number;
  taxe?: number;
  numMarche?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  bonDeCommande?: IBonDeCommande;
  fournisseur?: IFournisseur;
}

export class Offre implements IOffre {
  constructor(
    public id?: number,
    public libelle?: Moment,
    public date?: Moment,
    public montant?: number,
    public taxe?: number,
    public numMarche?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public bonDeCommande?: IBonDeCommande,
    public fournisseur?: IFournisseur
  ) {}
}
