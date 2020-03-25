import { Moment } from 'moment';

export interface ITypeAntecedent {
  id?: number;
  codeTypeAntecedent?: string;
  libelleTypeAntecedent?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class TypeAntecedent implements ITypeAntecedent {
  constructor(
    public id?: number,
    public codeTypeAntecedent?: string,
    public libelleTypeAntecedent?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
