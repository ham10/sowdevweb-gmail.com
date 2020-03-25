import { Moment } from 'moment';

export interface ITypeFacture {
  id?: number;
  codeTypeFacture?: string;
  libelleTypeFacture?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class TypeFacture implements ITypeFacture {
  constructor(
    public id?: number,
    public codeTypeFacture?: string,
    public libelleTypeFacture?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
