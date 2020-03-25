import { Moment } from 'moment';

export interface IJourFerie {
  id?: number;
  libelleJourFerie?: string;
  dateJourFerie?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class JourFerie implements IJourFerie {
  constructor(
    public id?: number,
    public libelleJourFerie?: string,
    public dateJourFerie?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
