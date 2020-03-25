import { Moment } from 'moment';
import { IFicheMedical } from 'app/shared/model/fiche-medical.model';

export interface IOrdonnance {
  id?: number;
  numero?: string;
  date?: Moment;
  description?: string;
  prescription?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  ficheMedical?: IFicheMedical;
}

export class Ordonnance implements IOrdonnance {
  constructor(
    public id?: number,
    public numero?: string,
    public date?: Moment,
    public description?: string,
    public prescription?: string,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public ficheMedical?: IFicheMedical
  ) {}
}
