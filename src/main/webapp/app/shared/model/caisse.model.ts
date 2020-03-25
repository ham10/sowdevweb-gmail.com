import { Moment } from 'moment';
import { IOperation } from 'app/shared/model/operation.model';

export interface ICaisse {
  id?: number;
  numero?: number;
  soldeMin?: Moment;
  soldeMax?: string;
  montant?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  operations?: IOperation[];
}

export class Caisse implements ICaisse {
  constructor(
    public id?: number,
    public numero?: number,
    public soldeMin?: Moment,
    public soldeMax?: string,
    public montant?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public operations?: IOperation[]
  ) {}
}
