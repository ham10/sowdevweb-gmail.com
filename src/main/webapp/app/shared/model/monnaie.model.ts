import { Moment } from 'moment';
import { IDevise } from 'app/shared/model/devise.model';

export interface IMonnaie {
  id?: number;
  libelleMonnaie?: string;
  montantMonnaie?: number;
  natureMonnaie?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  devise?: IDevise;
}

export class Monnaie implements IMonnaie {
  constructor(
    public id?: number,
    public libelleMonnaie?: string,
    public montantMonnaie?: number,
    public natureMonnaie?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public devise?: IDevise
  ) {}
}
