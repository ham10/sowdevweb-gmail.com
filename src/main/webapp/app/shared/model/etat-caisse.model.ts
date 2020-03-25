import { Moment } from 'moment';

export interface IEtatCaisse {
  id?: number;
  code?: number;
  libelle?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class EtatCaisse implements IEtatCaisse {
  constructor(
    public id?: number,
    public code?: number,
    public libelle?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
