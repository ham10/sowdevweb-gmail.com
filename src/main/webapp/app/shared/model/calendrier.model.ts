import { Moment } from 'moment';

export interface ICalendrier {
  id?: number;
  libelleCalendrier?: string;
  heureDebut?: Moment;
  heureFin?: Moment;
  dateDebut?: Moment;
  dateFin?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: Moment;
  userUpdated?: number;
  userDeleted?: number;
}

export class Calendrier implements ICalendrier {
  constructor(
    public id?: number,
    public libelleCalendrier?: string,
    public heureDebut?: Moment,
    public heureFin?: Moment,
    public dateDebut?: Moment,
    public dateFin?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: Moment,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
