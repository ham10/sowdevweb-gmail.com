import { Moment } from 'moment';
import { IImmo } from 'app/shared/model/immo.model';

export interface ITypeImmo {
  id?: number;
  code?: string;
  modeCalcul?: string;
  taux?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  immos?: IImmo[];
}

export class TypeImmo implements ITypeImmo {
  constructor(
    public id?: number,
    public code?: string,
    public modeCalcul?: string,
    public taux?: number,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public immos?: IImmo[]
  ) {}
}
