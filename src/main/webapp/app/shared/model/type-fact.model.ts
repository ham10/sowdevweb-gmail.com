import { Moment } from 'moment';

export interface ITypeFact {
  id?: number;
  codeTypeFact?: string;
  libelleTypeFact?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class TypeFact implements ITypeFact {
  constructor(
    public id?: number,
    public codeTypeFact?: string,
    public libelleTypeFact?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
