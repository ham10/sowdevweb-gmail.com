import { Moment } from 'moment';
import { ILit } from 'app/shared/model/lit.model';

export interface ITypeLit {
  id?: number;
  codeTypeLit?: string;
  libelleTypeLit?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  lits?: ILit[];
}

export class TypeLit implements ITypeLit {
  constructor(
    public id?: number,
    public codeTypeLit?: string,
    public libelleTypeLit?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public lits?: ILit[]
  ) {}
}
