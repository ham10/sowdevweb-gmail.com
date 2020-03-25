import { Moment } from 'moment';

export interface ITypeReport {
  id?: number;
  codeTypeReport?: string;
  libelleTypeReport?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class TypeReport implements ITypeReport {
  constructor(
    public id?: number,
    public codeTypeReport?: string,
    public libelleTypeReport?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
