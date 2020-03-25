import { Moment } from 'moment';

export interface ITypeNotif {
  id?: number;
  codeTypeNotif?: string;
  libelleTypeNotif?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class TypeNotif implements ITypeNotif {
  constructor(
    public id?: number,
    public codeTypeNotif?: string,
    public libelleTypeNotif?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
