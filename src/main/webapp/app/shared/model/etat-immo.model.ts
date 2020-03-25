import { Moment } from 'moment';

export interface IEtatImmo {
  id?: number;
  codeEtat?: string;
  libelleEtat?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  dateDeleted?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
}

export class EtatImmo implements IEtatImmo {
  constructor(
    public id?: number,
    public codeEtat?: string,
    public libelleEtat?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public dateDeleted?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number
  ) {}
}
