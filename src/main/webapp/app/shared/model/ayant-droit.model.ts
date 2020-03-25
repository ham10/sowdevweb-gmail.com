import { Moment } from 'moment';
import { ITypePrCharge } from 'app/shared/model/type-pr-charge.model';

export interface IAyantDroit {
  id?: number;
  code?: string;
  prenom?: string;
  nom?: string;
  lienParente?: string;
  dateNais?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  typePrCharge?: ITypePrCharge;
}

export class AyantDroit implements IAyantDroit {
  constructor(
    public id?: number,
    public code?: string,
    public prenom?: string,
    public nom?: string,
    public lienParente?: string,
    public dateNais?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public typePrCharge?: ITypePrCharge
  ) {}
}
