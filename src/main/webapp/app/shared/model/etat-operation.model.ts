import { Moment } from 'moment';
import { IOperation } from 'app/shared/model/operation.model';

export interface IEtatOperation {
  id?: number;
  code?: number;
  libelle?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  operations?: IOperation[];
}

export class EtatOperation implements IEtatOperation {
  constructor(
    public id?: number,
    public code?: number,
    public libelle?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public operations?: IOperation[]
  ) {}
}
