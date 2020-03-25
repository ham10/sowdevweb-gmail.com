import { Moment } from 'moment';
import { IAyantDroit } from 'app/shared/model/ayant-droit.model';

export interface ITypePrCharge {
  id?: number;
  codeTypePrCharge?: string;
  libelleTypePrCharge?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  ayantDroits?: IAyantDroit[];
}

export class TypePrCharge implements ITypePrCharge {
  constructor(
    public id?: number,
    public codeTypePrCharge?: string,
    public libelleTypePrCharge?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public ayantDroits?: IAyantDroit[]
  ) {}
}
