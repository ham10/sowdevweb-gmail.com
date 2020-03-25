import { Moment } from 'moment';
import { ITauxDevise } from 'app/shared/model/taux-devise.model';
import { IMonnaie } from 'app/shared/model/monnaie.model';

export interface IDevise {
  id?: number;
  codeisoDvise?: string;
  libelleDevise?: string;
  descriptionDevise?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  tauxDevises?: ITauxDevise[];
  monnaies?: IMonnaie[];
}

export class Devise implements IDevise {
  constructor(
    public id?: number,
    public codeisoDvise?: string,
    public libelleDevise?: string,
    public descriptionDevise?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public tauxDevises?: ITauxDevise[],
    public monnaies?: IMonnaie[]
  ) {}
}
