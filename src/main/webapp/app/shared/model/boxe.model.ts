import { Moment } from 'moment';
import { ILit } from 'app/shared/model/lit.model';
import { IChambre } from 'app/shared/model/chambre.model';

export interface IBoxe {
  id?: number;
  numeroBoxe?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  lits?: ILit[];
  chambre?: IChambre;
}

export class Boxe implements IBoxe {
  constructor(
    public id?: number,
    public numeroBoxe?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public lits?: ILit[],
    public chambre?: IChambre
  ) {}
}
