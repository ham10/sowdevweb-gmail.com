import { Moment } from 'moment';
import { IBonDeCommande } from 'app/shared/model/bon-de-commande.model';

export interface ITypeBonCom {
  id?: number;
  libelleTypeBonCom?: string;
  codeTypeBonCom?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  bonDeCommandes?: IBonDeCommande[];
}

export class TypeBonCom implements ITypeBonCom {
  constructor(
    public id?: number,
    public libelleTypeBonCom?: string,
    public codeTypeBonCom?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public bonDeCommandes?: IBonDeCommande[]
  ) {}
}
