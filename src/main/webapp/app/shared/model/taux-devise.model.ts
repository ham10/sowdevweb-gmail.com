import { Moment } from 'moment';
import { IDevise } from 'app/shared/model/devise.model';

export interface ITauxDevise {
  id?: number;
  sensTauxDevise?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  devise?: IDevise;
}

export class TauxDevise implements ITauxDevise {
  constructor(
    public id?: number,
    public sensTauxDevise?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public devise?: IDevise
  ) {}
}
