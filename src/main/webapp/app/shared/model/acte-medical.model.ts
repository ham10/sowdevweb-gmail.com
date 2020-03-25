import { Moment } from 'moment';
import { ITarif } from 'app/shared/model/tarif.model';
import { IResultatActe } from 'app/shared/model/resultat-acte.model';

export interface IActeMedical {
  id?: number;
  code?: string;
  libelleA?: string;
  description?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  tarifs?: ITarif[];
  resultatActes?: IResultatActe[];
}

export class ActeMedical implements IActeMedical {
  constructor(
    public id?: number,
    public code?: string,
    public libelleA?: string,
    public description?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public tarifs?: ITarif[],
    public resultatActes?: IResultatActe[]
  ) {}
}
