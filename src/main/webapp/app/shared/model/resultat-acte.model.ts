import { Moment } from 'moment';
import { IActeMedical } from 'app/shared/model/acte-medical.model';
import { IFicheMedical } from 'app/shared/model/fiche-medical.model';

export interface IResultatActe {
  id?: number;
  numero?: string;
  resultat?: string;
  date?: Moment;
  description?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  acteMedicals?: IActeMedical[];
  ficheMedical?: IFicheMedical;
}

export class ResultatActe implements IResultatActe {
  constructor(
    public id?: number,
    public numero?: string,
    public resultat?: string,
    public date?: Moment,
    public description?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public acteMedicals?: IActeMedical[],
    public ficheMedical?: IFicheMedical
  ) {}
}
