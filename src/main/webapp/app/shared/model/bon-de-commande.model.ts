import { Moment } from 'moment';
import { IBonLivraison } from 'app/shared/model/bon-livraison.model';
import { ILigneCommande } from 'app/shared/model/ligne-commande.model';
import { IOffre } from 'app/shared/model/offre.model';
import { IServices } from 'app/shared/model/services.model';
import { IEtatBonCom } from 'app/shared/model/etat-bon-com.model';
import { ITypeBonCom } from 'app/shared/model/type-bon-com.model';

export interface IBonDeCommande {
  id?: number;
  numero?: string;
  libelle?: string;
  prixTotal?: number;
  dateComm?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  bonLivraison?: IBonLivraison;
  ligneCommandes?: ILigneCommande[];
  offre?: IOffre;
  serv?: IServices;
  etatBonCommande?: IEtatBonCom;
  typeBonDeCommande?: ITypeBonCom;
}

export class BonDeCommande implements IBonDeCommande {
  constructor(
    public id?: number,
    public numero?: string,
    public libelle?: string,
    public prixTotal?: number,
    public dateComm?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public bonLivraison?: IBonLivraison,
    public ligneCommandes?: ILigneCommande[],
    public offre?: IOffre,
    public serv?: IServices,
    public etatBonCommande?: IEtatBonCom,
    public typeBonDeCommande?: ITypeBonCom
  ) {}
}
