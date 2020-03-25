import { Moment } from 'moment';
import { IFacture } from 'app/shared/model/facture.model';
import { ILigneLivraison } from 'app/shared/model/ligne-livraison.model';
import { IBonDeCommande } from 'app/shared/model/bon-de-commande.model';

export interface IBonLivraison {
  id?: number;
  designationBonLivraison?: string;
  prixTotalBonLivraison?: number;
  dateBonLivraison?: Moment;
  userCertified?: number;
  dateCertif?: Moment;
  numCertif?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  factures?: IFacture[];
  ligneLivraisons?: ILigneLivraison[];
  bonDeCommande?: IBonDeCommande;
}

export class BonLivraison implements IBonLivraison {
  constructor(
    public id?: number,
    public designationBonLivraison?: string,
    public prixTotalBonLivraison?: number,
    public dateBonLivraison?: Moment,
    public userCertified?: number,
    public dateCertif?: Moment,
    public numCertif?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public factures?: IFacture[],
    public ligneLivraisons?: ILigneLivraison[],
    public bonDeCommande?: IBonDeCommande
  ) {}
}
