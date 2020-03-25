import { Moment } from 'moment';

export interface ICatReport {
  id?: number;
  codeCategReport?: string;
  libelleCategReport?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class CatReport implements ICatReport {
  constructor(
    public id?: number,
    public codeCategReport?: string,
    public libelleCategReport?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
