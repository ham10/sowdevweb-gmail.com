import { Moment } from 'moment';

export interface ITypeDoc {
  id?: number;
  codeTypeDoc?: string;
  libelleTypeDoc?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class TypeDoc implements ITypeDoc {
  constructor(
    public id?: number,
    public codeTypeDoc?: string,
    public libelleTypeDoc?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
