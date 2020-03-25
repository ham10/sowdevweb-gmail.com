import { Moment } from 'moment';
import { IFicheMedical } from 'app/shared/model/fiche-medical.model';

export interface IVaccin {
  id?: number;
  description?: string;
  date?: Moment;
  dateRenouv?: Moment;
  dateCreated?: Moment;
  dateUpdated?: Moment;
  userCreated?: number;
  userUpdated?: number;
  userDeleted?: number;
  ficheMedical?: IFicheMedical;
}

export class Vaccin implements IVaccin {
  constructor(
    public id?: number,
    public description?: string,
    public date?: Moment,
    public dateRenouv?: Moment,
    public dateCreated?: Moment,
    public dateUpdated?: Moment,
    public userCreated?: number,
    public userUpdated?: number,
    public userDeleted?: number,
    public ficheMedical?: IFicheMedical
  ) {}
}
