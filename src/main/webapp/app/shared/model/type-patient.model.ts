import { Moment } from 'moment';

export interface ITypePatient {
  id?: number;
  codeTypePatient?: string;
  libelleTypePatient?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class TypePatient implements ITypePatient {
  constructor(
    public id?: number,
    public codeTypePatient?: string,
    public libelleTypePatient?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
