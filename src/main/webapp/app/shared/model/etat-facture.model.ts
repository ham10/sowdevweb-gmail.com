import { Moment } from 'moment';

export interface IEtatFacture {
  id?: number;
  code?: string;
  libelle?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class EtatFacture implements IEtatFacture {
  constructor(
    public id?: number,
    public code?: string,
    public libelle?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
