import { Moment } from 'moment';
import { ITarif } from 'app/shared/model/tarif.model';

export interface ITypeSoins {
  id?: number;
  codeTypeSoins?: string;
  libelleTypeSoins?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  tarifs?: ITarif[];
}

export class TypeSoins implements ITypeSoins {
  constructor(
    public id?: number,
    public codeTypeSoins?: string,
    public libelleTypeSoins?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public tarifs?: ITarif[]
  ) {}
}
